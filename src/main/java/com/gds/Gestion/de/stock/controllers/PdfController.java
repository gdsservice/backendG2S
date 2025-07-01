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

import javax.print.*;
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

    }

//    @GetMapping("/imprimer/{idVente}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
//    public ResponseEntity<String> imprimerTicket(@PathVariable String idVente) {
//        List<VenteProduit> venteProduitList = venteProduitRepository.findByVenteIdVente(idVente);
//
//        VenteDAO venteDAO = new VenteDAO();
//        venteDAO.setVente(venteProduitList.get(0).getVente());
//        venteDAO.setVenteProduitList(venteProduitList);
//
//        String ticketTexte = pdfService.generateTicketTexte(venteDAO);
//
//        // Impression en local
//        try {
//            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
//            if (printService != null) {
//                DocPrintJob job = printService.createPrintJob();
//                Doc doc = new SimpleDoc(ticketTexte.getBytes(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
//                job.print(doc, null);
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body("Aucune imprimante trouvée.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Erreur d'impression : " + e.getMessage());
//        }
//
//        return ResponseEntity.ok("Ticket imprimé avec succès.");
//    }



}

