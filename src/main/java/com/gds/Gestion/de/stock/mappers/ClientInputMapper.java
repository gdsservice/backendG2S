package com.gds.Gestion.de.stock.mappers;


import com.gds.Gestion.de.stock.DTOs.ClientDTO;
import com.gds.Gestion.de.stock.Input.ClientINPUT;
import com.gds.Gestion.de.stock.entites.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientInputMapper {


//    convertir de DTO a client input
    public ClientINPUT mapDeDtoAClientInput(ClientDTO clientDTO) {
        ClientINPUT clientInput = new ClientINPUT();
        BeanUtils.copyProperties(clientDTO, clientInput);
        return clientInput;
    }

//    convertir de client a client input
    public ClientINPUT mapDeClientAClientInput(Client client) {
        ClientINPUT clientInput = new ClientINPUT();
        BeanUtils.copyProperties(client, clientInput);
        return clientInput;
    }

//    convertir de client input a client
    public Client mapDeClientInputAClient(ClientINPUT clientInput) {
        Client client = new Client();
        BeanUtils.copyProperties(clientInput, client);
        return client;
    }



}
