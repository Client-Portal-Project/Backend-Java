package com.projectx.controllers;

import java.util.List;

import com.projectx.Driver;
import com.projectx.models.User;
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

@RestController
@RequestMapping("client")
@CrossOrigin(value = Driver.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ClientController {
    @Autowired
    private ClientService clientServ;

    // Currently, for testing purposes to see the User data in Postman
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
    	return new ResponseEntity<>(clientServ.findAllClients(), HttpStatus.OK);
    } 

    @GetMapping("id/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer clientId) {
        Client client = clientServ.findClientById(clientId);
        if(client != null) {
           return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("name/{companyName}")
    public ResponseEntity<Client> getClientByCompanyName(@PathVariable String companyName) {
        Client client = clientServ.findClientByCompanyName(companyName);
        if(client != null) {
        	return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
        	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client newClient = clientServ.createClient(client);
        if(newClient != null) {
        	return new ResponseEntity<>(newClient, HttpStatus.CREATED);
        } else {
        	return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
    
    @PutMapping
    public ResponseEntity<Client> editClient(@RequestBody Client client) {
    	Client updateClient = clientServ.editClient(client);
    	if(updateClient == null) {
    		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(updateClient, HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteClient(@RequestBody Client client) {
    	boolean deleted = clientServ.deleteClient(client);
    	if(deleted) {
    		return new ResponseEntity<>("Client: " + client + ", was deleted", HttpStatus.OK);
    	}
    	return new ResponseEntity<>("Failed to find Client by Company Name: " + client.getCompanyName(), HttpStatus.NOT_FOUND);
    }

    //Returns a list of all the users in the client
    @GetMapping("user")
    public ResponseEntity<List<User>> getAllClientUsers(@RequestBody Client client) {
        return new ResponseEntity<>(this.clientServ.findAllClientUsers(client), HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getClientUser(@RequestBody Client client, @PathVariable int id) {
        User user = this.clientServ.findClientUser(client, id);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }
    }

    @PutMapping("user/{id}") //id is the userId
    public ResponseEntity<Void> createClientUser(@RequestBody Client client, @PathVariable int id) {
        boolean check = clientServ.createClientUser(client, id);
        if (check) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("user/{id}") //id is the userId
    public ResponseEntity<Void> deleteClientUser(@RequestBody Client client, @PathVariable int id) {
        boolean check = clientServ.deleteClientUser(client, id);
        if (check) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}