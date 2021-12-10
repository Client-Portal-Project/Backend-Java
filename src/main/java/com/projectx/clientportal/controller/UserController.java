package com.projectx.clientportal.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.clientportal.model.JsonResponse;
import com.projectx.clientportal.model.User;
import com.projectx.clientportal.service.UserService;
import com.projectx.clientportal.utility.CrossOriginUtil;
import com.projectx.clientportal.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("userController")
@RequestMapping(value = "api")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class UserController {
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("user")
    public JsonResponse createUser(@RequestBody User user) {
        JsonResponse jsonResponse;
        User newUser = this.userService.createUser(user);
        if(newUser != null)
            jsonResponse = new JsonResponse(true, "User successfully created", newUser);
        else
            jsonResponse= new JsonResponse(false, "Email entered already exists", null);
        return jsonResponse;
    }

    @PostMapping("login")
    public JsonResponse login(@RequestBody User user) {
        JsonResponse jsonResponse;
        User existingUser = this.userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if(existingUser != null) {
            String token = jwtUtil.generateToken(existingUser.getUserId());
            existingUser.setPassword(null); // To prevent sensitive information getting leaked out
            jsonResponse = new JsonResponse(true, token, existingUser);
        }
        else
            jsonResponse= new JsonResponse(false, "Invalid email or password", null);
        return jsonResponse;
    }

    // Currently, for testing purposes to see the User data in Postman
    @GetMapping("user")
    public JsonResponse getAllUsers() {
        return new JsonResponse(true, "Users thus far", this.userService.findAllUsers());
    }

    @PutMapping("user")
    public JsonResponse editUser(@RequestBody User user, @RequestHeader Map<String, String> headers) {
        JsonResponse jsonResponse;
        DecodedJWT decodedJWT = jwtUtil.verify(headers.get("authorization"));
        if(decodedJWT == null)
            jsonResponse = new JsonResponse(false, "Invalid token, no authorization", null);
        else {
            if(decodedJWT.getClaims().get("userId").asInt() == user.getUserId()) {
                if(user.getPassword() != null && user.getPassword().length() >= 8) {
                    // Password encryption goes here
                    User updatedUser = this.userService.editUser(user);
                    updatedUser.setPassword(null); // To prevent sensitive information getting leaked out
                    if(updatedUser == null)
                        jsonResponse = new JsonResponse(false, "Invalid token, User does not exist", null);
                    else
                        jsonResponse = new JsonResponse(true, "Valid token received", updatedUser);
                } else
                    jsonResponse = new JsonResponse(false, "Invalid token, invalid password", null);
            } else
                jsonResponse = new JsonResponse(false, "Invalid token, user mismatch", null);
        }
        return jsonResponse;
    }

    @DeleteMapping("user/{userId}")
    public JsonResponse deleteUser(@PathVariable Integer userId, @RequestHeader Map<String, String> headers) {
        JsonResponse jsonResponse;
        DecodedJWT decodedJWT = jwtUtil.verify(headers.get("authorization"));
        if(decodedJWT == null)
            jsonResponse = new JsonResponse(false, "Invalid token, no authorization", null);
        else {
            if(decodedJWT.getClaims().get("userId").asInt() == userId) {
                User user = userService.findUserById(userId);
                userService.deleteUser(user);
                jsonResponse = new JsonResponse(true, "Valid token, user deleted", null);
            }
            else
                jsonResponse = new JsonResponse(false, "Invalid token, user mismatch", null);
        }
        return jsonResponse;
    }
}
