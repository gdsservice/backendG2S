package com.gds.Gestion.de.stock.entites;

import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id_client")
    private String idClient;
    @Column(length = 60)
    private String nom;
    @Column(length = 60)
    private String prenom;
    @Column(length = 80)
    private String adresse;
    @Column(unique = true,length = 8)
    private String telephone;
    @Column(length = 80)
    private String email;
    @Column(name = "date_ajout")
    private LocalDate dateAjout;
    @Enumerated(EnumType.STRING)
    @Column(name = "supprimer_status")
    private SupprimerStatus supprimerStatus;
    private boolean publier;

    @ManyToOne
    private Utilisateur utilisateurClient;
}
