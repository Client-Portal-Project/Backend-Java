package com.projectx.repositories;

import com.projectx.models.Need;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeedDao extends JpaRepository<Need, Integer> {
    List<Need> findByClient_ClientId (int clientId);
}
