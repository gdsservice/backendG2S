package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.Collections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collections, String> {

    Collections findByTitre(String titre);

    @Query("SELECT c FROM Collections c WHERE c.supprimerStatus = 'FALSE'")
    List<Collections> findAllBySupprimerStatusFalse();
}
