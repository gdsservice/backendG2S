package com.gds.Gestion.de.stock.DAO;

import com.gds.Gestion.de.stock.DTOs.VenteDTO;
import com.gds.Gestion.de.stock.entites.Produit;
import com.gds.Gestion.de.stock.entites.Vente;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenteDAO {
    private Vente vente;
    private List<VenteProduit> venteProduitList;
}
