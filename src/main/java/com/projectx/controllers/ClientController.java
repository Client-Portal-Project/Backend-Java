package com.projectx.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectx.models.Client;
import com.projectx.services.ClientService;
import com.projectx.utility.CrossOriginUtil;

@RestController
@RequestMapping("client")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ClientController {
    private ClientService clientServ;

    @Autowired
    public ClientController(ClientService clientServ) {
        this.clientServ = clientServ;
    }

    // Currently, for testing purposes to see the User data in Postman
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
    	return new ResponseEntity<List<Client>>(this.clientServ.findAllClients(), HttpStatus.OK);
    } 

    @GetMapping("id/{clientId}")
    public ResponseEntity<?> getClientById(@PathVariable Integer clientId) {
        Client client = this.clientServ.findClientById(clientId);
        if(client != null) {
           return new ResponseEntity<Client>(client, HttpStatus.OK);
        } else {
           return new ResponseEntity<String>("Failed to find Client by id: " + clientId, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("name/{companyName}")
    public ResponseEntity<?> getClientByCompanyName(@PathVariable String companyName) {
        Client client = this.clientServ.findClientByCompanyName(companyName);
        if(client != null) {
        	return new ResponseEntity<Client>(client, HttpStatus.OK);
        } else {
        	return new ResponseEntity<String>("Failed to find Client by Company Name: " + companyName, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        Client newClient = this.clientServ.createClient(client);
        if(newClient != null) {
        	return new ResponseEntity<Client>(newClient, HttpStatus.CREATED);
        } else {
        	return new ResponseEntity<String>("Failed to create: " + client + ", it is already exist", HttpStatus.CONFLICT);
        }
    }
    
    @PutMapping
    public ResponseEntity<?> editClient(@RequestBody Client client) {
    	Client updateClient = this.clientServ.editClient(client);
    	if(updateClient == null) {
    		return new ResponseEntity<String>("Failed to find Client by Id: " + client.getClientId(), HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<Client>(updateClient, HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteClioent(@RequestBody Client client) {	
    	boolean deleted = this.clientServ.deleteClient(client);
    	if(deleted) {
    		return new ResponseEntity<String>("Client: " + client + ", was deleted", HttpStatus.OK);
    	}
    	return new ResponseEntity<String>("Failed to find Client by Company Name: " + client.getCompanyName(), HttpStatus.NOT_FOUND);
    }
}