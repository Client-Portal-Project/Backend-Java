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
import com.projectx.model.ClientUser;
import com.projectx.model.User;
import com.projectx.service.ClientService;
import com.projectx.service.ClientUserService;
import com.projectx.service.UserService;
import com.projectx.utility.ClientUserRequestObject;
import com.projectx.utility.CrossOriginUtil;

@RestController("clientUserController")
@RequestMapping(value = "api")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ClientUserController {
	private ClientUserService clientUserServ;
	private ClientService clientServ;
	private UserService userServ;
	
	@Autowired
	public ClientUserController(ClientUserService clientUserServ, ClientService clientServ,  UserService userServ) {
		this.clientUserServ = clientUserServ;
		this.clientServ = clientServ;
		this.userServ = userServ;
	}
	
	// Currently, for testing purposes to see the User data in Postman
	@GetMapping("clientUsers")
	public ResponseEntity<List<ClientUser>> getAllClientUsers() {
		return new ResponseEntity<List<ClientUser>>(this.clientUserServ.findAllClientUsers(), HttpStatus.OK);
	}
	
	@GetMapping("clientUser/id/{clientUserId}")
	public ResponseEntity<?> getClientUserById(@PathVariable Integer clientUserId) {
		ClientUser clientUser = this.clientUserServ.findClientUserById(clientUserId);
		if(clientUser != null) return new ResponseEntity<ClientUser>(clientUser, HttpStatus.OK);
		return new ResponseEntity<String>("Failed to find Client User by id: " + clientUserId, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("clientUser/user/id/{userId}")
	public ResponseEntity<?> getClientUserByUserId(@PathVariable Integer userId) {
		User user = this.userServ.findUserById(userId);
		ClientUser clientUser = this.clientUserServ.findClientUserByUser(user);
		if(clientUser != null) return new ResponseEntity<ClientUser>(clientUser, HttpStatus.OK);
		return new ResponseEntity<String>("Failed to find Client User by User Id: " + userId, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("clientUser/client/id/{clientId}")
	public ResponseEntity<?> getClientUserByClientId(@PathVariable Integer clientId) {
		Client client = this.clientServ.findClientById(clientId);
		List<ClientUser> clientUser = this.clientUserServ.findClientUserByClient(client);
		if(clientUser.size() != 0) return new ResponseEntity<List<ClientUser>>(clientUser, HttpStatus.OK);
		return new ResponseEntity<String>("Failed to find Client Users by Client Id: " + clientId, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("clientUser/user/email/{userEmail}")
	public ResponseEntity<?> getClientUserByUserId(@PathVariable String userEmail) {
		User user = this.userServ.findUserByEmail(userEmail);
		ClientUser clientUser = this.clientUserServ.findClientUserByUser(user);
		if(clientUser != null) return new ResponseEntity<ClientUser>(clientUser, HttpStatus.OK);
		return new ResponseEntity<String>("Failed to find Client User by User Email: " + userEmail, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("clientUser/client/name/{companyName}")
	public ResponseEntity<?> getClientUserByCompanyName(@PathVariable String companyName) {
		Client client = this.clientServ.findClientByCompanyName(companyName);
		List<ClientUser> clientUser = this.clientUserServ.findClientUserByClient(client);
		if(clientUser.size() != 0) return new ResponseEntity<List<ClientUser>>(clientUser, HttpStatus.OK);
		return new ResponseEntity<String>("Failed to find Client Users by Company Name: " + companyName, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("clientUser")
	public ResponseEntity<?> createClientUser(@RequestBody ClientUserRequestObject clientUserRequestObject) {
		Client client = this.clientServ.findClientByCompanyName(clientUserRequestObject.getCompanyName());
		if(client != null) {
			User user = this.userServ.findUserByEmail(clientUserRequestObject.getEmail());
			if(user != null) {
				ClientUser clientUser = this.clientUserServ.createClientUser(client, user);
				if(clientUser != null) return new ResponseEntity<ClientUser>(clientUser, HttpStatus.CREATED);
				return new ResponseEntity<String> ("Failed to create Client User: client user'" + clientUser + "', already exist.",HttpStatus.CONFLICT);
			}
			return new ResponseEntity<String>("Failed to create Client User: user with email '" + clientUserRequestObject.getEmail() + "', doesn't exist in the system",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Failed to create Client User: Company Name '" + clientUserRequestObject.getCompanyName() + "', doesn't exist in the system",HttpStatus.NOT_FOUND);
	}
}