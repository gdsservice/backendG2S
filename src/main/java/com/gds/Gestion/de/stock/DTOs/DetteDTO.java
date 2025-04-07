package com.gds.Gestion.de.stock.DTOs;


import com.gds.Gestion.de.stock.entites.Client;
import com.gds.Gestion.de.stock.entites.Produit;
import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.StatusDette;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetteDTO {

    private String idDette;
    private String titre;
    private int quantite;
    private int reduction;
    private int montant;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatusDette status;
    private SupprimerStatus supprimerStatus;
    private String note;
    private ClientDTO clientDTO;
    private List<ProduitDTO> produitDTOS;
    private Utilisateur utilisateurDette;
}
