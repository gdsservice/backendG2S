package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, String> {

    //    Gestion-de-stock

    Banner findByTitre(String titre);

    @Query("SELECT b FROM Banner b WHERE b.supprimerStatus = 'FALSE'")
    List<Banner> findAllBySupprimerStatusFalse();
}

