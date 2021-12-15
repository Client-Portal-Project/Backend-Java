package com.projectx.clientportal.repository;

import com.projectx.clientportal.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("clientDao")
@Transactional
public interface ClientDao extends JpaRepository<Client, Integer> {
}
