package com.projectx.repositories;

import com.projectx.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("clientDao")
@Transactional
public interface ClientDao extends JpaRepository<Client, Integer> {
    Client findClientByCompanyName(String companyName);
}
