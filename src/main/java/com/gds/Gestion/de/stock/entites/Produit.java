package com.gds.Gestion.de.stock.entites;

import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produit")
public class Produit {

    @Id
    @Column(name = "id_prod")
    private String idProd;
    @Column(length = 60)
    private String designation;
    @Column(length = 10)
    private int quantite;
    @Column(name = "prix_unitaire",length = 100)
    private int prixUnitaire;
    @Column(length = 100)
    private int montant;
    private LocalDate date;
    @Column(length = 255)
    private String note;
    @Enumerated(EnumType.STRING)
    @Column(name = "supprimer_status")
    private SupprimerStatus supprimerStatus;

    // Nouveaux champs
    @Column(unique = true)
    private String slug;
    @Column(name = "prix_regulier")
    private String prixRegulier;

    @Column(length = 1000)
    private String description;

    private boolean nouveaute;
    private boolean vedette;
    @Column(name = "offre_speciale")
    private boolean offreSpeciale;
    @Column(name = "plus_vendu")
    private boolean plusVendu;
    private boolean publier;

    @ManyToOne
    private Utilisateur utilisateurProd;

    @ManyToOne
    private CategorieStock categorieStock;


}
