package com.projectx.service;

import com.projectx.exceptions.ResourceDoesNotExist;
import com.projectx.model.Owner;
import com.projectx.repository.OwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepo ownerRepo;

    @Autowired
    public OwnerService(OwnerRepo ownerRepo){
        this.ownerRepo = ownerRepo;
    }


    public List<Owner> getAll(){
        return ownerRepo.findAll();
    }

    public Owner getOwnerById(Integer id){
        return ownerRepo.findById(id).orElseThrow(()->new ResourceDoesNotExist("No owner with id of :" + id));
    }


    public Owner createOrSave(Owner owner){
        return ownerRepo.save(owner);
    }


}
