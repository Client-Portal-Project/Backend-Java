package com.projectx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.model.Client;
import com.projectx.repository.ClientDao;

import lombok.extern.slf4j.Slf4j;

@Service("clientService")
@Slf4j //Using Self4j logger from lombok
public class ClientService {
    private ClientDao clientDao;

    @Autowired
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    // Currently, for testing purposes to see the User data in Postman
    public List<Client> findAllClients() {
        log.info("clientService: findAllClients() call");
        return this.clientDao.findAll();
    }

    public Client findClientById(Integer clientId) {
        log.info("clientService: findClientById() call");
        Client client = this.clientDao.findById(clientId).orElse(null);
        if (client == null) log.error("clientService: client with id " + clientId + " not found.");
        return client;
    }

    public Client findClientByCompanyName(String companyName) {
        log.info("clientService: findClientByCompanyName() call");
        Client client = this.clientDao.findClientByCompanyName(companyName);
        if (client == null) log.error("clientService: client with companyName " + companyName + " not found.");
        return client;
    }

    public Client createClient(Client client) {
      return clientDao.save(client);
    }



}