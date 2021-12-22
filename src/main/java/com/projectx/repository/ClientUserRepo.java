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
public interface ClientUserRepo extends JpaRepository<ClientUser, Integer> {
	ClientUser findClientUserByUserId(User user);
	List<ClientUser> findClientUserByClient(Client client);
}
