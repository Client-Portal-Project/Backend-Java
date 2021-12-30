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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectx.models.Client;
import com.projectx.models.ClientUser;
import com.projectx.models.User;
import com.projectx.services.ClientService;
import com.projectx.services.ClientUserService;
import com.projectx.services.UserService;
import com.projectx.utility.ClientUserRequestObject;
import com.projectx.utility.CrossOriginUtil;

@RestController("clientUserController")
@RequestMapping("clientUsers")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ClientUserController {

	@Autowired
	private ClientUserService clientUserServ;

	@Autowired
	private ClientService clientServ;

	@Autowired
	private UserService userServ;
	
	// Currently, for testing purposes to see the User data in Postman
	@GetMapping("/clientUsers")
	public ResponseEntity<List<ClientUser>> getAllClientUsers() {
		return new ResponseEntity<>(this.clientUserServ.findAllClientUsers(), HttpStatus.OK);
	}
	
	//Getting ClientUser by ClientUser ID
	@GetMapping("id/{clientUserId}")
	public ResponseEntity<?> getClientUserById(@PathVariable Integer clientUserId) {
		ClientUser clientUser = this.clientUserServ.findClientUserById(clientUserId);
		if(clientUser != null) return new ResponseEntity<>(clientUser, HttpStatus.OK);
		return new ResponseEntity<>("Failed to find Client User by id: " + clientUserId, HttpStatus.NOT_FOUND);
	}
	
	//Getting ClientUser by User ID
	@GetMapping("user/id/{userId}")
	public ResponseEntity<?> getClientUserByUserId(@PathVariable Integer userId) {
		User user = this.userServ.findUserById(userId);
		ClientUser clientUser = this.clientUserServ.findClientUserByUser(user);
		if(clientUser != null) return new ResponseEntity<>(clientUser, HttpStatus.OK);
		return new ResponseEntity<>("Failed to find Client User by User Id: " + userId, HttpStatus.NOT_FOUND);
	}
	
	//Getting List of ClientUser by Client ID
	@GetMapping("client/id/{clientId}")
	public ResponseEntity<?> getClientUserByClientId(@PathVariable Integer clientId) {
		Client client = this.clientServ.findClientById(clientId);
		List<ClientUser> clientUser = this.clientUserServ.findClientUserByClient(client);
		if(clientUser.size() != 0) return new ResponseEntity<List<ClientUser>>(clientUser, HttpStatus.OK);
		return new ResponseEntity<>("Failed to find Client Users by Client Id: " + clientId, HttpStatus.NOT_FOUND);
	}
	
	//Getting ClientUser by User Email
	@GetMapping("user/email/{userEmail}")
	public ResponseEntity<?> getClientUserByUserEmail(@PathVariable String userEmail) {
		User user = this.userServ.findUserByEmail(userEmail);
		ClientUser clientUser = this.clientUserServ.findClientUserByUser(user);
		if(clientUser != null) return new ResponseEntity<>(clientUser, HttpStatus.OK);
		return new ResponseEntity<>("Failed to find Client User by User Email: " + userEmail, HttpStatus.NOT_FOUND);
	}
	
	//Getting List of ClientUser by Client Company Name
	@GetMapping("client/name/{companyName}")
	public ResponseEntity<?> getClientUserByCompanyName(@PathVariable String companyName) {
		Client client = this.clientServ.findClientByCompanyName(companyName);
		List<ClientUser> clientUser = this.clientUserServ.findClientUserByClient(client);
		if(clientUser.size() != 0) return new ResponseEntity<>(clientUser, HttpStatus.OK);
		return new ResponseEntity<>("Failed to find Client Users by Company Name: " + companyName, HttpStatus.NOT_FOUND);
	}
	
	//Creating ClientUser by using ClientUser entity
	@PostMapping
	public ResponseEntity<?> createClientUser(@RequestBody ClientUser clientUser) {
		//Consistency check
		String companyName = clientUser.getClient().getCompanyName();
		Client client = this.clientServ.findClientByCompanyName(companyName);
		if(client != null) {
			String email = clientUser.getUser().getEmail();
			User user = this.userServ.findUserByEmail(email);
			if(user != null) {
				ClientUser newClientUser = this.clientUserServ.createClientUser(client, user);
				if(newClientUser != null) return new ResponseEntity<ClientUser>(newClientUser, HttpStatus.CREATED);
				return new ResponseEntity<> ("Failed to create Client User: client user'" + clientUser + "', already exist.",HttpStatus.CONFLICT);
			}
			return new ResponseEntity<>("Failed to create Client User: user with email '" + email + "', doesn't exist in the system",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Failed to create Client User: Company Name '" + companyName + "', doesn't exist in the system",HttpStatus.NOT_FOUND);
	}
	
	//Creating ClientUser by using clientUserRequestObject {"email", "companyName"} entity
	@PostMapping("request")
	public ResponseEntity<?> createClientUserByRequestObject(@RequestBody ClientUserRequestObject clientUserRequestObject) {
		//Consistency check
		Client client = this.clientServ.findClientByCompanyName(clientUserRequestObject.getCompanyName());
		if(client != null) {
			User user = this.userServ.findUserByEmail(clientUserRequestObject.getEmail());
			if(user != null) {
				ClientUser newClientUser = this.clientUserServ.createClientUser(client, user);
				if(newClientUser != null) return new ResponseEntity<>(newClientUser, HttpStatus.CREATED);
				return new ResponseEntity<> ("Failed to create Client User: client user with user email'" + clientUserRequestObject.getEmail() + "', already exist.",HttpStatus.CONFLICT);
			}
			return new ResponseEntity<>("Failed to create Client User: user with email '" + clientUserRequestObject.getEmail() + "', doesn't exist in the system",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Failed to create Client User: Company Name '" + clientUserRequestObject.getCompanyName() + "', doesn't exist in the system",HttpStatus.NOT_FOUND);
	}
	
	//Delete ClientUser by using ClientUser entity
	@DeleteMapping
	public ResponseEntity<String> deleteClientUser(@RequestBody ClientUser clientUser) {
		boolean deleted = this.clientUserServ.deleteClientUser(clientUser);
		if(deleted) {
			return new ResponseEntity<>("Client User: " + clientUser + ", was deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("Failed to find Client User with ID: " + clientUser.getClientUserId(), HttpStatus.NOT_FOUND);
	}

}
