package com.gds.Gestion.de.stock.Input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gds.Gestion.de.stock.DTOs.CategorieStockDTO;
import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitINPUT {

    private String idProd;
    private String designation;
    private int quantite;
    private int prixUnitaire;
    private int montant;

    // Nouveau attributbbbb
    private String slug;
    private String prixRegulier;
    private String description;
    private boolean nouveaute;
    private boolean vedette;
    private boolean offreSpeciale;
    private boolean plusVendu;
    private boolean publier;

    private List<MultipartFile> images;
    private List<String> imageUrls;
    private LocalDate date;
    private String note;
    private SupprimerStatus supprimerStatus;
    private CategorieStockDTO categorieStockProdDTO;
    private Utilisateur utilisateurProd;
}
