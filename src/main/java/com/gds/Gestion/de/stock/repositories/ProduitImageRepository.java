package com.gds.Gestion.de.stock.repositories;

import com.gds.Gestion.de.stock.entites.Produit;
import com.gds.Gestion.de.stock.entites.ProduitImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProduitImageRepository extends JpaRepository<ProduitImage, Long> {
    List<ProduitImage> findByProduitIdProd(String idProd);
    ProduitImage findFirstByProduitIdProd(String idProd);
    void deleteByProduitIdProd(String produitId);
}

