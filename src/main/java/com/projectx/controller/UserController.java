package com.projectx.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.DTOs.RegisterUserDTO;
import com.projectx.DTOs.ResponseMessage;
import com.projectx.model.User;
import com.projectx.service.*;
import com.projectx.utility.CrossOriginUtil;
import com.projectx.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping(value = "/api/v1")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class UserController {
    private final UserService userService;
    private final OwnerUserService ownerUserService;
    private final ClientUserService clientUserService;
    private final ApplicantService applicantService;
    private final OwnerService ownerService;


    @Autowired
    public UserController(UserService userService, OwnerUserService ownerUserService, ClientUserService clientUserService, ApplicantService applicantService, OwnerService ownerService) {
        this.userService = userService;
        this.ownerUserService = ownerUserService;
        this.clientUserService = clientUserService;
        this.applicantService = applicantService;
        this.ownerService = ownerService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.findAllUsers());
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id){
        User user = userService.findUserById(id);

        if(user == null){
            ResponseMessage message = new ResponseMessage("No user with that id");
            return ResponseEntity.badRequest().body(message);
        }

        return ResponseEntity.ok(user);
    }


    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody RegisterUserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        return ResponseEntity.ok(userService.createUser(user));
    }
}
