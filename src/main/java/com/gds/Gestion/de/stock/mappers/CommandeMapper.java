package com.gds.Gestion.de.stock.mappers;

import com.gds.Gestion.de.stock.Input.CommandeInput;
import com.gds.Gestion.de.stock.entites.Commande;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CommandeMapper {


    public Commande mapInputToCde(CommandeInput commandeInput) {
        Commande commande = new Commande();
        BeanUtils.copyProperties(commandeInput,commande);
        commande.setClientCde(commandeInput.getCommande().getClientCde());
        return commande;
    }
}
