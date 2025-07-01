package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.services.ProduitImageImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/images")
public class ProduitImageController {

    private ProduitImageImpl produitImage ;

    @GetMapping("/produit/{idProd}")
    public ResponseEntity<byte[]> getImageByProduitId(
            @PathVariable String idProd,
            @RequestParam(defaultValue = "0") int index) {

        List<byte[]> images = produitImage.getImagesForProduit(idProd);

        if (images.isEmpty() || index < 0 || index >= images.size()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(images.get(index));
    }


    @GetMapping("/produit/main/{idProd}")
    public ResponseEntity<byte[]> getMainImage(@PathVariable String idProd) {
        byte[] image = produitImage.getMainImageForProduit(idProd);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(image);
    }
}
