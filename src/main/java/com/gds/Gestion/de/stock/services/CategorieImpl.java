package com.gds.Gestion.de.stock.services;


import com.gds.Gestion.de.stock.DAOs.CatProduitListDAO;
import com.gds.Gestion.de.stock.DAOs.CategorieStockDAO;
import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.DTOs.CategorieStockDTO;
import com.gds.Gestion.de.stock.entites.CategorieStock;
import com.gds.Gestion.de.stock.entites.Produit;
import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import com.gds.Gestion.de.stock.exceptions.CategorieDuplicateException;
import com.gds.Gestion.de.stock.exceptions.CategorieNotFoundException;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.mappers.CategorieMapper;
import com.gds.Gestion.de.stock.repositories.CategorieRepository;
import com.gds.Gestion.de.stock.repositories.ProduitRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CategorieImpl implements InterfaceCategorie {

    private CategorieRepository categorieRepository;
    private CategorieMapper categorieMapper;
    private ProduitRepository produitRepository;


//    Gestion de stock
    @Override
    public CategorieStockDTO creerCat(CategorieStockDTO categorieStockDTO) throws CategorieDuplicateException, EmptyException {
        CategorieStock categorieStock = categorieMapper.mapDeDtoACategorie(categorieStockDTO);
        CategorieStock cat = categorieRepository.findByNom(categorieStock.getNom());

        if (categorieStockDTO.getNom().isEmpty())
            throw new EmptyException("Remplissez les champs vides");

        if (cat != null)
            throw new CategorieDuplicateException(cat.getNom() +" existe deja");

        Utilisateur principal = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        categorieStock.setUtilisateurCat(principal);
        categorieStock.setDate(LocalDate.now());
        categorieStock.setPublier(true);
        categorieStock.setSupprimerStatus(SupprimerStatus.FALSE);
        return categorieMapper.mapDeCategorieADto(categorieRepository.save(categorieStock));
    }

    @Override
    public CategorieStockDTO modifierCat(CategorieStockDTO categorieStockDTO) throws EmptyException {
        CategorieStock categorieStockExist = categorieRepository.findById(categorieStockDTO.getIdCat())
                .orElseThrow(()-> new EmptyException("Cet categorie n'existe pas"));
        CategorieStock categorieStock = categorieMapper.mapDeDtoACategorie(categorieStockDTO);
        if (categorieStock.getNom().isEmpty())
            throw new EmptyException("Remplissez les champs vides");
        categorieStock.setIdCat(categorieStockExist.getIdCat());
        categorieStock.setDate(categorieStockExist.getDate());
        categorieStock.setSupprimerStatus(SupprimerStatus.FALSE);
        categorieStock.setUtilisateurCat(categorieStockExist.getUtilisateurCat());
        return categorieMapper.mapDeCategorieADto(categorieRepository.save(categorieStock));
    }

    @Override
    public void supprimerCat(Long idCategorieStock) throws CategorieNotFoundException {
        CategorieStock categorieStock = categorieRepository.findById(idCategorieStock).
                orElseThrow(() -> new CategorieNotFoundException("Cet categorie n'existe pas"));
        if (categorieStock.getSupprimerStatus() == SupprimerStatus.TRUE)
            throw new CategorieNotFoundException("Cet categorie n'existe pas");
        categorieStock.setSupprimerStatus(SupprimerStatus.TRUE);
        categorieRepository.save(categorieStock);
    }

    //    afficher la list ds categorie sans produit dans Categorie
    @Override
    public List<CategorieStockDAO> listCat() {
        List<CategorieStock> categorieStockDTOList = categorieRepository.findAllBySupprimerStatusFalse();
        return categorieStockDTOList.stream()
                .map(cat->categorieMapper.mapDeCategorieADAO(cat))
                .collect(Collectors.toList());
    }

    @Override
    public CategorieStockDTO afficher(Long idCat) throws CategorieNotFoundException {
        CategorieStock categorieStock = categorieRepository.findById(idCat).
                orElseThrow(()-> new CategorieNotFoundException("Cet categorie n'existe pas"));
        return categorieMapper.mapDeCategorieADto(categorieStock);
    }

    // Bamako-Gadgets
    @Override
    public List<CatProduitListDAO> listCatRechercher(int min, int max) {
        List<CategorieStock> categorieStockDTOList = categorieRepository.findAllBySupprimerStatusFalse();

        int fromIndex = Math.max(0, min - 1);
        int toIndex = Math.min(categorieStockDTOList.size(), max);

        List<CatProduitListDAO> catProduitListDAOS = new ArrayList<>();
        for (CategorieStock categorieStock : categorieStockDTOList.subList(fromIndex, toIndex)) {
            CatProduitListDAO catProduitListDAO = new CatProduitListDAO();
            catProduitListDAO.setCategorieStock(categorieStock);
            List<Produit> produitList = produitRepository.findByCategorieStock_IdCat(categorieStock.getIdCat());
            catProduitListDAO.setProduitList(produitList);
            catProduitListDAOS.add(catProduitListDAO);
        }

        return catProduitListDAOS;
    }

    @Override
    public List<CatProduitListDAO> listCatProd(){
        List<CategorieStock> categorieStockDTOList = categorieRepository.findAllBySupprimerStatusFalsePublierTrue();
        List<CatProduitListDAO> catProduitListDAOS = new ArrayList<>();
        for (CategorieStock categorieStock : categorieStockDTOList) {
            CatProduitListDAO catProduitListDAO = new CatProduitListDAO();
            catProduitListDAO.setCategorieStock(categorieStock);
            List<Produit> produitList = produitRepository.findByCategorieStock_IdCat(categorieStock.getIdCat());
            catProduitListDAO.setProduitList(produitList);
            catProduitListDAOS.add(catProduitListDAO);
        }
        return catProduitListDAOS;
    }

    @Override
    public CatProduitListDAO catIdProduitList(String slug) {
        CategorieStock bySlug = categorieRepository.findBySlug(slug);
        CatProduitListDAO catProduitListDAO = new CatProduitListDAO();
        catProduitListDAO.setCategorieStock(bySlug);
        List<Produit> produitList = produitRepository.findByCategorieStock_IdCat(bySlug.getIdCat());
        catProduitListDAO.setProduitList(produitList);
        return catProduitListDAO;
    }




}
