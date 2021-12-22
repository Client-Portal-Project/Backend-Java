package com.projectx.service;

import com.projectx.exceptions.ResourceDoesNotExist;
import com.projectx.model.Applicant;
import com.projectx.model.Interview;
import com.projectx.model.Order;
import com.projectx.repository.InterviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {


    private final InterviewRepo repo;

    @Autowired
    public InterviewService(InterviewRepo repo){
        this.repo = repo;
    }

    public List<Interview> getAll(){
        return repo.findAll();
    }

    public List<Interview> getListByOrder(Order order){
        return repo.getListByOrder(order);
    }

    public List<Interview> getListByApplicant(Applicant applicant){
        return repo.getListByApplicant(applicant);
    }

    public Interview createOrSave(Interview interview){return repo.save(interview);}

    public Interview getById(Integer id){
        return repo.findById(id).orElseThrow(()-> new ResourceDoesNotExist("No Interview with id: "+ id));
    }
}
