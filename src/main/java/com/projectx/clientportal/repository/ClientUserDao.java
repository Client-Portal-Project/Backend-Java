package com.projectx.clientportal.repository;

import com.projectx.clientportal.model.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("clientUserDao")
@Transactional
public interface ClientUserDao extends JpaRepository<ClientUser, Integer> {

}
