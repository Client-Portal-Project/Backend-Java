package com.projectx.repository;

import com.projectx.model.Client;
import com.projectx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("clientDao")
@Transactional
public interface ClientDao extends JpaRepository<Client, Integer> {
    Client findClientByCompanyName(String companyName);
}
