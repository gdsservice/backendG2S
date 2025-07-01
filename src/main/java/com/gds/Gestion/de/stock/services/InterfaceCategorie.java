package com.gds.Gestion.de.stock.services;


import com.gds.Gestion.de.stock.DAOs.CatProduitListDAO;
import com.gds.Gestion.de.stock.DAOs.CategorieStockDAO;
import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.DTOs.CategorieStockDTO;
import com.gds.Gestion.de.stock.exceptions.CategorieDuplicateException;
import com.gds.Gestion.de.stock.exceptions.CategorieNotFoundException;
import com.gds.Gestion.de.stock.exceptions.EmptyException;

import java.util.List;
import java.util.Map;

public interface InterfaceCategorie {

    CategorieStockDTO creerCat(CategorieStockDTO categorieStockDTO) throws CategorieDuplicateException, EmptyException;
    CategorieStockDTO modifierCat(CategorieStockDTO categorieStockDTO) throws CategorieDuplicateException, EmptyException;
    void supprimerCat(Long idCategorieStock) throws CategorieNotFoundException;
    List<CategorieStockDAO> listCat();
    List<CatProduitListDAO> listCatProd();
    List<CatProduitListDAO> listCatRechercher(int min, int max);
    CategorieStockDTO afficher(Long idCat) throws CategorieNotFoundException;
    CatProduitListDAO catIdProduitList(String slug);
}
