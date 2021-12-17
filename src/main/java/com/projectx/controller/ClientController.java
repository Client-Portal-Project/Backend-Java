package com.projectx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectx.model.Client;
import com.projectx.model.JsonResponse;
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
    public JsonResponse getAllClients() {
        return new JsonResponse(true, "Clients :", this.clientServ.findAllClients());
    }

    @GetMapping("client/id/{clientId}")
    public JsonResponse getClientById(@PathVariable Integer clientId) {
        JsonResponse jsonResponse;
        Client reqClient = this.clientServ.findClientById(clientId);

        if(reqClient != null) {
            jsonResponse = new JsonResponse(true, "Client :", reqClient);
        } else {
            jsonResponse = new JsonResponse(false, "Client with id: " + clientId + " doesn't exist.", null);
        }

        return jsonResponse;
    }

    @GetMapping("client/name/{companyName}")
    public JsonResponse getClientByCompanyName(@PathVariable String companyName) {
        JsonResponse jsonResponse;
        Client reqClient = this.clientServ.findClientByCompanyName(companyName);

        if(reqClient != null) {
            jsonResponse = new JsonResponse(true, "Client :", reqClient);
        } else {
            jsonResponse = new JsonResponse(false, "Client with Company Name: " + companyName + "' doesn't exist.", null);
        }

        return jsonResponse;
    }

    //POST Creating Client
    @PostMapping("client")
    public JsonResponse createClient(@RequestBody Client client) {
        JsonResponse jsonResponse;
        Client newClient = this.clientServ.createClient(client);

        if(newClient != null) {
            jsonResponse = new JsonResponse(true, "Client successfully created.", newClient);
        } else {
            jsonResponse = new JsonResponse(false, "Client '" + client.getCompanyName() + "' already exist.", null);
        }

        return jsonResponse;
    }



}