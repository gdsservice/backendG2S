package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.entites.BannerImage;
import com.gds.Gestion.de.stock.entites.CollectionImage;
import com.gds.Gestion.de.stock.repositories.BannerImageRepository;
import com.gds.Gestion.de.stock.repositories.CollectionImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CollectionImageImpl implements InterfaceCollectionImage{

    private CollectionImageRepository collectionImageRepository;

    @Override
    public byte[] getMainImageForCollection(String idCollection) {
        return collectionImageRepository.findFirstByCollectionIdCollection(idCollection).getData();
    }

    @Override
    public List<byte[]> getImagesForCollection(String idCollection) {
        return collectionImageRepository.findByCollectionIdCollection(idCollection)
                .stream()
                .map(CollectionImage::getData)
                .collect(Collectors.toList());
    }
}
