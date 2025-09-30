package com.gds.Gestion.de.stock.services;


import com.gds.Gestion.de.stock.DAOs.BannerDAO;
import com.gds.Gestion.de.stock.DAOs.CollectionDAO;
import com.gds.Gestion.de.stock.Input.CollectionINPUT;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.exceptions.ProduitNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InterfaceCollection {

    void addCollection(CollectionINPUT collectionINPUT, List<MultipartFile> images) throws EmptyException, IOException;

    void modifierCollection(CollectionINPUT collectionINPUT, List<MultipartFile> images) throws ProduitNotFoundException, IOException;

    CollectionDAO afficherCollection(@Valid String idCollection) throws ProduitNotFoundException;

    List<CollectionDAO> listCollection();

    void suppressionCollection(@Valid String idCollection) throws ProduitNotFoundException;
}
