package com.gds.Gestion.de.stock.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gds.Gestion.de.stock.DAOs.BannerDAO;
import com.gds.Gestion.de.stock.DAOs.CollectionDAO;
import com.gds.Gestion.de.stock.Input.BannerINPUT;
import com.gds.Gestion.de.stock.Input.CollectionINPUT;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.exceptions.ProduitNotFoundException;
import com.gds.Gestion.de.stock.services.InterfaceCollection;
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
@RequestMapping("/collections")
public class CollectionController {

    private InterfaceCollection interfaceCollection;

    @PostMapping(value = "/addCollection")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private void addCollection(
            @RequestPart("collections") byte[] collectionsJsonBytes,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException, EmptyException {
        String collectionJson = new String(collectionsJsonBytes, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Supprimez le champ images du JSON avant désérialisation
        JsonNode jsonNode = objectMapper.readTree(collectionJson);
        ((ObjectNode) jsonNode).remove("images");

        CollectionINPUT collectionINPUT = objectMapper.treeToValue(jsonNode, CollectionINPUT.class);

        interfaceCollection.addCollection(collectionINPUT, images);
    }

    @PutMapping(value = "/modifierCollection/{idCollection}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public void modifierCollection(
            @RequestPart("collections") byte[] collectionJsonBytes,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @PathVariable("idCollection") String idCollection) throws IOException, EmptyException, ProduitNotFoundException {

        String collectionJson = new String(collectionJsonBytes, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Supprimez le champ images du JSON avant désérialisation
        JsonNode jsonNode = objectMapper.readTree(collectionJson);
        ((ObjectNode) jsonNode).remove("images");

        CollectionINPUT collectionINPUT = objectMapper.treeToValue(jsonNode, CollectionINPUT.class);
        collectionINPUT.setIdCollection(idCollection);

        interfaceCollection.modifierCollection(collectionINPUT, images);
    }

    @GetMapping("/afficherCollection/{idCollection}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private CollectionDAO afficherCollection(@Valid @PathVariable String idCollection) throws ProduitNotFoundException {
        return interfaceCollection.afficherCollection(idCollection);
    }

    @GetMapping(value = "/listeCollection", produces = "application/json;charset=UTF-8")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private List<CollectionDAO> collectionDAOList(){
        return interfaceCollection.listCollection();
    }

    @PutMapping("/supprimerCollection")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private void supprimerCollection(@Valid @RequestBody String idCollection) throws ProduitNotFoundException{
        interfaceCollection.suppressionCollection(idCollection);
    }
}
