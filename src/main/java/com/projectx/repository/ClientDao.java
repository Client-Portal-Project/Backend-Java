package com.projectx.repository;

import com.projectx.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("clientDao")
@Transactional
public interface ClientDao extends JpaRepository<Client, Integer> {
}
