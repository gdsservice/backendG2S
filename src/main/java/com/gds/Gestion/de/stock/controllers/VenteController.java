package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.DAO.VenteDAO;
import com.gds.Gestion.de.stock.DTOs.VenteDTO;
import com.gds.Gestion.de.stock.Input.VenteInput;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import com.gds.Gestion.de.stock.exceptions.*;
import com.gds.Gestion.de.stock.services.InterfaceVente;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vente")
public class VenteController {

//    test
    private final InterfaceVente interfaceVente;

    @PostMapping("/effectuerVente")
    public void effectuerVente(@Valid @RequestBody VenteInput venteInput) throws Exception {
        interfaceVente.effectuerVente(venteInput);
    }

    @PutMapping("/modifier/{idVente}")
    public void modifierVente(@Valid @RequestBody VenteDTO venteDTO, @PathVariable("idVente") String idVente) throws Exception {
        venteDTO.setIdVente(idVente);
        interfaceVente.modifierVente(venteDTO);
    }

    @GetMapping("/afficherVente")
    public VenteDAO afficherVente(@Valid @RequestParam("idVente") String idVente) throws Exception {
        return interfaceVente.afficherVente(idVente);
    }

    @GetMapping("/listeVente")
    public List<VenteDAO> listeVente() {
        return interfaceVente.listerVente();
    }

    @GetMapping("/totalVente")
    public long totalVente() {
        return interfaceVente.totalVente();
    }

    @PostMapping("/annulerVente")
    private void annuler(@Valid @RequestBody VenteDTO venteDTO) throws EmptyException {
         interfaceVente.annulerVente(venteDTO);
    };

    @PutMapping("/supprimerVente")
    private void supprimer(@Valid @RequestBody String venteId) throws VenteNotFoundException {
        interfaceVente.supprimerVente(venteId);
    };
}
