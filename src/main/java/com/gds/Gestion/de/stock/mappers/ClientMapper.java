package com.gds.Gestion.de.stock.mappers;


import com.gds.Gestion.de.stock.DTOs.ClientDTO;
import com.gds.Gestion.de.stock.Input.ClientINPUT;
import com.gds.Gestion.de.stock.entites.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {

    @Autowired
    private UtilisateurMapper utilisateurMapper;


    public Client mapDeDtoAClient(ClientDTO clientDTO) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        return client;
    }

    public ClientDTO mapDeClientADto(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client, clientDTO);
        return clientDTO;
    }

    public Client mapDeClientInputAClient(ClientINPUT clientInput) {
        Client client = new Client();
        BeanUtils.copyProperties(clientInput, client);
        return client;
    }

}
