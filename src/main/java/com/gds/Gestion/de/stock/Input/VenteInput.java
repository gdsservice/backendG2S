package com.gds.Gestion.de.stock.Input;

import com.gds.Gestion.de.stock.DTOs.ProduitDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenteInput {

    private int quantite;
    private int reduction;
    private List<ProduitDTO> produit;
    private ClientInput clientInput;
}
