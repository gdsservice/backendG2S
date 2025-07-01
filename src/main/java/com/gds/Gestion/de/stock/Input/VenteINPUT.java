package com.gds.Gestion.de.stock.Input;

import com.gds.Gestion.de.stock.entites.Vente;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenteINPUT {
    private Vente vente;
    private List<VenteProduit> listVenteProduit;
}
