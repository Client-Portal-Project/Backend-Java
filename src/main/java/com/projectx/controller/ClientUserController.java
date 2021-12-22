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
import com.projectx.DTOs.ClientUserRequestObject;
import com.projectx.utility.CrossOriginUtil;

@RestController
@RequestMapping(value = "/api/v1")
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
		return ResponseEntity.ok(clientUserServ.getAll());
	}
	
	@GetMapping("clientUser/{id}")
	public ResponseEntity<?> getClientUserById(@PathVariable Integer id) {
		return ResponseEntity.ok(clientUserServ.findClientUserById(id));
	}

	
	@PostMapping("clientUser/user/{userId}")
	public ResponseEntity<?> createClientUser(@RequestBody ClientUser clientUser, @PathVariable Integer userId) {

		User user = userServ.findUserById(userId);
		clientUser.setUserId(user);
		ClientUser newClientUser = clientUserServ.createClientUser(clientUser);
		user.setClientUser(newClientUser);

		return ResponseEntity.ok(newClientUser);
	}



}
