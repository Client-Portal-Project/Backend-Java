package com.projectx.clientportal.repository;

import com.projectx.clientportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDao")
@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
    User findUserByEmailAndPassword(String email, String password);
}
