package com.projectx.services;

import com.projectx.models.Applicant;
import com.projectx.models.Skill;
import com.projectx.repositories.SkillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("skillService")
public class SkillService {
    @Autowired
    private SkillDao skillDao;

    /**
     * Saves a skill object to the database for creating a new skill or updating an existing one
     *
     * @param skill skill object
     * @return skill object if saved successfully, otherwise null
     */
    public Skill saveSkill(Skill skill) {
        return skillDao.save(skill);
    }

    /**
     * Deletes a skill object in the database
     *
     * @param skill skill object
     */
    public void deleteSkill(Skill skill) {
        skillDao.delete(skill);
    }

    /**
     * Gets a skill object from the database using its id
     *
     * @param id id of a skill object
     * @return skill object if found, null otherwise
     */
    public Skill getSkill(int id) {
        return skillDao.findById(id).orElse(null);
    }

    /**
     * Gets a list of skills from the database connected to an applicant
     *
     * @param applicant applicant object
     * @return a List of skill objects
     */
    public List<Skill> getAllSkills(Applicant applicant) {
        return skillDao.findByApplicant_ApplicantId(applicant.getApplicantId());
    }
}
