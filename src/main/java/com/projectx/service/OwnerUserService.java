package com.projectx.service;

import com.projectx.model.Owner;
import com.projectx.model.OwnerUser;
import com.projectx.repository.OwnerUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerUserService {

    private final OwnerUserRepo repo;

    @Autowired
    public OwnerUserService(OwnerUserRepo repo){
        this.repo = repo;
    }

    public List<OwnerUser> getAll(){
        return repo.findAll();
    }

    public List<OwnerUser> getListByOwner(Owner owner){
        return repo.getListByOwner(owner);
    }

    public OwnerUser createOrSave(OwnerUser ownerUser){
        return repo.save(ownerUser);
    }
}
