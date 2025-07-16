package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.DAOs.CommandeDAO;
import com.gds.Gestion.de.stock.Input.CommandeInput;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.services.InterfaceCommande;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/commande")
public class CommandeController {

    private InterfaceCommande interfaceCommande;

    @PostMapping("/effectuerCde")
    private void saveCde(@Valid @RequestBody CommandeInput commandeInput) throws EmptyException {
      interfaceCommande.effectuer(commandeInput);
    }

    @GetMapping("/listCommande")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    private List<CommandeDAO> commandeDAOList(){
        return interfaceCommande.listCommande();
    }

    @GetMapping("/listCommandeTraiter")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','USER')")
    private List<CommandeDAO> commandeDAOListTraiter(){
        return interfaceCommande.listCommandeTraiterFalse();
    }
}
