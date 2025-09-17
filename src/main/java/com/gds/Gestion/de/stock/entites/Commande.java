package com.gds.Gestion.de.stock.entites;

import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commande")
public class Commande {

    @Id
    @Column(name = "id_cde")
    private String idCde;
    @Column(length = 100)
    private int quantite;
    @Column(length = 100)
    private int total;
    @Enumerated(EnumType.STRING)
    @Column(name = "supprimer_status")
    private SupprimerStatus supprimerStatus;
    @Column(name = "date_ajout")
    private LocalDate dateAjout;
    @Column(nullable = false)
    private boolean traiter;
    @Column(length = 2560,nullable = true)
    private String note;


    @ManyToOne
    @JoinColumn(name = "client_cde_id")
    private Client clientCde;

}
