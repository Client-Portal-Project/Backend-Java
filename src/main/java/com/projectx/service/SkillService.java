package com.projectx.service;

import com.projectx.exceptions.ResourceDoesNotExist;
import com.projectx.model.Skill;
import com.projectx.repository.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    private final SkillRepo skillRepo;

    @Autowired
    public SkillService(SkillRepo skillRepo){
        this.skillRepo = skillRepo;
    }

    public List<Skill> getAll(){return skillRepo.findAll();}

    public Skill getById(Integer id){
        return skillRepo.findById(id).orElseThrow(()-> new ResourceDoesNotExist("No Skill with id: "+id));
    }

    public Skill createOrSave(Skill skill){
        return skillRepo.save(skill);
    }
}
