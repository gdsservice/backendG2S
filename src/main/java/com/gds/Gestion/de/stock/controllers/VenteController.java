package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.DAOs.VenteDAO;
import com.gds.Gestion.de.stock.DTOs.VenteDTO;
import com.gds.Gestion.de.stock.Input.VenteINPUT;
import com.gds.Gestion.de.stock.exceptions.*;
import com.gds.Gestion.de.stock.services.InterfaceVente;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vente")
public class VenteController {

//    test
    private final InterfaceVente interfaceVente;

    @PostMapping("/effectuerVente")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    public void effectuerVente(@Valid @RequestBody VenteINPUT venteInput) throws Exception {
        interfaceVente.effectuerVente(venteInput);
    }

    @PutMapping("/modifier/{idVente}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    public void modifierVente(@Valid @RequestBody VenteDTO venteDTO, @PathVariable("idVente") String idVente) throws Exception {
        venteDTO.setIdVente(idVente);
        interfaceVente.modifierVente(venteDTO);
    }

    @GetMapping("/afficherVente")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    public VenteDAO afficherVente(@Valid @RequestParam("idVente") String idVente) throws Exception {
        return interfaceVente.afficherVente(idVente);
    }

    @GetMapping("/listeVente")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    public List<VenteDAO> listeVente() {
        return interfaceVente.listerVente();
    }

    @GetMapping("/totalVente")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    public long totalVente() {
        return interfaceVente.totalVente();
    }

    @PostMapping("/annulerVente")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    private void annuler(@Valid @RequestBody VenteDTO venteDTO) throws EmptyException {
         interfaceVente.annulerVente(venteDTO);
    };

    @PutMapping("/supprimerVente")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    private void supprimer(@Valid @RequestBody String venteId) throws VenteNotFoundException {
        interfaceVente.supprimerVente(venteId);
    };
}
