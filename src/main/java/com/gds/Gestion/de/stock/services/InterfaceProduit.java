package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.DTOs.ProduitDTO;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface InterfaceProduit {


    void enregistrerProd(ProduitINPUT produitINPUT, List<MultipartFile> images) throws MontantQuantiteNullException, ProduitDupicateException, EmptyException, IOException;

//    List<byte[]> getImagesForProduit(String produitId);

    ProduitDTO enregistrerVenteProd(ProduitDTO produitDTO);

    ProduitDTO enregistrerDetteProd(ProduitDTO produitDTO);

    void modifierProd(ProduitINPUT produitINPUT, List<MultipartFile> images) throws EmptyException, IOException, ProduitNotFoundException;

    void suppressionProd(String idProd) throws ProduitNotFoundException;

    ProduitDAO afficherProd(String idProd) throws ProduitNotFoundException;

    ProduitDAO afficherSlug(String slug) throws ProduitNotFoundException, EmptyException;

    List<ProduitDAO> ListerProd();

    List<ProduitDAO> rechercherProduits(Map<String, String> params);

    List<ProduitDAO> listProduitLimit(int min, int max);


}
