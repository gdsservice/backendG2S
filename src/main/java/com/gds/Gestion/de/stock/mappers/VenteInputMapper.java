package com.gds.Gestion.de.stock.mappers;

import com.gds.Gestion.de.stock.DTOs.VenteDTO;
import com.gds.Gestion.de.stock.Input.VenteInput;
import com.gds.Gestion.de.stock.entites.Vente;
import com.gds.Gestion.de.stock.entites.VenteProduit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenteInputMapper {

    @Autowired
    private ClientMapper clientMapper;
    private ClientInputMapper clientInputMapper;

//    public VenteInput mapDeDtoAVenteInput(VenteDTO venteDTO) {
//        VenteInput venteInput = new VenteInput();
//        BeanUtils.copyProperties(venteDTO, venteInput);
//        venteInput.setClientDTO(clientMapper.mapDeDtoAClient(venteDTO.getClientDTO()));
//
//        return venteInput;
//    }
//
//    public VenteDTO mapDeVenteInputADTO(VenteInput venteInput) {
//        VenteDTO venteDTO = new VenteDTO();
//        BeanUtils.copyProperties(venteInput, venteDTO);
//        venteDTO.setClientDTO(clientMapper.mapDeClientADto(venteInput.getVente().getClientsVente()));
//        return venteDTO;
//    }
//
//    public VenteInput mapDeVenteAVenteInput(Vente vente) {
//        VenteInput venteInput = new VenteInput();
//        BeanUtils.copyProperties(vente, venteInput);
//        venteInput.getVente().setClientsVente(vente.getClientsVente());
//
//        return venteInput;
//    }

    public Vente mapDeVenteInputAVente(VenteInput venteInput) {
        Vente vente = new Vente();
        BeanUtils.copyProperties(venteInput, vente);
        vente.setClientsVente(venteInput.getVente().getClientsVente());
        return vente;
    }

//    public VenteDTO mapDeVenteADTO(VenteInput venteInput) {
//        VenteDTO venteDTO = new VenteDTO();
//        BeanUtils.copyProperties(venteInput, venteDTO);
//        venteDTO.setClientDTO(clientMapper.mapDeClientADto());
//        return venteDTO;
//    }
}
