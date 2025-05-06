package com.gds.Gestion.de.stock.services;


import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.DTOs.ProduitDTO;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.entites.Produit;
import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import com.gds.Gestion.de.stock.exceptions.*;
import com.gds.Gestion.de.stock.mappers.ProduitMapper;
import com.gds.Gestion.de.stock.repositories.ProduitRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProduitImpl implements InterfaceProduit {

    private ProduitRepository produitRepository;
    private ProduitMapper produitMapper;


    @Override
    public void enregistrerProd(ProduitINPUT produitINPUT) throws MontantQuantiteNullException, IOException, EmptyException {

        Produit produit = produitMapper.mapDeINPUTAProd(produitINPUT);

 //        verification de produit entrant
        if (produitINPUT.getImage() == null && produitINPUT.getImageUrl() == null && produitINPUT.getImage().getContentType() == null && produitINPUT.getImage().getContentType().isEmpty()) {
            throw new EmptyException("Selectionner une image");
        }

        if (produitINPUT.getQuantite() <= 0)
            throw new MontantQuantiteNullException("La quantite doit etre superieur a 0");

        if (produitINPUT.getPrixUnitaire() <= 0 )
            throw new MontantQuantiteNullException("Le prix unitaire doit etre superieur a 0");

        produit.setImageData(produitINPUT.getImage().getBytes());
        produit.setImageType(produitINPUT.getImage().getContentType());
        produit.setImageName(produitINPUT.getImage().getOriginalFilename());
        int montant = produitINPUT.getPrixUnitaire() * produitINPUT.getQuantite();
        produit.setMontant(montant);
        produit.setDate(LocalDate.now());
        Utilisateur userConnecter = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        produit.setUtilisateurProd(userConnecter);
        produit.setIdProd("GDS "+UUID.randomUUID());
        produit.setDate(LocalDate.now());
        produit.setSupprimerStatus(SupprimerStatus.FALSE);
        Produit save = produitRepository.save(produit);
        ProduitINPUT produitINPUT1 = produitMapper.mapDeProdAINPUT(save);
        produitINPUT1.setImageUrl("/produit/image/" + save.getIdProd());
//        return produitDAO1;
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
    public void modifierProd(ProduitINPUT produitINPUT) throws EmptyException, IOException {

        Produit produitExist = produitRepository.findById(produitINPUT.getIdProd())
                .orElseThrow(() -> new EmptyException("Ce produit n'existe pas"));

        // Mise à jour des champs de base
        produitExist.setPrixUnitaire(produitINPUT.getPrixUnitaire());
        produitExist.setQuantite(produitINPUT.getQuantite());
        produitExist.setDesignation(produitINPUT.getDesignation());
        produitExist.setNote(produitINPUT.getNote());
        int montant = produitINPUT.getPrixUnitaire() * produitINPUT.getQuantite();
        produitExist.setMontant(montant);
        produitExist.setSupprimerStatus(SupprimerStatus.FALSE);

        // Mise à jour de l'image uniquement si une nouvelle est fournie
        if (produitINPUT.getImage() != null && !produitINPUT.getImage().isEmpty()) {
            produitExist.setImageData(produitINPUT.getImage().getBytes());
            produitExist.setImageType(produitINPUT.getImage().getContentType());
            produitExist.setImageName(produitINPUT.getImage().getOriginalFilename());
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
    public List<ProduitDAO> ListerProd() {
        List<Produit> produits = produitRepository.findAllBySupprimerStatusFalse();
        return produits.stream().map(produit -> produitMapper.mapDeProdADAO(produit))
                .collect(Collectors.toList());
    }
}
