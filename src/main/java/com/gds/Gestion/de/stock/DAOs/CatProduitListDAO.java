package com.gds.Gestion.de.stock.DAOs;

import com.gds.Gestion.de.stock.entites.CategorieStock;
import com.gds.Gestion.de.stock.entites.Produit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatProduitListDAO {
    private CategorieStock categorieStock;
    private List<Produit> produitList;
}
