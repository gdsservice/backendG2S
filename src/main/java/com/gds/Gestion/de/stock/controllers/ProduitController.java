package com.gds.Gestion.de.stock.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.exceptions.*;
import com.gds.Gestion.de.stock.repositories.ProduitRepository;
import com.gds.Gestion.de.stock.services.InterfaceProduit;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/produit")
public class ProduitController {

    private InterfaceProduit interfaceProduit;

    private ProduitRepository produitRepository;

    @PostMapping(value = "/enregistrerProd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE+";charset=UTF-8")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public void creerProd(
            @RequestPart("produit") String produitJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images)
            throws IOException, EmptyException, MontantQuantiteNullException, ProduitDupicateException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Supprimez le champ images du JSON avant désérialisation
        JsonNode jsonNode = objectMapper.readTree(produitJson);
        ((ObjectNode) jsonNode).remove("images");

        ProduitINPUT produitINPUT = objectMapper.treeToValue(jsonNode, ProduitINPUT.class);

        interfaceProduit.enregistrerProd(produitINPUT, images);
    }

    @PutMapping(value = "/modifierProd/{idProd}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE+";charset=UTF-8")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public void modifierProd(
            @RequestPart("produit") String produitJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @PathVariable("idProd") String idProd) throws IOException, EmptyException, ProduitNotFoundException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Supprimez le champ images du JSON avant désérialisation
        JsonNode jsonNode = objectMapper.readTree(produitJson);
        ((ObjectNode) jsonNode).remove("images");

        ProduitINPUT produitINPUT = objectMapper.treeToValue(jsonNode, ProduitINPUT.class);
        produitINPUT.setIdProd(idProd);

        interfaceProduit.modifierProd(produitINPUT, images);
    }


    @PutMapping("/supprimerProd")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private void supprimerProd(@Valid @RequestBody String idProd) throws ProduitNotFoundException{
        interfaceProduit.suppressionProd(idProd);
    }

    @GetMapping(value = "/listeProd", produces = "application/json;charset=UTF-8")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private List<ProduitDAO> produitDTOList(){
        return interfaceProduit.ListerProd();
    }

    @GetMapping("/afficherProd/{idProd}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    private ProduitDAO afficherProd(@Valid @PathVariable String idProd) throws ProduitNotFoundException{
        return interfaceProduit.afficherProd(idProd);
    }

    @GetMapping("/slug/{slug}")
    private ProduitDAO afficherSlug(@Valid @PathVariable String slug) throws ProduitNotFoundException, EmptyException {
        return interfaceProduit.afficherSlug(slug);
    }


    @GetMapping("/recherche")
    public List<ProduitDAO> rechercherProduits(@RequestParam Map<String, String> params) {
        return interfaceProduit.rechercherProduits(params);
    }

    @GetMapping("/limite")
    public List<ProduitDAO> listProduitLimit(
            @RequestParam(name = "min", defaultValue = "1") int min,
            @RequestParam(name = "max", defaultValue = "20") int max
    ){
       return interfaceProduit.listProduitLimit(min, max);
    }






}
