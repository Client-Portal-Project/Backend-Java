package com.projectx.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projectx.models.Client;
import com.projectx.models.ClientUser;
import com.projectx.models.User;

@Repository("clientUserDao")
@Transactional
public interface ClientUserDao extends JpaRepository<ClientUser, Integer> {
	ClientUser findClientUserByUser(User user);
	List<ClientUser> findClientUserByClient(Client client);
}
