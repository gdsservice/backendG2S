package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, String> {

    @Query("SELECT c FROM Commande c WHERE c.supprimerStatus = 'FALSE'")
    List<Commande> findAllBySupprimerStatusFalse();

    List<Commande> findBySupprimerStatusFalseAndTraiterFalse();

}
