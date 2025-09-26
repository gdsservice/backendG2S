package com.gds.Gestion.de.stock.Input;

import com.gds.Gestion.de.stock.entites.Utilisateur;
import com.gds.Gestion.de.stock.enums.SupprimerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionINPUT {

    private String idCollection;
    private String sous_titre;
    private String titre;
    private String imageUrl;
    private String btn_text;
    private String btn_link;
    private SupprimerStatus supprimerStatus;
    private boolean publier;
    private Utilisateur utilisateurCollection;
    private List<MultipartFile> images;
    private List<String> imageUrls;
}
