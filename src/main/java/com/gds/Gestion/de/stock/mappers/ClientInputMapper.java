package com.gds.Gestion.de.stock.mappers;


import com.gds.Gestion.de.stock.DTOs.ClientDTO;
import com.gds.Gestion.de.stock.Input.ClientInput;
import com.gds.Gestion.de.stock.entites.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientInputMapper {


//    convertir de DTO a client input
    public ClientInput mapDeDtoAClientInput(ClientDTO clientDTO) {
        ClientInput clientInput = new ClientInput();
        BeanUtils.copyProperties(clientDTO, clientInput);
        return clientInput;
    }

//    convertir de client a client input
    public ClientInput mapDeClientAClientInput(Client client) {
        ClientInput clientInput = new ClientInput();
        BeanUtils.copyProperties(client, clientInput);
        return clientInput;
    }

//    convertir de client input a client
    public Client mapDeClientInputAClient(ClientInput clientInput) {
        Client client = new Client();
        BeanUtils.copyProperties(clientInput, client);
        return client;
    }



}
