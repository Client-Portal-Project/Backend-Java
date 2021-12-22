package com.projectx.repository;

import com.projectx.model.Owner;
import com.projectx.model.OwnerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerUserRepo extends JpaRepository<OwnerUser, Integer> {

    List<OwnerUser> getListByOwner(Owner owner);
}
