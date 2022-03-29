package com.projectx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.models.User;
import com.projectx.services.UserService;
import com.projectx.utility.JwtUtil;
import lombok.SneakyThrows;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @MockBean
    UserService userService;
    @Mock
    JwtUtil jwtUtil;
    @InjectMocks
    UserController userController;

    // private static final String EMAIL = "testuser@test.com";
    // private static final String PASS = "password";
    // private static final String AUTH = "authorization";
    private static final String ID = "userId";

    private MockMvc mvc;
    private User user = new User();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
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
        ResponseEntity<String> expectedResult = new ResponseEntity<>("Email entered already exists",
                HttpStatus.CONFLICT);
        //Act
        ResponseEntity<String> actualResult = userController.createUser(user);
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    // @Test
    // void login() {
    //     //Assign
    //     String email = EMAIL;
    //     String password = PASS;
    //     Mockito.when(userService.getUserByEmailAndPassword(email, password)).thenReturn(user);
    //     String token = "JWT";
    //     Mockito.when(jwtUtil.generateToken(user.getUserId())).thenReturn(token);
    //     HttpHeaders responseHeaders = new HttpHeaders();
    //     responseHeaders.set("Authorization", token);
    //     responseHeaders.set("Access-Control-Expose-Headers", AUTH);
    //     ResponseEntity<User> expectedResult = new ResponseEntity<>(user, responseHeaders, HttpStatus.CREATED);
    //     //Act
    //     ResponseEntity<User> actualResult = userController.login(user);
    //     //Assert
    //     assertEquals(expectedResult, actualResult);
    // }

    // @Test
    // void loginWhenEmailOrPasswordIsInvalid() {
    //     //Assign
    //     User temp = new User();
    //     temp.setEmail("testusr@test.co");
    //     temp.setPassword(PASS);
    //     Mockito.when(userService.getUserByEmailAndPassword(temp.getEmail(), temp.getPassword())).thenReturn(null);
    //     ResponseEntity<User> expectedResult = new ResponseEntity<>(null, HttpStatus.CONFLICT);
    //     //Act
    //     ResponseEntity<User> actualResult = this.userController.login(temp);
    //     //Assert
    //     assertEquals(expectedResult, actualResult);
    // }

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
        User before = new User();
        User after = new User();
        MockHttpServletRequest headers = new MockHttpServletRequest();
        headers.setAttribute(ID, -1);
        Mockito.when(userService.editUser(before)).thenReturn(after);
        ResponseEntity<?> expectedResult = new ResponseEntity<>(after, HttpStatus.ACCEPTED);
        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(before, headers);
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenUserTokenMismatch() {
        //Assign
        MockHttpServletRequest headers = new MockHttpServletRequest();
        headers.setAttribute(ID, -1);
        ResponseEntity<User> expectedResult = new ResponseEntity<>(null,
                HttpStatus.BAD_REQUEST);
        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(user, headers);
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenPasswordLengthLessThenEight() {
        //Assign
        User temp = new User();
        MockHttpServletRequest headers = new MockHttpServletRequest();
        headers.setAttribute(ID, -1);
        ResponseEntity<User> expectedResult = new ResponseEntity<>(null,
                HttpStatus.UNAUTHORIZED);
        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(temp, headers);
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenUserIsNull() {
        //Assign
        User temp = new User();
        MockHttpServletRequest headers = new MockHttpServletRequest();
        headers.setAttribute(ID, -1);
        ResponseEntity<User> expectedResult = new ResponseEntity<>(null,
                HttpStatus.NOT_FOUND);
        Mockito.when(userService.editUser(user)).thenReturn(null);
        //Act
        ResponseEntity<?> actualResult = this.userController.editUser(temp, headers);
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteUser() {
        //Assign
        Integer userId = -1;
        User temp = new User();
        MockHttpServletRequest headers = new MockHttpServletRequest();
        headers.setAttribute(ID, -1);
        Mockito.when(userService.findUserById(userId)).thenReturn(temp);
        ResponseEntity<?> expectedResult = new ResponseEntity<>("Valid token, user deleted", HttpStatus.ACCEPTED);
        //Act
        ResponseEntity<?> actualResult = this.userController.deleteUser(userId, headers);
        //Assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(userService, Mockito.times(1)).deleteUser(temp);
    }

    @Test
    void deleteUserWhenUserTokenMismatch() {
        //Assign
        Integer userId = 1;
        MockHttpServletRequest headers = new MockHttpServletRequest();
        headers.setAttribute(ID, -1);
        ResponseEntity<?> expectedResult = new ResponseEntity<>("Invalid token, user mismatch",
                HttpStatus.UNAUTHORIZED);
        //Act
        ResponseEntity<?> actualResult = this.userController.deleteUser(userId, headers);
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test @SneakyThrows
    void testGetUser() {
        Mockito.when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        Map<String, String> requestBody1 = new HashMap<>();
        requestBody1.put("email", user.getEmail());

        mvc.perform(MockMvcRequestBuilders.get("/user/")
                        .content(new ObjectMapper().writeValueAsString(requestBody1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(user)));

        Map<String, String> requestBody2 = new HashMap<>();
        requestBody2.put("email", "hello");

        mvc.perform(MockMvcRequestBuilders.get("/user/")
                        .content(new ObjectMapper().writeValueAsString(requestBody2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}