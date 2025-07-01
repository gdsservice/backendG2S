package com.gds.Gestion.de.stock.controllers;


import com.gds.Gestion.de.stock.DAOs.CatProduitListDAO;
import com.gds.Gestion.de.stock.DAOs.CategorieStockDAO;
import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.DTOs.CategorieStockDTO;
import com.gds.Gestion.de.stock.exceptions.CategorieDuplicateException;
import com.gds.Gestion.de.stock.exceptions.CategorieNotFoundException;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.services.InterfaceCategorie;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/categorie")
public class CategorieController {

    private InterfaceCategorie interfaceCategorie;

//    Gestion de stock
    @PostMapping("/creerCat")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private CategorieStockDTO save(@Valid @RequestBody CategorieStockDTO categorieStockDTO) throws CategorieDuplicateException, EmptyException {
        return interfaceCategorie.creerCat(categorieStockDTO);
    };

    @PutMapping("/modifierCat/{idCat}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private CategorieStockDTO update(@Valid @RequestBody CategorieStockDTO categorieStockDTO, @PathVariable("idCat") Long idCat) throws CategorieDuplicateException, EmptyException {
        categorieStockDTO.setIdCat(idCat);
        return interfaceCategorie.modifierCat(categorieStockDTO);
    }

    @GetMapping("/afficherCat/{idCat}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private CategorieStockDTO getIdCat(@Valid @PathVariable("idCat") Long idCat) throws CategorieNotFoundException {
        return interfaceCategorie.afficher(idCat);
    }

    @PutMapping("/supprimerCat")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private void delete(@Valid @RequestBody Long idCat) throws CategorieNotFoundException {
        interfaceCategorie.supprimerCat(idCat);
    }

    @GetMapping(value="/listeCat" , consumes = { "application/json", "application/xml" })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private List<CategorieStockDAO> getAll(  ) {
        return interfaceCategorie.listCat();
    }



//    Bamako-Gadgets
    @GetMapping("/recherche")
    private List<CatProduitListDAO> getAllCatProdRecherche(
            @RequestParam(name = "min", defaultValue = "1") int min,
            @RequestParam(name = "max", defaultValue = "5") int max) {
        return interfaceCategorie.listCatRechercher(min, max);
    }

    @GetMapping("/listCatProd")
    private List<CatProduitListDAO> getAllCatProd(){
        return interfaceCategorie.listCatProd();
    }

    @GetMapping("/slug/{slug}")
    private CatProduitListDAO getAllCatProd(@Valid @PathVariable("slug") String slug){
        return interfaceCategorie.catIdProduitList(slug);
    }

}
