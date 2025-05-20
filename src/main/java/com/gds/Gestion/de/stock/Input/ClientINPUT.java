package com.gds.Gestion.de.stock.Input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientINPUT {

    private String idClient;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
}
