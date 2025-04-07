package com.gds.Gestion.de.stock.DTOs;


import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.StatusVente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenteProduitDTO {


    private Long idVenteProduit;
    private int montant;
    private int quantite;
    private int reduction;

}
