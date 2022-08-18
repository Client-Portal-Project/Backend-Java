package com.projectx.controllers;

import com.projectx.Driver;
import com.projectx.aspects.annotations.NoAuth;
import com.projectx.models.Mail;
import com.projectx.models.User;
import com.projectx.services.MailService;
import com.projectx.services.UserService;
import com.projectx.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController("userController")
@RequestMapping("user")
@CrossOrigin(value = Driver.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired 
    private MailService mailService;

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
    @NoAuth
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        ResponseEntity<String> response;
        User newUser = this.userService.createUser(user);
        
        if(newUser != null) {
        	Mail mail=mailService.register(newUser.getEmail());
            response = new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
        }
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
    @NoAuth
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
    @NoAuth
    @GetMapping
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
     * user was updated, else response with no object and an UNAUTHORIZED if password does not match the criteria,
     * NOT_FOUND if the User is not in the database, or BAD_REQUEST request if the user ids don't match.
     */
    @PutMapping
    public ResponseEntity<User> editUser(@RequestBody User user, HttpServletRequest headers) {
        ResponseEntity<User> response;
        if(Objects.equals(headers.getAttribute("userId"), user.getUserId())) {
            if(user.getPassword() == null || user.getPassword() != null && user.getPassword().length() >= 8) {
                // Password encryption goes here
                User updatedUser = this.userService.editUser(user);
                if(updatedUser == null) {
                    response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                } else {
                    updatedUser.setPassword(null); // To prevent sensitive information getting leaked out
                    response = new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
                }
            } else {
                response = new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } else {
            response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId, HttpServletRequest headers) {
        ResponseEntity<String> response;
        if(Objects.equals(headers.getAttribute("userId"), userId)) {
            User user = userService.findUserById(userId);
            userService.deleteUser(user);
            response = new ResponseEntity<>("Valid token, user deleted", HttpStatus.ACCEPTED);
        }
        else {
            response = new ResponseEntity<>("Invalid token, user mismatch", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}
