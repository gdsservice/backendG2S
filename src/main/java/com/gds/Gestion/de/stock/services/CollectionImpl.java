package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.Input.CollectionINPUT;
import com.gds.Gestion.de.stock.entites.CollectionImage;
import com.gds.Gestion.de.stock.entites.Collections;
import com.gds.Gestion.de.stock.entites.ProduitImage;
import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.mappers.CollectionMapper;
import com.gds.Gestion.de.stock.repositories.CollectionImageRepository;
import com.gds.Gestion.de.stock.repositories.CollectionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CollectionImpl implements InterfaceCollection{

    private CollectionMapper collectionMapper;
    private CollectionRepository collectionRepository;
    private CollectionImageRepository collectionImageRepository;

    @Override
    public void addCollection(CollectionINPUT collectionINPUT, List<MultipartFile> images) throws EmptyException, IOException {

        Collections collection = collectionMapper.collectionINPUTACollection(collectionINPUT);
        Collections byTitre = collectionRepository.findByTitre(collection.getTitre());
        if(byTitre != null){
            throw new EmptyException("Cette collectiom existe deja !");
        }
        Utilisateur principal = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        byTitre.setUtilisateurCollection(principal);
        byTitre.setIdCollection("GDS" + UUID.randomUUID());
        byTitre.setSupprimerStatus(SupprimerStatus.FALSE);
        Collections saveCollection = collectionRepository.save(byTitre);

        // Traitement des images
        List<CollectionImage> collectionImages = new ArrayList<>();
        for (MultipartFile file : images) {
            CollectionImage image = new CollectionImage();
            image.setName(file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setData(file.getBytes());
            image.setCollection(saveCollection);
            collectionImages.add(image);
            for (CollectionImage collectionImage : collectionImages){
                collectionImageRepository.save(collectionImage);
            }
        }

    }
}
