package com.projectx.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.models.Client;
import com.projectx.repositories.ClientDao;

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
    	log.info("clientService: createClient() call");
        Client temp = this.clientDao.findClientByCompanyName(client.getCompanyName());
        if(temp != null) {
            log.error("clientService: " + temp + " , already exist.");
            return null;
        } else {
            Client result = this.clientDao.save(client);
            log.info("clientService: " + result + " , successfully created.");
            return result;
        }
    }
    
    public Client editClient(Client client) {
    	log.info("clientService: updateClient() call");
    	Client temp = this.clientDao.findById(client.getClientId()).orElse(null);
    	if(temp == null) {
    		 log.error("clientService: Client with id '" + client.getClientId() + "' , doesn't exist.");
    		 return null;
    	} else {
    		temp.setCompanyName(client.getCompanyName());
    		Client updated = this.clientDao.save(temp);
    		log.info("clientService: Client was successfully updated to '" + updated +"'." );
    		return updated;
    	}
    }
    
    public boolean deleteClient(Client client) {
    	log.info("clientService: deleteClient() call");
    	Client temp = this.clientDao.findClientByCompanyName(client.getCompanyName());
    	if(temp == null) {
   		 	log.error("clientService: " + temp + " , doesn't exist.");
   		 	return false;
    	} else {
    		this.clientDao.delete(temp);
    		log.info("clientService: " + temp + " , have been deleted.");
    		return true;
    	}
    }


}