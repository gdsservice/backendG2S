package com.gds.Gestion.de.stock.DAOs;

import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerDAO {

    private String idBanner;
    private String sous_titre;
    private String titre;
    private String btn_text;
    private String btn_link;
    private SupprimerStatus supprimerStatus;
    private boolean publier;
    private MultipartFile image;
    private String imageUrl;
    private Utilisateur utilisateurBanner;
}
