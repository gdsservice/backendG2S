package com.gds.Gestion.de.stock.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetteProduitDTO {


    private Long idDetteProduit;
    private int montant;
    private int quantite;
    private int reduction;

}
