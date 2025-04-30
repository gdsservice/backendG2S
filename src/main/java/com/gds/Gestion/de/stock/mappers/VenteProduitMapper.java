package com.gds.Gestion.de.stock.mappers;

import com.gds.Gestion.de.stock.DTOs.VenteDTO;
import com.gds.Gestion.de.stock.DTOs.VenteProduitDTO;
import com.gds.Gestion.de.stock.entites.Vente;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class VenteProduitMapper {

    private VenteMapper venteMapper;

    public VenteProduit mapDeDtoAVenteProduit(VenteProduitDTO venteProduitDTO) {
        VenteProduit venteProduit = new VenteProduit();
        BeanUtils.copyProperties(venteProduitDTO, venteProduit);
        return venteProduit;
    }

    public VenteProduitDTO mapDeVenteADTO(VenteProduit venteProduit) {
        VenteProduitDTO venteProduitDTO = new VenteProduitDTO();
        BeanUtils.copyProperties(venteProduit, venteProduitDTO);
        return venteProduitDTO;
    }

//    public VenteProduit mapDeVenteProduitADAO(VenteDTO venteDTO) {
//        VenteProduit venteProduit = new VenteProduit();
//        venteProduit.setVente(venteMapper.mapDeDtoAVente(venteDTO));
//        venteProduit.setProduit(venteProduit.getProduit());
//        System.out.println(venteProduit+"++++++++++++++++++");
//        return venteProduit;
//    }
}
