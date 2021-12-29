package com.projectx.controller;

import java.util.List;

import com.projectx.model.User;
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
        	return new ResponseEntity<Client>(newClient, HttpStatus.CREATED);
        } else {
        	return new ResponseEntity<String>("Failed to create: " + client + ", it is already exist", HttpStatus.CONFLICT);
        }
    }
    
    @PutMapping("client")
    public ResponseEntity<?> editClient(@RequestBody Client client) {
    	Client updateClient = this.clientServ.editClient(client);
    	if(updateClient == null) {
    		return new ResponseEntity<String>("Failed to find Client by Id: " + client.getClientId(), HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<Client>(updateClient, HttpStatus.OK);
    }
    
    @DeleteMapping("client")
    public ResponseEntity<String> deleteClioent(@RequestBody Client client) {	
    	boolean deleted = this.clientServ.deleteClient(client);
    	if(deleted) {
    		return new ResponseEntity<String>("Client: " + client + ", was deleted", HttpStatus.OK);
    	}
    	return new ResponseEntity<String>("Failed to find Client by Company Name: " + client.getCompanyName(), HttpStatus.NOT_FOUND);
    }

    //Returns a list of all the users in the client
    @GetMapping(value = {"client/user","client/user/{id}"})
    public ResponseEntity<?> getAllClientUsers(@RequestBody Client client, @PathVariable(required = false) String id) {
        if (id != null) { //if null, returns list
            return new ResponseEntity<>(this.clientServ.findAllClientUsers(client), HttpStatus.OK);
        } else { //if not null, searches for a user
            User user = this.clientServ.findClientUser(client, Integer.parseInt(id));
            if (user == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(user, HttpStatus.FOUND);
            }
        }
    }

    @PutMapping("client/user/{id}") //id is the userId
    public ResponseEntity createClientUser(@RequestBody Client client, @PathVariable int id) {
        Boolean check = this.clientServ.createClientUser(client, id);
        if (check) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("client/user/{id}") //id is the userId
    public ResponseEntity deleteClientUser(@RequestBody Client client, @PathVariable int id) {
        Boolean check = this.clientServ.deleteClientUser(client, id);
        if (check) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}