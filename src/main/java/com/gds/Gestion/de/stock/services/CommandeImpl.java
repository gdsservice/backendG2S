package com.gds.Gestion.de.stock.services;

import com.gds.Gestion.de.stock.DAOs.CommandeDAO;
import com.gds.Gestion.de.stock.Input.CommandeInput;
import com.gds.Gestion.de.stock.entites.Commande;
import com.gds.Gestion.de.stock.entites.CommandeProduit;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.mappers.CommandeMapper;
import com.gds.Gestion.de.stock.repositories.CommandeProduitRepository;
import com.gds.Gestion.de.stock.repositories.CommandeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CommandeImpl implements InterfaceCommande{

    private CommandeRepository commandeRepository;
    private CommandeMapper commandeMapper;
    private CommandeProduitRepository commandeProduitRepository;

    @Override
    public void effectuer(CommandeInput commandeInput) throws EmptyException {
        if (commandeInput==null)
            throw new EmptyException("Remplissez tous les champs");
        Commande commande = commandeMapper.mapInputToCde(commandeInput);
        commande.setIdCde("GDS" + UUID.randomUUID());
        commande.setSupprimerStatus(SupprimerStatus.FALSE);
        commande.setDateAjout(LocalDate.now());
        commande.setQuantite(commandeInput.getCommande().getQuantite());
        commande.setTotal(commandeInput.getCommande().getTotal());
        commande.setClientCde(commandeInput.getCommande().getClientCde());
        commande.setTraiter(false);
        commandeRepository.save(commande);

        List<CommandeProduit> listCommandeProduit = commandeInput.getListCommandeProduit();

        for (CommandeProduit cdeProduit : listCommandeProduit){
          CommandeProduit commandeProduit = new CommandeProduit();
          commandeProduit.setCommande(commande);
          commandeProduit.setProduit(cdeProduit.getProduit());
          commandeProduit.setMontant(cdeProduit.getMontant());
          commandeProduit.setQuantite(cdeProduit.getQuantite());
          commandeProduitRepository.save(commandeProduit);
        }

    }

    @Override
    public List<CommandeDAO> listCommande() {
        List<Commande> listCommande = commandeRepository.findAllBySupprimerStatusFalse();
        List<CommandeDAO> commandeDAOList = new ArrayList<>();
        for (Commande commande : listCommande){
            CommandeDAO commandeDAO = new CommandeDAO();
            commandeDAO.setCommande(commande);
            commandeDAO.setCommandeProduitList(commandeProduitRepository.findByCommandeIdCde(commande.getIdCde()));
            commandeDAOList.add(commandeDAO);
        }
        return commandeDAOList;
    }

    @Override
    public List<CommandeDAO> listCommandeTraiterFalse() {
        List<Commande> listCommande = commandeRepository.findBySupprimerStatusFalseAndTraiterFalse();
        List<CommandeDAO> commandeDAOList = new ArrayList<>();
        for (Commande commande : listCommande){
            CommandeDAO commandeDAO = new CommandeDAO();
            commandeDAO.setCommande(commande);
            commandeDAO.setCommandeProduitList(commandeProduitRepository.findByCommandeIdCde(commande.getIdCde()));
            commandeDAOList.add(commandeDAO);
        }
        return commandeDAOList;
    }
}
