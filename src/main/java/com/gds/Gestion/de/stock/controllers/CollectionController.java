package com.gds.Gestion.de.stock.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gds.Gestion.de.stock.Input.CollectionINPUT;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.services.InterfaceCollection;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
}
