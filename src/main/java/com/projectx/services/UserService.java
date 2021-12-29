package com.projectx.services;

import com.projectx.models.User;
import com.projectx.repositories.UserDao;
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

    /**
     * Retrieves a user from the database based on their userId
     *
     * @param userId - the user's id needed to retrieve the
     * user's data from the database
     * @return the user associated with the user's id, else null if
     * the user id does not exist
     */

    public User findUserById(Integer userId) {
        return this.userDao.findById(userId).orElse(null);
    }

    /**
     * Retrieves a user from the database based on their email
     *
     * @param email - the user's email needed to retrieve the
     * user's data from the database
     * @return the user associated with the user's email
     */
    
    public User findUserByEmail(String email) {
    	return this.userDao.findUserByEmail(email);
    }

    /**
     * Retrieve all users in the database
     *
     * @return a list of user objects from the database
     * this action is intended for verifying users in the database via Postman,
     * but may be deleted.
     */

    public List<User> findAllUsers() {
        return this.userDao.findAll();
    }

    /**
     * Adds a user to the database
     *
     * @param user - the user to be added to the database
     * @return the user that was successfully added, else null if
     * the user already exists
     */

    public User createUser(User user) {
        User temp = this.userDao.findUserByEmail(user.getEmail());
        if(temp != null)
            return null;
        return this.userDao.save(user);
    }

    /**
     * Retrieves a user from the database based on their email and password
     *
     * @param email - the user's email
     * @param password - the user's password
     * @return the user associated with the user's email and password
     */

    public User getUserByEmailAndPassword(String email, String password) {
        return this.userDao.findUserByEmailAndPassword(email, password);
    }

    /**
     * Edit a user from the database
     *
     * @param user - the user's new info to be updated in the database
     * @return the updated user, else null if user does not exist
     */

    public User editUser(User user) {
        User temp = this.userDao.findById(user.getUserId()).orElse(null);
        if(temp == null)
            return null;
        else {
            if(user.getEmail() != null)
                temp.setEmail(user.getEmail());
            if(user.getPassword() != null)
                temp.setPassword(user.getPassword());
            if(user.getFirstName() != null)
                temp.setFirstName(user.getFirstName());
            if(user.getLastName() != null)
                temp.setLastName(user.getLastName());
            if(user.getApproved() != null)
                temp.setApproved(user.getApproved());
            return this.userDao.save(temp);
        }
    }

    /**
     * Delete a user from the database
     *
     * @param user - the user to be deleted from the database
     */

    public void deleteUser(User user) {
        this.userDao.delete(user);
    }

    /**
     * @param id
     * @return
     */
    public User getUserById(int id) {
        return userDao.findById(id).orElse(null);
    }
}
