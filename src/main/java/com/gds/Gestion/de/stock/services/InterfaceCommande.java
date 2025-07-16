package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.DAOs.CommandeDAO;
import com.gds.Gestion.de.stock.Input.CommandeInput;
import com.gds.Gestion.de.stock.exceptions.EmptyException;

import java.util.List;

public interface InterfaceCommande {

    void effectuer(CommandeInput commandeInput) throws EmptyException;
    List<CommandeDAO> listCommande();
    List<CommandeDAO> listCommandeTraiterFalse();
}
