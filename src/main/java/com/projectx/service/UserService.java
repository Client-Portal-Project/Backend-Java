package com.projectx.service;

import com.projectx.model.User;
import com.projectx.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {
    UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUserById(Integer userId) {
        return this.userDao.findById(userId).orElse(null);
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

    public User editUser(User user) {
        System.out.println(user);
        User temp = this.userDao.findById(user.getUserId()).orElse(null);
        if(temp == null)
            return null;
        else {
            if(user.getEmail() != null)
                temp.setEmail(user.getEmail());
            if(user.getPassword() != null)
                temp.setPassword(user.getPassword());
            if(user.getApproved() != null)
                temp.setApproved(user.getApproved());
            return this.userDao.save(temp);
        }
    }

    public void deleteUser(User user) {
        this.userDao.delete(user);
    }
}
