package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.DTOs.ProduitDTO;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.exceptions.*;

import java.io.IOException;
import java.util.List;

public interface InterfaceProduit {


    void enregistrerProd(ProduitINPUT produitINPUT) throws MontantQuantiteNullException, ProduitDupicateException, EmptyException, IOException;

    ProduitDTO enregistrerVenteProd(ProduitDTO produitDTO);

    ProduitDTO enregistrerDetteProd(ProduitDTO produitDTO);

    void modifierProd(ProduitINPUT produitINPUT) throws EmptyException, IOException;

    void suppressionProd(String idProd) throws ProduitNotFoundException;

    ProduitDAO afficherProd(String idProd) throws ProduitNotFoundException;

    List<ProduitDAO> ListerProd();

}
