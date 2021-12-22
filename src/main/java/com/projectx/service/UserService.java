package com.projectx.service;

import com.projectx.exceptions.ResourceDoesNotExist;
import com.projectx.model.User;
import com.projectx.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUserById(Integer userId) {
        return this.userDao.findById(userId).orElseThrow(()-> new ResourceDoesNotExist("No User with id: "+ userId));
    }
    
    public User findUserByEmail(String email) {
    	return this.userDao.findUserByEmail(email);
    }

    // Currently, for testing purposes to see the User data in Postman
    public List<User> findAllUsers() {
        return this.userDao.findAll();
    }

    public User createUser(User user) {
        User temp = this.userDao.findUserByEmail(user.getEmail());
        if(temp != null)
            return null;
        return this.userDao.save(user);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return this.userDao.findUserByEmailAndPassword(email, password);
    }



    public void deleteUser(User user) {
        this.userDao.delete(user);
    }


}
