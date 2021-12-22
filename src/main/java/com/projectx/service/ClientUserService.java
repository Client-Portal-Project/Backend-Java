package com.projectx.service;

import java.util.List;

import com.projectx.exceptions.ResourceDoesNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.model.ClientUser;
import com.projectx.repository.ClientUserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientUserService {
	private ClientUserRepo clientUserRepo;
	
	@Autowired
	public ClientUserService(ClientUserRepo clientUserRepo) {
		this.clientUserRepo = clientUserRepo;
	}
	
	// Currently, for testing purposes to see the User data in Postman
	public List<ClientUser> getAll() {
		log.info("clientService: findAllClientUsers() call");
		return clientUserRepo.findAll();
	}
	
	public ClientUser findClientUserById(Integer id) {
		log.info("clientService: findClientUserById() call");
		return clientUserRepo.findById(id).orElseThrow(()-> new ResourceDoesNotExist("No ClientUser with Id: "+ id));
	}

	
	public ClientUser createClientUser(ClientUser clientUser) {
return clientUserRepo.save(clientUser);
	}
	
	
}
