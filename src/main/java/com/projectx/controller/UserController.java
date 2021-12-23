package com.projectx.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.model.JsonResponse;
import com.projectx.model.User;
import com.projectx.service.UserService;
import com.projectx.utility.CrossOriginUtil;
import com.projectx.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // JsonResponse jsonResponse;
        ResponseEntity<String> response;
        User newUser = this.userService.createUser(user);
        if(newUser != null) {
            // jsonResponse = new JsonResponse(true, "User successfully created", newUser);
            response = new ResponseEntity("User successfully created", HttpStatus.CREATED);
        } else {
            // jsonResponse= new JsonResponse(false, "Email entered already exists", null);
            response = new ResponseEntity("Email entered already exists", HttpStatus.CONFLICT);
        }

        return response;
    }

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody User user) {
        // JsonResponse jsonResponse;
        ResponseEntity<User> response;
        User existingUser = this.userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if(existingUser != null) {
            String token = jwtUtil.generateToken(existingUser.getUserId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("authorization", token);
            responseHeaders.set("Access-Control-Expose-Headers", "authorization");
            existingUser.setPassword(null); // To prevent sensitive information getting leaked out
            // jsonResponse = new JsonResponse(true, token, existingUser);
            response = new ResponseEntity(existingUser, responseHeaders, HttpStatus.CREATED);
        }
        else {
            // jsonResponse= new JsonResponse(false, "Invalid email or password", null);
            response = new ResponseEntity(null, HttpStatus.CONFLICT);
        }

        return response;
    }

    // Currently, for testing purposes to see the User data in Postman
    @GetMapping("user")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity(this.userService.findAllUsers(), HttpStatus.ACCEPTED);
    }

    @PutMapping("user")
    public ResponseEntity<?> editUser(@RequestBody User user, @RequestHeader Map<String, String> headers) {
        // JsonResponse jsonResponse;
        ResponseEntity<String> response;
        DecodedJWT decodedJWT = jwtUtil.verify(headers.get("authorization"));
        if(decodedJWT == null) {
            // jsonResponse = new JsonResponse(false, "Invalid token, no authorization", null);
            response = new ResponseEntity("Invalid token (1), no authorization",
                    HttpStatus.UNAUTHORIZED);
        } else {
            if(decodedJWT.getClaims().get("userId").asInt() == user.getUserId()) {
                if(user.getPassword() == null || user.getPassword() != null && user.getPassword().length() >= 8) {
                    // Password encryption goes here
                    User updatedUser = this.userService.editUser(user);
                    updatedUser.setPassword(null); // To prevent sensitive information getting leaked out
                    if(updatedUser == null) {
                        // jsonResponse = new JsonResponse(false, "Invalid token, user does not exist", null);
                        response = new ResponseEntity("Invalid token (4), user does not exist",
                                HttpStatus.UNAUTHORIZED);
                    } else {
                        // jsonResponse = new JsonResponse(true, "Valid token received", updatedUser);
                        response = new ResponseEntity(updatedUser, HttpStatus.ACCEPTED);
                    }

                } else {
                    // jsonResponse = new JsonResponse(false, "Invalid token, invalid password", null);
                    response = new ResponseEntity("Invalid token (3), invalid password",
                            HttpStatus.UNAUTHORIZED);
                }

            } else {
                // jsonResponse = new JsonResponse(false, "Invalid token (2), user mismatch", null);
                response = new ResponseEntity("Invalid token (2), user mismatch", HttpStatus.UNAUTHORIZED);
            }
        }
        return response;
    }

    @DeleteMapping("user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId, @RequestHeader Map<String, String> headers) {
        // JsonResponse jsonResponse;
        ResponseEntity<String> response;
        DecodedJWT decodedJWT = jwtUtil.verify(headers.get("authorization"));
        if(decodedJWT == null)
            // jsonResponse = new JsonResponse(false, "Invalid token, no authorization", null);
            response = new ResponseEntity("Invalid token (1), no authorization", HttpStatus.UNAUTHORIZED);
        else {
            if(decodedJWT.getClaims().get("userId").asInt() == userId) {
                User user = userService.findUserById(userId);
                userService.deleteUser(user);
                // jsonResponse = new JsonResponse(true, "Valid token, user deleted", null);
                response = new ResponseEntity("Valid token, user deleted", HttpStatus.ACCEPTED);
            }
            else {
                // jsonResponse = new JsonResponse(false, "Invalid token, user mismatch", null);
                response = new ResponseEntity("Invalid token (2), user mismatch", HttpStatus.UNAUTHORIZED);
            }
        }
        return response;
    }
}
