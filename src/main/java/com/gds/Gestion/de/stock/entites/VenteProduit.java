package com.gds.Gestion.de.stock.entites;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vente_produit")
public class VenteProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private int montant;
    @Column(length = 10)
    private int quantite;
    @Column(length = 10)
    private int reduction;
    @ManyToOne
    private Produit produit;
    @ManyToOne
    private Vente vente;
}
