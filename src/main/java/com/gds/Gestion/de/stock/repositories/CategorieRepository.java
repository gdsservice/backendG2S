package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.DTOs.ProduitDTO;
import com.gds.Gestion.de.stock.entites.Approvision;
import com.gds.Gestion.de.stock.entites.CategorieStock;
import com.gds.Gestion.de.stock.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategorieRepository extends JpaRepository<CategorieStock, Long> {

//    Gestion-de-stock
    CategorieStock findByNom(String nom);

    @Query("SELECT c FROM CategorieStock c WHERE c.supprimerStatus = 'FALSE'")
    List<CategorieStock> findAllBySupprimerStatusFalse();


//    Bamako-Gadgets
//    List<CategorieStock> findBySupprimerStatusFalseAndPublierTrue();

    @Query(value = "SELECT * FROM categorie_stock WHERE supprimer_status = 'false' AND publier = '1'",
            nativeQuery = true)
    List<CategorieStock> findAllBySupprimerStatusFalsePublierTrue();

    CategorieStock findBySlug(String slug);



}
