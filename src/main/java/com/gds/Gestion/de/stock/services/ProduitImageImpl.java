package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.entites.ProduitImage;
import com.gds.Gestion.de.stock.repositories.ProduitImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProduitImageImpl implements InterfaceProduitImage{

    private ProduitImageRepository produitImageRepository;

    public byte[] getMainImageForProduit(String idProd) {
        return produitImageRepository.findFirstByProduitIdProd(idProd).getData();
    }

    public List<byte[]> getImagesForProduit(String idProd) {
        return produitImageRepository.findByProduitIdProd(idProd)
                .stream()
                .map(ProduitImage::getData)
                .collect(Collectors.toList());
    }

}
