package com.gds.Gestion.de.stock.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gds.Gestion.de.stock.DAOs.BannerDAO;
import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.Input.BannerINPUT;
import com.gds.Gestion.de.stock.Input.CollectionINPUT;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.exceptions.ProduitNotFoundException;
import com.gds.Gestion.de.stock.services.InterfaceBanner;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/banner")
public class BannerController {

    private InterfaceBanner interfaceBanner;

    @PostMapping(value = "/addBanner")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public void addBanner(
            @RequestPart("banner") byte[] bannerJsonBytes,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException, EmptyException{

        String bannerJson = new String(bannerJsonBytes, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Supprimez le champ images du JSON avant désérialisation
        JsonNode jsonNode = objectMapper.readTree(bannerJson);
        ((ObjectNode) jsonNode).remove("images");

        BannerINPUT bannerINPUT = objectMapper.treeToValue(jsonNode, BannerINPUT.class);

        interfaceBanner.addBanner(bannerINPUT, images);
    }

    @PutMapping(value = "/modifierBanner/{idBanner}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public void modifierBanner(
            @RequestPart("banner") byte[] bannerJsonBytes,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @PathVariable("idBanner") String idBanner) throws IOException, EmptyException, ProduitNotFoundException {

        String bannerJson = new String(bannerJsonBytes, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Supprimez le champ images du JSON avant désérialisation
        JsonNode jsonNode = objectMapper.readTree(bannerJson);
        ((ObjectNode) jsonNode).remove("images");

        BannerINPUT bannerINPUT = objectMapper.treeToValue(jsonNode, BannerINPUT.class);
        bannerINPUT.setIdBanner(idBanner);

        interfaceBanner.modifierBanner(bannerINPUT, images);
    }

    @GetMapping("/afficherBanner/{idBanner}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private BannerDAO afficherBanner(@Valid @PathVariable String idBanner) throws ProduitNotFoundException {
        return interfaceBanner.afficherBanner(idBanner);
    }

    @GetMapping(value = "/listeBanner", produces = "application/json;charset=UTF-8")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private List<BannerDAO> bannerDAOList(){
        return interfaceBanner.listBanner();
    }

    @PutMapping("/supprimerBanner")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private void supprimerBanner(@Valid @RequestBody String idBanner) throws ProduitNotFoundException{
        interfaceBanner.suppressionBanner(idBanner);
    }
}
