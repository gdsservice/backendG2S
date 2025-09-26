package com.gds.Gestion.de.stock.entites;

import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collection")
public class Collections {

    @Id
    @Column(name = "id_collection")
    private String idCollection;
    private String sous_titre;
    private String titre;
    private String imageUrl;
    private String btn_text;
    private String btn_link;
    @Enumerated(EnumType.STRING)
    @Column(name = "supprimer_status")
    private SupprimerStatus supprimerStatus;
    private boolean publier;

    @ManyToOne
    private Utilisateur utilisateurCollection;
}
