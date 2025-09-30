package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.services.InterfaceCollectionImage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/images")
public class CollectionImageController {

    private InterfaceCollectionImage internetCollectionImage;

    @GetMapping("/collections/{idCollection}")
    public ResponseEntity<byte[]> getImageByCollectionId(
            @PathVariable String idCollection,
            @RequestParam(defaultValue = "0") int index) {

        List<byte[]> images = internetCollectionImage.getImagesForCollection(idCollection);

        if (images.isEmpty() || index < 0 || index >= images.size()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(images.get(index));
    }


    @GetMapping("/collections/main/{idCollection}")
    public ResponseEntity<byte[]> getMainImage(@PathVariable String idCollection) {
        byte[] image = internetCollectionImage.getMainImageForCollection(idCollection);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(image);
    }
}
