package com.projectx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projectx.model.Client;
import com.projectx.model.ClientUser;
import com.projectx.model.User;

@Repository("clientUserDao")
@Transactional
public interface ClientUserDao extends JpaRepository<ClientUser, Integer> {
	ClientUser findClientUserByUser(User user);
	List<ClientUser> findClientUserByClient(Client client);
}
