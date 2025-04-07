package com.gds.Gestion.de.stock.DTOs;


import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorieStockDTO {


    private Long idCat;
    private String nom;
    private String description;
    private LocalDate date;
    private SupprimerStatus supprimerStatus;
}
