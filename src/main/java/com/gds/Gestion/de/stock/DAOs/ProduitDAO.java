package com.gds.Gestion.de.stock.DAOs;

import com.gds.Gestion.de.stock.DTOs.CategorieStockDTO;
import com.gds.Gestion.de.stock.entites.CategorieStock;
import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDAO {

    private String idProd;
    private String designation;
    private int quantite;
    private int prixUnitaire;
    private int montant;
    private MultipartFile image;
    private String imageUrl;
    private LocalDate date;
    private String note;
    // Nouveau attribut
    private String slug;
    private String prixRegulier;
    private String description;
    private boolean nouveaute;
    private boolean vedette;
    private boolean offreSpeciale;
    private boolean plusVendu;
    private boolean publier;
    private String caracteristique;
    private String info;
    // ####################
    private SupprimerStatus supprimerStatus;
    private CategorieStockDTO categorieStockProdDTO;
    private Utilisateur utilisateurProd;
}
