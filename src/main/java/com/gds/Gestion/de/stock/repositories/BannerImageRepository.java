package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.BannerImage;
import com.gds.Gestion.de.stock.entites.ProduitImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerImageRepository extends JpaRepository<BannerImage, Long> {
    List<BannerImage> findByBannerIdBanner(String idBanner);
    BannerImage findFirstByBannerIdBanner(String idBanner);
    void deleteByBannerIdBanner(String idBanner);
}
