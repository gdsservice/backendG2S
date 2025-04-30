package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.DAO.VenteDAO;
import com.gds.Gestion.de.stock.DTOs.VenteDTO;
import com.gds.Gestion.de.stock.entites.Produit;
import com.gds.Gestion.de.stock.entites.Vente;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import com.gds.Gestion.de.stock.exceptions.VenteNotFoundException;
import com.gds.Gestion.de.stock.mappers.VenteMapper;
import com.gds.Gestion.de.stock.mappers.VenteProduitMapper;
import com.gds.Gestion.de.stock.repositories.VenteProduitRepository;
import com.gds.Gestion.de.stock.repositories.VenteRepository;
import com.gds.Gestion.de.stock.services.InterfaceVente;
import com.gds.Gestion.de.stock.services.PdfService;
import com.gds.Gestion.de.stock.services.VenteImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping("/pdf")
public class PdfController {
    private final PdfService pdfService;
    private VenteRepository venteRepository;
    private VenteProduitRepository venteProduitRepository;
    private VenteMapper venteMapper;

    @GetMapping("/imprimer/{idVente}")
    public ResponseEntity<byte[]> generateVentePdf(@PathVariable String idVente) {
        List<VenteProduit> venteProduitList = venteProduitRepository.findByVenteIdVente(idVente);

            VenteDAO venteDAO = new VenteDAO();
            venteDAO.setVente(venteProduitList.get(0).getVente());
            venteDAO.setVenteProduitList(venteProduitList);
            byte[] pdfBytes = pdfService.generateVentePdf(venteDAO);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=vente_" + idVente + ".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

    }}

