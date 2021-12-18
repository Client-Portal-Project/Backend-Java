package com.projectx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectx.model.Client;
import com.projectx.service.ClientService;
import com.projectx.utility.CrossOriginUtil;

@RestController("clientController")
@RequestMapping(value = "api")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ClientController {
    private ClientService clientServ;

    @Autowired
    public ClientController(ClientService clientServ) {
        this.clientServ = clientServ;
    }

    // Currently, for testing purposes to see the User data in Postman
    @GetMapping("clients")
    public ResponseEntity<List<Client>> getAllClients() {
    	return new ResponseEntity<List<Client>>(this.clientServ.findAllClients(), HttpStatus.OK);
    } 

    @GetMapping("client/id/{clientId}")
    public ResponseEntity<?> getClientById(@PathVariable Integer clientId) {
        Client client = this.clientServ.findClientById(clientId);
        if(client != null) {
           return new ResponseEntity<Client>(client, HttpStatus.OK);
        } else {
           return new ResponseEntity<String>("Failed to find Client by id: " + clientId, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("client/name/{companyName}")
    public ResponseEntity<?> getClientByCompanyName(@PathVariable String companyName) {
        Client client = this.clientServ.findClientByCompanyName(companyName);
        if(client != null) {
        	return new ResponseEntity<Client>(client, HttpStatus.OK);
        } else {
        	return new ResponseEntity<String>("Failed to find Client by Company Name: " + companyName, HttpStatus.NOT_FOUND);
        }
    }

    //POST Creating Client
    @PostMapping("client")
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        Client newClient = this.clientServ.createClient(client);
        if(newClient != null) {
        	return new ResponseEntity<Client>(newClient, HttpStatus.OK);
        } else {
        	return new ResponseEntity<String>("Failed to create: " + client + ", it is already exist", HttpStatus.CONFLICT);
        }
    }



}