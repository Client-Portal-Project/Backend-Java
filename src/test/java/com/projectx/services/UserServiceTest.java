package com.projectx.services;

import com.projectx.models.User;
import com.projectx.repositories.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserServiceTest {
    private static final String EMAIL = "testuser@test.com";
    private static final String PASSWORD = "password";
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUserById() {
        // Assign
        Integer userId = 1;
        User expectedResult = new User(1, false, PASSWORD,
                "test", "user", EMAIL);
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
        Mockito.when(userDao.findById(userId)).thenReturn(Optional.empty());

        // Act
        User actualResult = userService.findUserById(userId);

        // Assert
        assertNull(actualResult);
    }

    @Test
    void findUserByEmail() {
        // Assign
        User expectedResult = new User(1, false, PASSWORD,
                "test", "user", EMAIL);
        Mockito.when(userDao.findUserByEmail(EMAIL)).thenReturn(expectedResult);

        // Act
        User actualResult = userService.findUserByEmail(EMAIL);

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
        User temp = new User(0, false, PASSWORD,
                "test", "user", EMAIL);
        User newUser = new User(1, false, PASSWORD,
                "test", "user", EMAIL);
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
        User user = new User(0, false, PASSWORD,
                "test", "user", EMAIL);
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
        User expectedResult = new User(1, false, PASSWORD,
                "test", "user", EMAIL);
        Mockito.when(userDao.findUserByEmailAndPassword(EMAIL, PASSWORD)).thenReturn(expectedResult);

        // Act
        User actualResult = userService.getUserByEmailAndPassword(EMAIL, PASSWORD);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void editUser() {
        // Assign
        User before = new User(1, true, PASSWORD,
                "test", "user", EMAIL);
        User after = new User(1, true, PASSWORD,
                "test1", "user1", EMAIL);
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
        User user = new User(1, false, PASSWORD,
                "test", "user", EMAIL);
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
        User user = new User(1, false, PASSWORD,
                "test", "user", EMAIL);

        // Act
        userService.deleteUser(user);

        // Assert
        Mockito.verify(userDao, Mockito.times(1)).delete(user);
    }
}
