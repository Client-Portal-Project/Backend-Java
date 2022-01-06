package com.projectx.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.models.User;
import com.projectx.services.UserService;
import com.projectx.utility.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @MockBean
    UserService userService;
    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    UserController userController;

    private static final String EMAIL = "testuser@test.com";
    private static final String PASS = "password";
    private static final String AUTH = "authorization";
    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDAxMzM2ODksInVz" +
            "ZXJJZCI6LTF9.yvafM2l901ou-GetgqI6nNcDh3E_1eQ4sbvxVwYmQZs";

    private User user = new User(1, EMAIL, PASS, "test", "user", null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        //Assign
        Mockito.when(userService.createUser(user)).thenReturn(user);
        ResponseEntity<String> expectedResult = new ResponseEntity<>("User successfully created", HttpStatus.CREATED);

        //Act
        ResponseEntity<String> actualResult = userController.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserWhenEmailExists() {
        //Assign
        Mockito.when(userService.createUser(user)).thenReturn(null);
        ResponseEntity<String> expectedResult = new ResponseEntity<>("Email entered already exists", HttpStatus.CONFLICT);

        //Act
        ResponseEntity<String> actualResult = userController.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void login() {
        //Assign
        String email = EMAIL;
        String password = PASS;
        Mockito.when(userService.getUserByEmailAndPassword(email, password)).thenReturn(user);
        String token = "JWT";
        Mockito.when(jwtUtil.generateToken(user.getUserId())).thenReturn(token);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        responseHeaders.set("Access-Control-Expose-Headers", AUTH);
        ResponseEntity<User> expectedResult = new ResponseEntity<>(user, responseHeaders, HttpStatus.CREATED);

        //Act
        ResponseEntity<User> actualResult = userController.login(user);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void loginWhenEmailOrPasswordIsInvalid() {
        //Assign
        User temp = new User();
        temp.setEmail("testusr@test.co");
        temp.setPassword(PASS);
        Mockito.when(userService.getUserByEmailAndPassword(temp.getEmail(), temp.getPassword())).thenReturn(null);
        ResponseEntity<User> expectedResult = new ResponseEntity<>(null, HttpStatus.CONFLICT);

        //Act
        ResponseEntity<User> actualResult = this.userController.login(temp);

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
        User before = new User(-1, EMAIL, PASS, "test", "user", null);
        User after = new User(-1, EMAIL, null, "test-1", "user-1", null);
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        headers.put(AUTH, TOKEN);
        Mockito.when(jwtUtil.verify(headers.get(AUTH))).thenReturn(JWT.decode(TOKEN));
        Mockito.when(userService.editUser(before)).thenReturn(after);
        ResponseEntity<?> expectedResult = new ResponseEntity<>(after, HttpStatus.ACCEPTED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(before, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenTokenIsInvalid() {
        //Assign
        Map<String, String> headers = new HashMap<>();
        // lacks key-pair of 'authorization' and encoded token
        ResponseEntity<?> expectedResult = new ResponseEntity<>("Invalid token (1), no authorization", HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(user, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void editUserWhenUserTokenMismatch() {
        //Assign
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        headers.put(AUTH, TOKEN);
        Mockito.when(jwtUtil.verify(headers.get(AUTH))).thenReturn(JWT.decode(TOKEN));
        ResponseEntity<?> expectedResult = new ResponseEntity<>("Invalid token (2), user mismatch", HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(user, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenPasswordLengthLessThenEight() {
        //Assign
        User temp = new User(-1, EMAIL, "pass", "test", "user", null);
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        headers.put(AUTH, TOKEN);
        Mockito.when(jwtUtil.verify(headers.get(AUTH))).thenReturn(JWT.decode(TOKEN));
        ResponseEntity<?> expectedResult = new ResponseEntity<>("Invalid token (3), invalid password",
                HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(temp, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenUserIsNull() {
        //Assign
        User temp = new User(-1, EMAIL, PASS, "test", "user", null);
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        headers.put(AUTH, TOKEN);
        Mockito.when(jwtUtil.verify(headers.get(AUTH))).thenReturn(JWT.decode(TOKEN));
        Mockito.when(userService.editUser(temp)).thenReturn(null);
        ResponseEntity<?> expectedResult = new ResponseEntity<>("Invalid token (4), user does not exist",
                HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(temp, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteUser() {
        //Assign
        Integer userId = -1;
        User temp = new User(-1, EMAIL, PASS, "test", "user", null);
        Map<String, String> headers = new HashMap<>();
        // encoded token generated using JWT's debugger, token's userId is -1
        headers.put(AUTH, TOKEN);
        Mockito.when(jwtUtil.verify(headers.get(AUTH))).thenReturn(JWT.decode(TOKEN));
        Mockito.when(userService.findUserById(userId)).thenReturn(temp);

        ResponseEntity<?> expectedResult = new ResponseEntity<>("Valid token, user deleted", HttpStatus.ACCEPTED);

        //Act
        ResponseEntity<?> actualResult = this.userController.deleteUser(userId, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(userService, Mockito.times(1)).deleteUser(temp);
    }

    @Test
    void deleteUserWhenTokenIsInvalid() {
        //Assign
        Integer userId = -1;
        Map<String, String> headers = new HashMap<>(); // lacks key-pair of 'authorization' and encoded token
        ResponseEntity<?> expectedResult = new ResponseEntity<>("Invalid token (1), no authorization", HttpStatus.UNAUTHORIZED);

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
        headers.put(AUTH, TOKEN);
        Mockito.when(jwtUtil.verify(headers.get("authorization"))).thenReturn(JWT.decode(TOKEN));

        ResponseEntity<?> expectedResult = new ResponseEntity<>("Invalid token (2), user mismatch", HttpStatus.UNAUTHORIZED);

        //Act
        ResponseEntity<?> actualResult = this.userController.deleteUser(userId, headers);

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}