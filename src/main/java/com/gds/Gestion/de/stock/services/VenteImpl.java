package com.gds.Gestion.de.stock.services;


import com.gds.Gestion.de.stock.DAO.VenteDAO;
import com.gds.Gestion.de.stock.DTOs.ApprovisionDTO;
import com.gds.Gestion.de.stock.DTOs.ProduitDTO;
import com.gds.Gestion.de.stock.DTOs.VenteDTO;
import com.gds.Gestion.de.stock.Input.VenteInput;
import com.gds.Gestion.de.stock.entites.*;
import com.gds.Gestion.de.stock.enums.StatusVente;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import com.gds.Gestion.de.stock.exceptions.*;
import com.gds.Gestion.de.stock.mappers.ClientMapper;
import com.gds.Gestion.de.stock.mappers.ProduitMapper;
import com.gds.Gestion.de.stock.mappers.VenteInputMapper;
import com.gds.Gestion.de.stock.mappers.VenteMapper;
import com.gds.Gestion.de.stock.repositories.ClientRepository;
import com.gds.Gestion.de.stock.repositories.ProduitRepository;
import com.gds.Gestion.de.stock.repositories.VenteProduitRepository;
import com.gds.Gestion.de.stock.repositories.VenteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class VenteImpl implements InterfaceVente {


    private final ClientMapper clientMapper;

    private VenteMapper venteMapper;

    private VenteInputMapper venteInputMapper;

    private VenteRepository venteRepository;

    private VenteProduitRepository venteProduitRepository;

    private ClientRepository clientRepository;

    private ProduitRepository produitRepository;

    private ProduitMapper produitMapper;

    private ProduitImpl produit;

//    private ClientMapper clientMapper;

    //    ajouter une ventenksjabjnkcsnnkw mdvc ,sd mscmscmsd
    @Override
    public void effectuerVente(VenteInput venteInput) throws Exception {

//        // Validation des entrées
        if (venteInput.getListVenteProduit().stream().map(VenteProduit::getProduit).collect(Collectors.toList()).isEmpty()) {
            throw new EmptyException("Sélectionner un produit !");
        }
        if (venteInput.getVente().getClientsVente() == null) {
            throw new EmptyException("Sélectionner un client !");
        }

        // Création de la vente
        Vente vente = venteInputMapper.mapDeVenteInputAVente(venteInput);
        Utilisateur userConnecter = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vente.setUtilisateurVente(userConnecter);
        vente.setStatus(StatusVente.TRAITER);
        vente.setSupprimerStatus(SupprimerStatus.FALSE);
        vente.setDateVente(LocalDate.now());
        vente.setIdVente("GDS" + UUID.randomUUID());

        List<VenteProduit> listVenteProduit = venteInput.getListVenteProduit();

        for (VenteProduit venteProduit : listVenteProduit){

            if (venteInput.getVente().getReduction() > (0.2 * venteInput.getVente().getMontant())){
                throw new EmptyException("La réduction ne peut pas dépasser 20 % du montant pour le produit ");
            }
            vente.setReduction(venteInput.getVente().getReduction());

            // Obtenir le produit depuis la DB
            ProduitDTO produitDTO = produit.afficherProd(venteProduit.getProduit().getIdProd());
            if (venteInput.getVente().getQuantite() > produitDTO.getQuantite()){
                throw new EmptyException("Le stock est inssufisant ! ");
            }
            produitDTO.setQuantite(produitDTO.getQuantite() - venteInput.getVente().getQuantite());
            produitDTO.setMontant((produitDTO.getPrixUnitaire() * venteInput.getVente().getQuantite()) - venteInput.getVente().getQuantite());
            produitRepository.save(produitMapper.mapDeDtoAProd(produitDTO));
        }
        vente.setQuantite(venteInput.getVente().getQuantite());
        vente.setMontant(venteInput.getVente().getMontant());




        /*// Initialiser le montant total et la quantité totale
        int montantTotal = 0;
        int quantiteTotale = 0;



        for (VenteProduit venteProduit : listVenteProduit) {
            // Obtenir le produit depuis la DB
            ProduitDTO produitDTO = produit.afficherProd(venteProduit.getProduit().getIdProd());

            // Vérifier le stock
            if (produitDTO.getQuantite() < venteProduit.getQuantite()) {
                throw new InsufficientStockException("Stock insuffisant de "+produitDTO.getDesignation());
            }

            // Calcul du montant
            int montantProduit = produitDTO.getPrixUnitaire() * venteProduit.getQuantite();
            montantTotal += montantProduit;
            quantiteTotale += venteProduit.getQuantite();

            // Mise à jour du stock
            produitDTO.setQuantite(produitDTO.getQuantite() - venteProduit.getQuantite());
            produitDTO.setMontant(produitDTO.getMontant() - montantProduit);

            // Appliquer la réduction sur le montant total
//            if (vente.getReduction() > (0.2 * montantTotal)) {
//                throw new InsufficientStockException("La réduction ne peut pas dépasser 20 % du montant pour le produit ");
//            }
//            montantTotal -= venteInput.getVente().getReduction();
//            if (montantTotal < 0) {
//                montantTotal = 0;
//            }

            // Enregistrer la mise à jour du produit
            produitRepository.save(produitMapper.mapDeDtoAProd(produitDTO));
        }*/

        // Enregistrer la vente
        Vente saveVente = venteRepository.save(vente);

//        // Enregistrer les détails de la vente
        for (VenteProduit vpInput : listVenteProduit) {
            VenteProduit venteProduit = new VenteProduit();
            venteProduit.setProduit(vpInput.getProduit());
            venteProduit.setVente(saveVente);
            venteProduit.setQuantite(vpInput.getQuantite());
            venteProduit.setReduction(vpInput.getReduction());
            int montantProduit = vpInput.getProduit().getPrixUnitaire() * vpInput.getQuantite() - vpInput.getReduction();
            venteProduit.setMontant(montantProduit);
            venteProduitRepository.save(venteProduit);
        }


    }


    @Override
    public void annulerVente(VenteDTO venteDTO) throws EmptyException {

        Vente venteExist = venteRepository.findById(venteDTO.getIdVente())
                .orElseThrow(() -> new EmptyException("Cette vente n'existe pas"));

        // Vérification du statut
        if (venteExist.getStatus() == StatusVente.ANNULER) {
            throw new EmptyException("La vente est déjà annulée");
        }

        // Vérification de nullité avant l'itération sur les produits
        List<VenteProduit> venteProduitsList = venteProduitRepository.findVenteProduitsByVenteId(venteDTO.getIdVente());
        if (venteProduitsList == null || venteProduitsList.isEmpty()) {
            throw new EmptyException("La vente n'a pas de produits à annuler.");
        }

        // Mise à jour des informations de la vente
        venteDTO.setDateVente(venteExist.getDateVente());
        venteDTO.setSupprimerStatus(venteExist.getSupprimerStatus());
        venteDTO.setIdVente(venteExist.getIdVente());
        venteDTO.setStatus(StatusVente.ANNULER);
        venteDTO.setClientDTO(clientMapper.mapDeClientADto(venteExist.getClientsVente()));
//        venteRepository.save(venteMapper.mapDeDtoAVente(venteDTO));

        for (VenteProduit venteProduit : venteProduitsList) {
            Produit produit = venteProduit.getProduit();
            produit.setQuantite(produit.getQuantite() + venteProduit.getQuantite());
            produit.setMontant(produit.getMontant() + venteProduit.getMontant() + venteProduit.getReduction());
            produitRepository.save(produit);
        }
    }

    //  modifier une vente
    @Override
    public void modifierVente(VenteDTO venteDTO) throws EmptyException, InsufficientStockException, VenteNotFoundException {
        // Récupérer la vente à modifier
        Vente venteExist = venteRepository.findById(venteDTO.getIdVente())
                .orElseThrow(() -> new VenteNotFoundException("Cette vente n'existe pas"));
//        Vente vente = venteMapper.mapDeDtoAVente(venteDTO);
        // Mise à jour des informations de la vente
//        vente.setUtilisateurVente(venteExist.getUtilisateurVente());
//        if (venteDTO.getStatus() == StatusVente.ANNULER) {
//            throw new VenteNotFoundException("Vente est deja annule");
//        }
//        vente.setStatus(StatusVente.TRAITER);
//        vente.setDateVente(LocalDate.now());
//        vente.setSupprimerStatus(SupprimerStatus.FALSE);
//        vente.setIdVente(venteExist.getIdVente());
//        vente.setClientsVente(venteExist.getClientsVente());
//        venteRepository.save(vente);
    }


    //    recuperer une vente
    @Override
    public VenteDAO afficherVente(String idVente) throws VenteNotFoundException {
        Vente vente = venteRepository.findById(idVente).orElseThrow(() -> new VenteNotFoundException("Vente n'existe pas"));
//        VenteDTO venteDTO = venteMapper.mapDeVenteADTO(vente);
        VenteDAO venteDAO = new VenteDAO();
        venteDAO.setVente(vente);
        List<VenteProduit> byVenteIdVente = venteProduitRepository.findByVenteIdVente(vente.getIdVente());
        venteDAO.setVenteProduitList(byVenteIdVente);
        return venteDAO;
    }

    //  recuperer la liste des vente
    @Override
    public List<VenteDAO> listerVente() {
//        recuperer la vente qui n'est pas supprimer dans allVente
        List<Vente> allVente = venteRepository.findAllBySupprimerStatusFalse();
//        initialisation de tableau pour la liste de venteDAO
        List<VenteDAO> venteDAOList = new ArrayList<>();
//        Parcouri la liste de venteDAO
        for (Vente vente : allVente) {
            VenteDAO venteDAO = new VenteDAO();
            venteDAO.setVente(vente);
            String idVente = vente.getIdVente();
            List<VenteProduit> venteProduitList = venteProduitRepository.findByVenteIdVente(idVente);
            venteDAO.setVenteProduitList(venteProduitList);
            venteDAOList.add(venteDAO);
        }
        return venteDAOList;
    }

    @Override
    public long totalVente() {
        return venteRepository.countTotalVente();
    }

    //    supprimer une vente
    @Override
    public void supprimerVente(String venteId) throws VenteNotFoundException {
//        change
        VenteDAO venteDAO = afficherVente(venteId);
        Vente vente = venteMapper.mapDeDAOAVente(venteDAO);
//        Vente vente = venteMapper.mapDeDAOAVente(venteDAO.getVente());
        if (venteDAO == null)
            throw new VenteNotFoundException("Vente n'existe pas");
        vente.setSupprimerStatus(SupprimerStatus.TRUE);
        venteRepository.save(vente);
    }
}

//    SET FOREIGN_KEY_CHECKS=0;


