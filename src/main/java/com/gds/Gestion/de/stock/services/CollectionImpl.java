package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.DAOs.BannerDAO;
import com.gds.Gestion.de.stock.DAOs.CollectionDAO;
import com.gds.Gestion.de.stock.Input.CollectionINPUT;
import com.gds.Gestion.de.stock.entites.*;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.exceptions.ProduitNotFoundException;
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
import java.util.stream.Collectors;

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
        collection.setUtilisateurCollection(principal);
        collection.setIdCollection("GDS" + UUID.randomUUID());
        collection.setSupprimerStatus(SupprimerStatus.FALSE);
        Collections saveCollection = collectionRepository.save(collection);

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

    @Override
    public void modifierCollection(CollectionINPUT collectionINPUT, List<MultipartFile> images) throws ProduitNotFoundException, IOException {
        // Vérifier que le produit existe
        Collections collectionExist = collectionRepository.findById(collectionINPUT.getIdCollection())
                .orElseThrow(() -> new ProduitNotFoundException("Collection non trouvé avec ID: " + collectionINPUT.getIdCollection()));

        // Mise à jour des champs de base
        collectionExist.setTitre(collectionINPUT.getTitre());
        collectionExist.setUtilisateurCollection(collectionINPUT.getUtilisateurCollection());
        collectionExist.setSupprimerStatus(SupprimerStatus.FALSE);
        collectionExist.setIdCollection(collectionINPUT.getIdCollection());
        collectionExist.setPublier(collectionINPUT.isPublier());
        collectionExist.setBtn_link(collectionINPUT.getBtn_link());
        collectionExist.setBtn_text(collectionINPUT.getBtn_text());
        collectionExist.setSous_titre(collectionINPUT.getSous_titre());

        // Gestion des images
        if (images != null && !images.isEmpty()) {
            // Supprimer les anciennes images
            collectionImageRepository.deleteByCollectionIdCollection(collectionExist.getIdCollection());
            // Ajouter les nouvelles images
            List<CollectionImage> collectionImages = new ArrayList<>();
            for (MultipartFile file : images) {
                CollectionImage image = new CollectionImage();
                image.setName(file.getOriginalFilename());
                image.setType(file.getContentType());
                image.setData(file.getBytes());
                image.setCollection(collectionExist);
                collectionImages.add(image);
            }
            collectionImageRepository.saveAll(collectionImages);
        }
        collectionRepository.save(collectionExist);
    }

    @Override
    public CollectionDAO afficherCollection(String idCollection) throws ProduitNotFoundException {
        return collectionMapper.mapCollectionADAO(collectionRepository.findById(idCollection).orElseThrow(()->
                new ProduitNotFoundException(" Cet collection n'existe pas")));
    }

    @Override
    public List<CollectionDAO> listCollection() {
        List<Collections> allBySupprimerStatusFalse = collectionRepository.findAllBySupprimerStatusFalse();
        return allBySupprimerStatusFalse.stream().map(collection -> collectionMapper.mapCollectionADAO(collection))
                .collect(Collectors.toList());
    }

    @Override
    public void suppressionCollection(String idCollection) throws ProduitNotFoundException {
        Collections collections = collectionRepository.findById(idCollection)
                .orElseThrow(() -> new ProduitNotFoundException("Cet collection n'existe pas"));
        if (collections.getSupprimerStatus() == SupprimerStatus.TRUE)
            throw new ProduitNotFoundException("Ce collection est supprimer ! ");
        collections.setSupprimerStatus(SupprimerStatus.TRUE);
        collectionRepository.save(collections);
    }
}
