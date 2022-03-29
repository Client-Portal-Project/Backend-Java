package com.projectx.services;

import com.projectx.models.User;
import com.projectx.repositories.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserServiceTest {
    private static User user1;
    private static User user2;
    private static String EMAIL;

    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = new User(1,"1-1-1999","example1@gmail.com",true,"John","Doe","John Doe","RagingTiger221","111-111-1111",true,"https://i.imgur.com/J5LVHEL.png");
        user2 = new User(2,"2-2-2000","example2@gmail.com",true,"Jane","Doe","Jane Doe","RagingTiger222","222-222-2222",true,"https://i.imgur.com/Q5LIk2L.png");
        EMAIL = "example1@gmail.com";
    }

    @Test
    void findUserById() {
        // Assign
        Integer userId = 1;
        User expectedResult = user1;
        Mockito.when(userDao.findById(userId)).thenReturn(Optional.of(expectedResult));

        // Act
        User actualResult = userService.findUserById(userId);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findUserByIdWhenUserIsNull() {
        // Assign
        Integer userId = 0;
        Mockito.when(userDao.findById(userId)).thenReturn(Optional.empty());

        // Act
        User actualResult = userService.findUserById(userId);

        // Assert
        assertNull(actualResult);
    }

    @Test
    void findUserByEmail() {
        // Assign
        User expectedResult = new User();
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

    // @Test
    // void createUser() {
    //     // Assign
    //     User temp = new User(1,"1-1-1999", EMAIL);
    //     User newUser = new User(2, "2-2-2000", "test2@gmail.com", true, "test2", "test2");
        
    //     Mockito.when(userDao.findUserByEmail(temp.getEmail())).thenReturn(null);
    //     Mockito.when(userDao.save(temp)).thenReturn(newUser);
    //     User expectedResult = newUser;

    //     // Act
    //     User actualResult = userService.createUser(temp);

    //     // Assert
    //     assertEquals(expectedResult, actualResult);
    // }

    @Test
    void createUserWhenUserAlreadyExists() {
        // Assign
        Mockito.when(userDao.findUserByEmail(user1.getEmail())).thenReturn(user1);
        User expectedResult = null;

        // Act
        User actualResult = userService.createUser(user1);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    // @Test
    // void getUserByEmailAndPassword() {
    //     // Assign
    //     User expectedResult = user1;
    //     Mockito.when(userDao.findUserByEmailAndPassword(EMAIL, PASSWORD)).thenReturn(expectedResult);

    //     // Act
    //     User actualResult = userService.getUserByEmailAndPassword(EMAIL, PASSWORD);

    //     // Assert
    //     assertEquals(expectedResult, actualResult);
    // }

    @Test
    void editUser() {
        // Assign
        User before = user1;
        User after = user2;
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
        User user = user1;
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

        // Act
        userService.deleteUser(user1);

        // Assert
        Mockito.verify(userDao, Mockito.times(1)).delete(user1);
    }
}
