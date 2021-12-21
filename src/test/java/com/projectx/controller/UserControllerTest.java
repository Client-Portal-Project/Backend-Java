package com.projectx.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.model.JsonResponse;
import com.projectx.model.User;
import com.projectx.service.UserService;
import com.projectx.utility.JwtUtil;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    UserController userController;
    @Mock
    UserService userService;
    @Mock
    JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService, jwtUtil);
    }

    @Test
    void createUser() {
        //Assign
        User user = new User(1, "testuser@test.com",
                "password", "test", "user", null);
        Mockito.when(userService.createUser(user)).thenReturn(user);
        ResponseEntity<String> expectedResult = new ResponseEntity<>("User successfully created", HttpStatus.CREATED);

        //Act
        ResponseEntity<String> actualResult = this.userController.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserWhenEmailExists() {
        //Assign
        User user = new User(1, "testuser@test.com",
                "password", "test", "user", null);
        Mockito.when(userService.createUser(user)).thenReturn(null);
        ResponseEntity<String> expectedResult = new ResponseEntity<>("Email entered already exists", HttpStatus.CONFLICT);

        //Act
        ResponseEntity<String> actualResult = this.userController.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void login() {
        //Assign
        String email = "testuser@test.com";
        String password = "password";
        User user = new User(1, "testuser@test.com",
                "password", "test", "user", null);
        Mockito.when(userService.getUserByEmailAndPassword(email, password)).thenReturn(user);
        String token = "JWT";
        Mockito.when(jwtUtil.generateToken(user.getUserId())).thenReturn(token);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        ResponseEntity<User> expectedResult = new ResponseEntity<>(user, responseHeaders, HttpStatus.CREATED);

        //Act
        ResponseEntity<User> actualResult = this.userController.login(user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void loginWhenEmailOrPasswordIsInvalid() {
        //Assign
        User user = new User();
        user.setEmail("testusr@test.co");
        user.setPassword("password");
        Mockito.when(userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(null);
        ResponseEntity<User> expectedResult = new ResponseEntity<>(null, HttpStatus.CONFLICT);

        //Act
        ResponseEntity<User> actualResult = this.userController.login(user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllUsers() {
        // Assign
        List<User> users = new ArrayList<>();
        Mockito.when(userService.findAllUsers()).thenReturn(users);
        ResponseEntity<List<User>> expectedResult = new ResponseEntity<>(users, HttpStatus.ACCEPTED);

        // Act
        ResponseEntity<List<User>> actualResult = this.userController.getAllUsers();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Ignore
    @Test
    void editUser() {
        //Assign
        User user = new User(1, "testuser@testing.com",
                "password", "test1", "user1", null);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "VALID");
        ResponseEntity<?> expectedResult = new ResponseEntity("Invalid token (1), no authorization",
                HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(user, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    // Passed; however, need to investigate if a generated web token will be needed for the next test
    @Test
    void editUserWhenTokenIsInvalid() {
        //Assign
        User user = new User(1, "testuser@testing.com",
                "password", "test1", "user1", null);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "INVALID");
        ResponseEntity<?> expectedResult = new ResponseEntity("Invalid token (1), no authorization",
                HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(user, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteUser() {
    }
}