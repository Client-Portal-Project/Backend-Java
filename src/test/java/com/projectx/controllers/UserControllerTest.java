package com.projectx.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.models.User;
import com.projectx.services.UserService;
import com.projectx.utility.JwtUtil;
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
        responseHeaders.set("Access-Control-Expose-Headers", "authorization");
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
    @Test
    void editUser() {
        //Assign
        User before = new User(-1, "testuser@test.com",
                "password", "test", "user", null);
        User after = new User(-1, "testuser@test.com",
                null, "test-1", "user-1", null);
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDAxMzM2ODksInVzZXJJZCI6LTF9.yvafM2l901ou-GetgqI6nNcDh3E_1eQ4sbvxVwYmQZs";
        headers.put("authorization", token);
        Mockito.when(jwtUtil.verify(headers.get("authorization"))).thenReturn((DecodedJWT) JWT.decode(token));
        Mockito.when(userService.editUser(before)).thenReturn(after);
        ResponseEntity<?> expectedResult = new ResponseEntity(after, HttpStatus.ACCEPTED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(before, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenTokenIsInvalid() {
        //Assign
        User user = new User(1, "testuser@test.com",
                "password", "test", "user", null);
        Map<String, String> headers = new HashMap<>();
        // lacks key-pair of 'authorization' and encoded token
        ResponseEntity<?> expectedResult = new ResponseEntity("Invalid token (1), no authorization",
                HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(user, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void editUserWhenUserTokenMismatch() {
        //Assign
        User user = new User(1, "testuser@test.com",
                "password", "test", "user", null);
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDAxMzM2ODksInVzZXJJZCI6LTF9.yvafM2l901ou-GetgqI6nNcDh3E_1eQ4sbvxVwYmQZs";
        headers.put("authorization", token);
        Mockito.when(jwtUtil.verify(headers.get("authorization"))).thenReturn((DecodedJWT) JWT.decode(token));
        ResponseEntity<?> expectedResult = new ResponseEntity("Invalid token (2), user mismatch", HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(user, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenPasswordLengthLessThenEight() {
        //Assign
        User user = new User(-1, "testuser@test.com",
                "pass", "test", "user", null);
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDAxMzM2ODksInVzZXJJZCI6LTF9.yvafM2l901ou-GetgqI6nNcDh3E_1eQ4sbvxVwYmQZs";
        headers.put("authorization", token);
        Mockito.when(jwtUtil.verify(headers.get("authorization"))).thenReturn((DecodedJWT) JWT.decode(token));
        ResponseEntity<?> expectedResult = new ResponseEntity("Invalid token (3), invalid password",
                HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(user, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenUserIsNull() {
        //Assign
        User user = new User(-1, "testuser@test.com",
                "password", "test", "user", null);
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDAxMzM2ODksInVzZXJJZCI6LTF9.yvafM2l901ou-GetgqI6nNcDh3E_1eQ4sbvxVwYmQZs";
        headers.put("authorization", token);
        Mockito.when(jwtUtil.verify(headers.get("authorization"))).thenReturn((DecodedJWT) JWT.decode(token));
        Mockito.when(userService.editUser(user)).thenReturn(null);
        ResponseEntity<?> expectedResult = new ResponseEntity("Invalid token (4), user does not exist",
                HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(user, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteUser() {
        //Assign
        Integer userId = -1;
        User user = new User(-1, "testuser@test.com",
                "password", "test", "user", null);
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDAxMzM2ODksInVzZXJJZCI6LTF9.yvafM2l901ou-GetgqI6nNcDh3E_1eQ4sbvxVwYmQZs";
        headers.put("authorization", token);
        Mockito.when(jwtUtil.verify(headers.get("authorization"))).thenReturn((DecodedJWT) JWT.decode(token));
        Mockito.when(userService.findUserById(userId)).thenReturn(user);

        ResponseEntity<?> expectedResult = new ResponseEntity("Valid token, user deleted", HttpStatus.ACCEPTED);

        //Act
        ResponseEntity<?> actualResult = this.userController.deleteUser(userId, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(userService, Mockito.times(1)).deleteUser(user);
    }

    @Test
    void deleteUserWhenTokenIsInvalid() {
        //Assign
        Integer userId = -1;
        Map<String, String> headers = new HashMap<>(); // lacks key-pair of 'authorization' and encoded token
        ResponseEntity<?> expectedResult = new ResponseEntity("Invalid token (1), no authorization", HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.deleteUser(userId, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteUserWhenUserTokenMismatch() {
        //Assign
        Integer userId = 1;
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDAxMzM2ODksInVzZXJJZCI6LTF9.yvafM2l901ou-GetgqI6nNcDh3E_1eQ4sbvxVwYmQZs";
        headers.put("authorization", token);
        Mockito.when(jwtUtil.verify(headers.get("authorization"))).thenReturn((DecodedJWT) JWT.decode(token));

        ResponseEntity<?> expectedResult = new ResponseEntity("Invalid token (2), user mismatch", HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.deleteUser(userId, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}