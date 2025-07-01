package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, String> {

//    Gestion-de-stock
    @Query("SELECT p FROM Produit p WHERE p.supprimerStatus = 'FALSE'")
    List<Produit> findAllBySupprimerStatusFalse();

    Produit findBySlug(String slug);

//    Bamako-Gadgets
    @Query(
            value = "SELECT * FROM produit WHERE publier = true AND supprimer_status = false",
            nativeQuery = true
    )
    List<Produit> findAllPublierEtNonSupprime();

    List<Produit> findByCategorieStock_IdCat(Long idCat);
}

