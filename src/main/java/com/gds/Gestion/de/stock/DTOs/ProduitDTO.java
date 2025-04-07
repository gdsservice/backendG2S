package com.gds.Gestion.de.stock.DTOs;


import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private String image;
    private LocalDate date;
    private String note;
    private SupprimerStatus supprimerStatus;
    private CategorieStockDTO categorieStockProdDTO;
    private Utilisateur utilisateurProd;

}
