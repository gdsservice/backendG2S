package com.gds.Gestion.de.stock.controllers;

import com.gds.Gestion.de.stock.Input.CommandeInput;
import com.gds.Gestion.de.stock.exceptions.EmptyException;
import com.gds.Gestion.de.stock.services.InterfaceCommande;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/commande")
public class CommandeController {

    private InterfaceCommande interfaceCommande;

    @PostMapping("/effectuerCde")
    private void saveCde(@Valid @RequestBody CommandeInput commandeInput) throws EmptyException {
        System.out.println(commandeInput);
        System.out.println("++++++++++++++++++++++++++++++");
      interfaceCommande.effectuer(commandeInput);
    }
}
