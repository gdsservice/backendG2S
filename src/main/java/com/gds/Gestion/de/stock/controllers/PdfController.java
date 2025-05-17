package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.DAOs.VenteDAO;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import com.gds.Gestion.de.stock.mappers.VenteMapper;
import com.gds.Gestion.de.stock.repositories.VenteProduitRepository;
import com.gds.Gestion.de.stock.repositories.VenteRepository;
import com.gds.Gestion.de.stock.services.PdfService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pdf")
public class PdfController {
    private final PdfService pdfService;
    private VenteRepository venteRepository;
    private VenteProduitRepository venteProduitRepository;
    private VenteMapper venteMapper;

    @GetMapping("/imprimer/{idVente}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
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

