package com.gds.Gestion.de.stock.services;


import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.DTOs.ProduitDTO;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.entites.Produit;
import com.gds.Gestion.de.stock.entites.ProduitImage;
import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import com.gds.Gestion.de.stock.exceptions.*;
import com.gds.Gestion.de.stock.mappers.CategorieMapper;
import com.gds.Gestion.de.stock.mappers.ProduitMapper;
import com.gds.Gestion.de.stock.repositories.ProduitImageRepository;
import com.gds.Gestion.de.stock.repositories.ProduitRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProduitImpl implements InterfaceProduit {

    private ProduitRepository produitRepository;
    private ProduitMapper produitMapper;
    private ProduitImageRepository produitImageRepository;
    private CategorieMapper categorieMapper;


    @Override
    public void enregistrerProd(ProduitINPUT produitINPUT, List<MultipartFile> images)
            throws MontantQuantiteNullException, IOException, EmptyException {

        Produit produit = produitMapper.mapDeINPUTAProd(produitINPUT);

        // Vérification des images
        if (images == null || images.isEmpty()) {
            throw new EmptyException("Au moins une image est requise");
        }

        // Validation des autres champs
        if (produitINPUT.getQuantite() <= 0)
            throw new MontantQuantiteNullException("La quantite doit etre superieur a 0");

        if (produitINPUT.getPrixUnitaire() <= 0)
            throw new MontantQuantiteNullException("Le prix unitaire doit etre superieur a 0");

        Produit bySlug = produitRepository.findBySlug(produitINPUT.getSlug());
        if (bySlug != null)
            throw new EmptyException("Ce slug existe deja");

        // Calcul et initialisation
        int montant = produitINPUT.getPrixUnitaire() * produitINPUT.getQuantite();
        produit.setMontant(montant);
        produit.setDate(LocalDate.now());

        Utilisateur userConnecter = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        produit.setUtilisateurProd(userConnecter);
        produit.setIdProd("GDS-"+UUID.randomUUID());
        produit.setSupprimerStatus(SupprimerStatus.FALSE);
        produit.setNouveaute(true);

        Produit saveProduit = produitRepository.save(produit);

        // Traitement des images
        List<ProduitImage> produitImages = new ArrayList<>();
        for (MultipartFile file : images) {
            ProduitImage image = new ProduitImage();
            image.setName(file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setData(file.getBytes());
            image.setProduit(saveProduit);
            produitImages.add(image);
            for (ProduitImage produitImage : produitImages){
                produitImageRepository.save(produitImage);
            }
        }
    }



    @Override
    public ProduitDTO enregistrerVenteProd(ProduitDTO produitDTO) {
        Produit produit = produitMapper.mapDeDtoAProd(produitDTO);
        Utilisateur userConnecter = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        produit.setUtilisateurProd(userConnecter);
        return produitMapper.mapDeProdADto(produitRepository.save(produit));
    }

    @Override
    public ProduitDTO enregistrerDetteProd(ProduitDTO produitDTO) {
        Produit produit = produitMapper.mapDeDtoAProd(produitDTO);
        Utilisateur userConnecter = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        produit.setUtilisateurProd(userConnecter);
        return produitMapper.mapDeProdADto(produitRepository.save(produit));
    }

    @Override
    public void modifierProd(ProduitINPUT produitINPUT, List<MultipartFile> images)
            throws EmptyException, IOException, ProduitNotFoundException {

        // Vérifier que le produit existe
        Produit produitExist = produitRepository.findById(produitINPUT.getIdProd())
                .orElseThrow(() -> new ProduitNotFoundException("Produit non trouvé avec ID: " + produitINPUT.getIdProd()));

        // Validation des données
        if (produitINPUT.getQuantite() <= 0)
            throw new EmptyException("La quantité doit être supérieure à 0");

        if (produitINPUT.getPrixUnitaire() <= 0)
            throw new EmptyException("Le prix unitaire doit être supérieur à 0");

        // Mise à jour des champs de base
        produitExist.setDesignation(produitINPUT.getDesignation());
        produitExist.setPrixUnitaire(produitINPUT.getPrixUnitaire());
        produitExist.setQuantite(produitINPUT.getQuantite());
        produitExist.setMontant(produitINPUT.getPrixUnitaire() * produitINPUT.getQuantite());
        produitExist.setDescription(produitINPUT.getDescription());
        produitExist.setNote(produitINPUT.getNote());
        produitExist.setPrixRegulier(produitINPUT.getPrixRegulier());
        produitExist.setSlug(produitINPUT.getSlug());
        produitExist.setNouveaute(produitINPUT.isNouveaute());
        produitExist.setOffreSpeciale(produitINPUT.isOffreSpeciale());
        produitExist.setPlusVendu(produitINPUT.isPlusVendu());
        produitExist.setVedette(produitINPUT.isVedette());
        produitExist.setPublier(produitINPUT.isPublier());
        produitExist.setCaracteristique(produitINPUT.getCaracteristique());
        produitExist.setInfo(produitINPUT.getInfo());


        // Mise à jour de la catégorie si nécessaire
        if (produitINPUT.getCategorieStockProdDTO() != null) {
            produitExist.setCategorieStock(categorieMapper.mapDeDtoACategorie(produitINPUT.getCategorieStockProdDTO()));
        }

        // Gestion des images
        if (images != null && !images.isEmpty()) {
            // Supprimer les anciennes images
            produitImageRepository.deleteByProduitIdProd(produitExist.getIdProd());

            // Ajouter les nouvelles images
            List<ProduitImage> produitImages = new ArrayList<>();
            for (MultipartFile file : images) {
                ProduitImage image = new ProduitImage();
                image.setName(file.getOriginalFilename());
                image.setType(file.getContentType());
                image.setData(file.getBytes());
                image.setProduit(produitExist);
                produitImages.add(image);
            }
            produitImageRepository.saveAll(produitImages);
        }

        produitRepository.save(produitExist);
    }


    @Override
    public void suppressionProd(String idProd) throws ProduitNotFoundException {
        Produit produit = produitRepository.findById(idProd)
                .orElseThrow(() -> new ProduitNotFoundException("Cet produit n'existe pas"));
        if (produit.getSupprimerStatus() == SupprimerStatus.TRUE)
            throw new ProduitNotFoundException("Ce produit est supprimer ! ");
        produit.setSupprimerStatus(SupprimerStatus.TRUE);
        produitRepository.save(produit);

    }

    @Override
    public ProduitDAO afficherProd(String idStock) throws ProduitNotFoundException {
       return produitMapper.mapDeProdADAO(produitRepository.findById(idStock).orElseThrow(()->
               new ProduitNotFoundException(" Cet stock n'existe pas")));

    }

    @Override
    public ProduitDAO afficherSlug(String slug) throws ProduitNotFoundException {
        Produit bySlug = produitRepository.findBySlug(slug);
        if (bySlug == null) {
            throw new ProduitNotFoundException("Produit avec le slug '" + slug + "' introuvable");
        }
        return produitMapper.mapDeProdADAO(bySlug);
    }


    @Override
    public List<ProduitDAO> ListerProd() {
        List<Produit> produits = produitRepository.findAllBySupprimerStatusFalse();
        return produits.stream().map(produit -> produitMapper.mapDeProdADAO(produit))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProduitDAO> rechercherProduits(Map<String, String> params) {
        List<Produit> produits = produitRepository.findAllPublierEtNonSupprime();
        // Appliquer les filtres
        if (params.containsKey("nouveaute") && params.get("nouveaute").equalsIgnoreCase("true")) {
            produits = produits.stream().filter(Produit::isNouveaute).collect(Collectors.toList());
        }
        if (params.containsKey("plusVendu") && params.get("plusVendu").equalsIgnoreCase("true")) {
            produits = produits.stream().filter(Produit::isPlusVendu).collect(Collectors.toList());
        }
        if (params.containsKey("offreSpeciale") && params.get("offreSpeciale").equalsIgnoreCase("true")) {
            produits = produits.stream().filter(Produit::isOffreSpeciale).collect(Collectors.toList());
        }
        if (params.containsKey("vedette") && params.get("vedette").equalsIgnoreCase("true")) {
            produits = produits.stream().filter(Produit::isVedette).collect(Collectors.toList());
        }



        return produits.stream().map(produitMapper::mapDeProdADAO).collect(Collectors.toList());
    }

    @Override
    public List<ProduitDAO> listProduitLimit(int min, int max){
        List<Produit> allPublierEtNonSupprime = produitRepository.findAllPublierEtNonSupprime();
        int fromIndex = Math.max(0, min - 1);
        int toIndex = Math.min(allPublierEtNonSupprime.size(), max);
        List<Produit> produitList = allPublierEtNonSupprime.subList(fromIndex, toIndex);
        return produitList.stream().map(produitMapper::mapDeProdADAO).collect(Collectors.toList());

    }

}
