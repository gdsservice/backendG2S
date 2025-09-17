package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.CommandeProduit;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommandeProduitRepository extends JpaRepository<CommandeProduit, Long> {

    List<CommandeProduit> findByCommandeIdCde(String idCde);

    @Query("SELECT cp FROM CommandeProduit cp WHERE cp.commande.idCde = :idCde")
    List<CommandeProduit> findCommandeProduitsByCommandeId(String idCde);
}
