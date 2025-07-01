package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, String> {
}
