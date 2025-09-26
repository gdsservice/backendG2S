package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.Collections;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collections, String> {
    Collections findByTitre(String titre);
}
