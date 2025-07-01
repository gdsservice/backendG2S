package com.gds.Gestion.de.stock.entites;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dette_produit")
public class DetteProduit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dette_produit")
    private Long idDetteProduit;
    @Column(length = 100)
    private int montant;
    @Column(length = 10)
    private int quantite;
    @Column(length = 10)
    private int reduction;

    @ManyToOne
    private Produit produit;

    @ManyToOne
    private Dette dette;
}
