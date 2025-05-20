package com.gds.Gestion.de.stock.DTOs;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDTO {

    private String idProd;
    private String designation;
    private int quantite;
    private int prixUnitaire;
    private int montant;
    private MultipartFile image;
    private String imageUrl;
    private LocalDate date;
    private String note;
    private SupprimerStatus supprimerStatus;
    private CategorieStockDTO categorieStockProdDTO;
    private Utilisateur utilisateurProd;

    private String slug;
    private String prixRegulier;
    private String description;
    private boolean nouveaute;
    private boolean vedette;
    private boolean offreSpeciale;
    private boolean plusVendu;
    private boolean publier;

}
