package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.services.InternetBannerImage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/images")
public class BannerImageController {

    private InternetBannerImage internetBannerImage;

    @GetMapping("/banner/{idBanner}")
    public ResponseEntity<byte[]> getImageByBannerId(
            @PathVariable String idBanner,
            @RequestParam(defaultValue = "0") int index) {

        List<byte[]> images = internetBannerImage.getImagesForBanner(idBanner);

        if (images.isEmpty() || index < 0 || index >= images.size()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(images.get(index));
    }


    @GetMapping("/banner/main/{idBanner}")
    public ResponseEntity<byte[]> getMainImage(@PathVariable String idBanner) {
        byte[] image = internetBannerImage.getMainImageForBanner(idBanner);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(image);
    }
}
