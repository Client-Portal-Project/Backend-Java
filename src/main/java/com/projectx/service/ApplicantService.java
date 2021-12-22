package com.projectx.service;

import com.projectx.exceptions.ResourceDoesNotExist;
import com.projectx.model.Applicant;
import com.projectx.model.Owner;
import com.projectx.repository.ApplicantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantService {

    private final ApplicantRepo repo;

    @Autowired
    public ApplicantService(ApplicantRepo repo){
        this.repo = repo;
    }

    public List<Applicant> getAll(){
        return repo.findAll();
    }

    public List<Applicant> getListByOwner(Owner owner){
        return repo.getListByOwner(owner);
    }

    public Applicant createOrSave(Applicant applicant){
        return repo.save(applicant);
    }

    public Applicant getById(Integer id){
        return repo.findById(id).orElseThrow(()-> new ResourceDoesNotExist("No Applicant with id: "+ id));
    }


}
