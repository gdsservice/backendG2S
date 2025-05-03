package com.gds.Gestion.de.stock.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gds.Gestion.de.stock.DAOs.ProduitDAO;
import com.gds.Gestion.de.stock.DTOs.CategorieStockDTO;
import com.gds.Gestion.de.stock.DTOs.ProduitDTO;
import com.gds.Gestion.de.stock.Input.ProduitINPUT;
import com.gds.Gestion.de.stock.entites.Produit;
import com.gds.Gestion.de.stock.exceptions.*;
import com.gds.Gestion.de.stock.repositories.ProduitRepository;
import com.gds.Gestion.de.stock.services.InterfaceProduit;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/produit")
public class ProduitController {

    private InterfaceProduit interfaceProduit;

    private ProduitRepository produitRepository;


    /*@PostMapping("/enregistrerProd")
    private ProduitDTO creerProd(@Valid @RequestBody ProduitDTO produitDTO) throws MontantQuantiteNullException, ProduitDupicateException, EmptyException {
        return interfaceProduit.enregistrerProd(produitDTO);
    }*/

    @PostMapping(value = "/enregistrerProd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void creerProd(@RequestPart("produit") String produitJson,
                                @RequestPart(value = "image", required = false) MultipartFile image)
            throws IOException, EmptyException, MontantQuantiteNullException, ProduitDupicateException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ProduitINPUT produitINPUT = objectMapper.readValue(produitJson, ProduitINPUT.class);

        if (image != null && !image.isEmpty()) {
            produitINPUT.setImage(image);
        }

         interfaceProduit.enregistrerProd(produitINPUT);
    }

/*    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        Produit produit = produitRepository.findById(id).orElseThrow();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(produit.getImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + produit.getImageName() + "\"")
                .body(produit.getImageData());
    }*/

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        String contentType = produit.getImageType();
        if (contentType == null || contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + produit.getImageName() + "\"")
                .body(produit.getImageData());
    }


    @PutMapping("/modifierProd/{idProd}")
    private void modifierProd(@Valid @RequestBody ProduitINPUT produitINPUT, @PathVariable("idProd") String idProd) throws  EmptyException {
        produitINPUT.setIdProd(idProd);
         interfaceProduit.modifierProd(produitINPUT);
    }

    @PutMapping("/supprimerProd")
    private void supprimerProd(@Valid @RequestBody String idProd) throws ProduitNotFoundException{
        interfaceProduit.suppressionProd(idProd);
    }

    @GetMapping("/listeProd")
    private List<ProduitDAO> produitDTOList(){
        return interfaceProduit.ListerProd();
    }

    @GetMapping("/afficherProd/{idProd}")
    private ProduitDAO afficherProd(@Valid @PathVariable String idProd) throws ProduitNotFoundException{
        ProduitDAO produitDAO = interfaceProduit.afficherProd(idProd);
        System.out.println(produitDAO);
        return produitDAO;
    }


}
