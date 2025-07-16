package com.gds.Gestion.de.stock.DAOs;

import com.gds.Gestion.de.stock.entites.Commande;
import com.gds.Gestion.de.stock.entites.CommandeProduit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeDAO {

    private Commande commande;
    private List<CommandeProduit> commandeProduitList;

}
