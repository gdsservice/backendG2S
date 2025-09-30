package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.BannerImage;
import com.gds.Gestion.de.stock.entites.CollectionImage;
import com.gds.Gestion.de.stock.entites.ProduitImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionImageRepository extends JpaRepository<CollectionImage, Long> {
    List<CollectionImage> findByCollectionIdCollection(String idCollection);
    CollectionImage findFirstByCollectionIdCollection(String idBanner);
    void deleteByCollectionIdCollection(String idCollection);
}

