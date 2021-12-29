package com.projectx.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.models.Client;
import com.projectx.models.ClientUser;
import com.projectx.models.User;
import com.projectx.repositories.ClientUserDao;

import lombok.extern.slf4j.Slf4j;

@Service("clientUserService")
@Slf4j
public class ClientUserService {
	private ClientUserDao clientUserDao;
	
	@Autowired
	public ClientUserService(ClientUserDao clientUserDao) {
		this.clientUserDao = clientUserDao;
	}
	
	// Currently, for testing purposes to see the User data in Postman
	public List<ClientUser> findAllClientUsers() {
		log.info("clientService: findAllClientUsers() call");
		return this.clientUserDao.findAll();
	}
	
	public ClientUser findClientUserById(Integer id) {
		log.info("clientService: findClientUserById() call");
		return this.clientUserDao.findById(id).orElse(null);
	}
	
	public List<ClientUser> findClientUserByClient(Client client) {
		log.info("clientService: findClientUserByClient() call");
		return this.clientUserDao.findClientUserByClient(client);
	}
	
	public ClientUser findClientUserByUser(User user) {
		log.info("clientService: findClientUserByUser() call");
		return this.clientUserDao.findClientUserByUser(user);
	}
	
	public ClientUser createClientUser(Client client, User user) {
		log.info("clientService: createClientUser() call");
		ClientUser checkClientUser = this.clientUserDao.findClientUserByUser(user);
		if(checkClientUser == null) {
			ClientUser clientUser = this.clientUserDao.save(new ClientUser(user, client));
			log.info("clientService: " + clientUser + " , successfully created.");
			return clientUser;
		} else {
			log.error("clientService: " + checkClientUser + " , already exist.");
			return null;
		}
	}
	
	public boolean deleteClientUser(ClientUser clientUser) {
		log.info("clientService: deleteClientUser() call");
		ClientUser delete = this.clientUserDao.findById(clientUser.getClientUserId()).orElse(null);
		if (delete != null) {
			this.clientUserDao.delete(delete);
			log.info("clientService: " + delete + " , successfully deleted.");
			return true;
		}
		log.error("clientService: " + clientUser + " , doesn't exist.");
		return false;
	}
	
	
}
