package com.projectx.services;

import com.projectx.models.User;
import com.projectx.repositories.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    UserService userService;
    @Mock
    UserDao userDao;

    @BeforeEach
    void setUp() {
        userService = new UserService(userDao);
    }

    @Test
    void findUserById() {
        // Assign
        Integer userId = 1;
        User expectedResult = new User(1, "testuser@test.com", "password",
                "test", "user", null);
        Mockito.when(userDao.findById(userId)).thenReturn(Optional.of(expectedResult));

        // Act
        User actualResult = userService.findUserById(userId);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findUserByIdWhenUserIsNull() {
        // Assign
        Integer userId = 1;
        User expectedResult = null;
        Mockito.when(userDao.findById(userId)).thenReturn(Optional.empty());

        // Act
        User actualResult = userService.findUserById(userId);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findUserByEmail() {
        // Assign
        String email = "testuser@test.com";
        User expectedResult = new User(1, "testuser@test.com", "password",
                "test", "user", null);
        Mockito.when(userDao.findUserByEmail(email)).thenReturn(expectedResult);

        // Act
        User actualResult = userService.findUserByEmail(email);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findAllUsers() {
        // Assign
        List<User> expectedResult = new ArrayList<>();
        Mockito.when(userDao.findAll()).thenReturn(expectedResult);

        // Act
        List<User> actualResult = userService.findAllUsers();

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUser() {
        // Assign
        User temp = new User(null, "testuser@test.com", "password",
                "test", "user", null);
        User newUser = new User(1, "testuser@test.com", "password",
                "test", "user", null);
        Mockito.when(userDao.findUserByEmail(temp.getEmail())).thenReturn(null);
        Mockito.when(userDao.save(temp)).thenReturn(newUser);
        User expectedResult = newUser;

        // Act
        User actualResult = userService.createUser(temp);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserWhenUserAlreadyExists() {
        // Assign
        User user = new User(null, "testuser@test.com", "password",
                "test", "user", null);
        Mockito.when(userDao.findUserByEmail(user.getEmail())).thenReturn(user);
        User expectedResult = null;

        // Act
        User actualResult = userService.createUser(user);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getUserByEmailAndPassword() {
        // Assign
        String email = "testuser@test.com";
        String password = "password";
        User expectedResult = new User(1, "testuser@test.com", "password",
                "test", "user", null);
        Mockito.when(userDao.findUserByEmailAndPassword(email, password)).thenReturn(expectedResult);

        // Act
        User actualResult = userService.userDao.findUserByEmailAndPassword(email, password);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUser() {
        // Assign
        User before = new User(1, "testuser@test.com", "password",
                "test", "user", null);
        User after = new User(1, "testuser1@test.com", "password1",
                "test1", "user1", null);
        Mockito.when(userDao.findById(after.getUserId())).thenReturn(Optional.of(before));
        Mockito.when(userDao.save(before)).thenReturn(after);
        User expectedResult = after;

        // Act
        User actualResult = userService.editUser(before);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUserWhenUserIsNull() {
        // Assign
        User user = new User(1, "testuser@test.com", "password",
                "test", "user", null);
        Mockito.when(userDao.findById(user.getUserId())).thenReturn(Optional.empty());
        User expectedResult = null;

        // Act
        User actualResult = userService.editUser(user);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteUser() {
        // Assign
        User user = new User(1, "testuser@test.com", "password",
                "test", "user", null);

        // Act
        userService.userDao.delete(user);

        // Assert
        Mockito.verify(userDao, Mockito.times(1)).delete(user);
    }
}
