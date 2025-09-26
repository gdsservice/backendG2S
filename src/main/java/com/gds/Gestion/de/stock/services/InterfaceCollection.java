package com.gds.Gestion.de.stock.services;


import com.gds.Gestion.de.stock.Input.CollectionINPUT;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InterfaceCollection {

    void addCollection(CollectionINPUT collectionINPUT, List<MultipartFile> images) throws EmptyException, IOException;
}
