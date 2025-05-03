package com.gds.Gestion.de.stock.Input;

import com.gds.Gestion.de.stock.DTOs.CategorieStockDTO;
import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitINPUT {

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
}
