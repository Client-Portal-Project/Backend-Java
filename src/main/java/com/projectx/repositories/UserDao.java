package com.projectx.repositories;

import com.projectx.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDao")
@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
    
    //@Query(value="select email, password from users where email = 'testuser0@test.com'")
    User findUserByEmailAndPassword(String email, String password);
}
