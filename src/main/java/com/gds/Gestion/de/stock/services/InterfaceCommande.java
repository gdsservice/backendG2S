package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.Input.CommandeInput;
import com.gds.Gestion.de.stock.exceptions.EmptyException;

public interface InterfaceCommande {

    void effectuer(CommandeInput commandeInput) throws EmptyException;
}
