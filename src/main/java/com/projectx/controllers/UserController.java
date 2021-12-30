package com.projectx.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.models.User;
import com.projectx.services.UserService;
import com.projectx.utility.CrossOriginUtil;
import com.projectx.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController("userController")
@RequestMapping(value = "api")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Adds a user to the database
     * The request body must contain a user
     *
     * @param user - the user to be added to the database
     * @return http response with a string message in a {@link ResponseEntity} that contains a CREATED request if the
     * user is added, else a CONFLICT request.
     */
    @PostMapping("user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        ResponseEntity<String> response;
        User newUser = this.userService.createUser(user);
        if(newUser != null)
            response = new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
        else
            response = new ResponseEntity<>("Email entered already exists", HttpStatus.CONFLICT);
        return response;
    }

    /**
     * Verify a user's credentials, and generates a web token if successful
     * The request body must contain a user (email, password)
     *
     * @param user - the user intending to log in to their account
     * @return http response with a user object in a {@link ResponseEntity} that contains a CREATED request if the
     * user exists; thus, generating a token, else a CONFLICT request.
     */
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody User user) {
        ResponseEntity<User> response;
        User existingUser = this.userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if(existingUser != null) {
            String token = jwtUtil.generateToken(existingUser.getUserId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("authorization", token);
            responseHeaders.set("Access-Control-Expose-Headers", "authorization");
            existingUser.setPassword(null); // To prevent sensitive information getting leaked out
            response = new ResponseEntity<>(existingUser, responseHeaders, HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return response;
    }

    /**
     * Retrieve all users in the database
     *
     * @return http response with a list of user objects in a {@link ResponseEntity} that contains an ACCEPTED
     * request; this endpoint is intended for verifying users in the database via Postman, but may be deleted.
     */
    @GetMapping("user")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(this.userService.findAllUsers(), HttpStatus.ACCEPTED);
    }

    /**
     * Update the user in the database
     * The header must have a key-value pair of 'authorization' and encoded token
     * The request body must contain a user
     *
     * @param user - the user to be updated in the database
     * @return http response with a user object in a {@link ResponseEntity} that contains an ACCEPTED request if the
     * user was updated, else response with a string message and an UNAUTHORIZED request.
     */
    @PutMapping("user")
    public ResponseEntity<?> editUser(@RequestBody User user, @RequestHeader Map<String, String> headers) {
        ResponseEntity<?> response;
        DecodedJWT decodedJWT = jwtUtil.verify(headers.get("authorization"));
        if(decodedJWT == null) {
            response = new ResponseEntity<>("Invalid token (1), no authorization", HttpStatus.UNAUTHORIZED);
        } else {
            if(Objects.equals(decodedJWT.getClaims().get("userId").asInt(), user.getUserId())) {
                if(user.getPassword() == null || user.getPassword() != null && user.getPassword().length() >= 8) {
                    // Password encryption goes here
                    User updatedUser = this.userService.editUser(user);
                    if(updatedUser == null) {
                        response = new ResponseEntity<>("Invalid token (4), user does not exist", HttpStatus.UNAUTHORIZED);
                    } else {
                        updatedUser.setPassword(null); // To prevent sensitive information getting leaked out
                        response = new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
                    }
                } else {
                    response = new ResponseEntity<>("Invalid token (3), invalid password", HttpStatus.UNAUTHORIZED);
                }

            } else {
                response = new ResponseEntity<>("Invalid token (2), user mismatch", HttpStatus.UNAUTHORIZED);
            }
        }
        return response;
    }

    /**
     * Update the user in the database
     * The header must have a key-value pair of 'authorization' and encoded token
     * The path variable must contain a user id
     *
     * @param userId - the user's id to retrieve the user from database, then delete said user
     * @return http response with a string message in a {@link ResponseEntity} that contains an ACCEPTED request if the
     * user was updated, else an UNAUTHORIZED request.
     */
    @DeleteMapping("user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId, @RequestHeader Map<String, String> headers) {
        ResponseEntity<String> response;
        DecodedJWT decodedJWT = jwtUtil.verify(headers.get("authorization"));
        if(decodedJWT == null) {
            response = new ResponseEntity<>("Invalid token (1), no authorization", HttpStatus.UNAUTHORIZED);
        } else {
            if(decodedJWT.getClaims().get("userId").asInt() == userId) {
                User user = userService.findUserById(userId);
                userService.deleteUser(user);
                response = new ResponseEntity<>("Valid token, user deleted", HttpStatus.ACCEPTED);
            }
            else {
                response = new ResponseEntity<>("Invalid token (2), user mismatch", HttpStatus.UNAUTHORIZED);
            }
        }
        return response;
    }
}
