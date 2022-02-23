/**
 * @authors April Weaver, Steven Hanley
 * @since  2021-12-21
 * @lastupdate 2022-02-23
 */
package com.projectx.services;

import com.projectx.models.Applicant;
import com.projectx.models.Skill;
import com.projectx.repositories.ApplicantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("applicantService")
public class ApplicantService {
    @Autowired
    private ApplicantDao applicantDao;

    /**
     * Adds an applicant into the database. Before adding to the database, checks if the applicant
     * already exists within the database.
     *
     * @param applicant  the applicant to be added in the database
     * @return the applicant that was successfully added, null otherwise
     */
    public Applicant createApplicant(Applicant applicant) {
        return applicantDao.save(applicant);
    }

    /**
     * Updates an applicant in the database
     *
     * @param applicant  the applicant to be updated in the database
     * @return the applicant that was successfully updated, null otherwise
     */
    public Applicant updateApplicant(Applicant applicant) {
        return applicantDao.save(applicant);
    }

    /**
     * Deletes an applicant in the database
     *
     * @param applicant the applicant to be deleted in the database
     */
    public void deleteApplicant(Applicant applicant) {
            applicantDao.delete(applicant);
    }

    /**
     * Returns a list of all the applicants within the database
     *
     * @return list of applicants within database
     */
    public List<Applicant> getAllApplicants() {
        return applicantDao.findAll();
    }

    /**
     * Returns an applicant that is associated with the user's id.
     *
     * @param id  the id of a user
     * @return  an applicant associated with the user's id
     */
    public Applicant getApplicant(int id) {
        return applicantDao.findByUser_UserId(id);
    }

    /**
     * Returns a list of applicants with requested employment status
     * @param employmentStatus
     * @return a List of applicants with requested employment status
     */
    // May not be needed
    public List<Applicant> getApplicantByEmploymentStatus(String employmentStatus) { return applicantDao.findByEmploymentStatus(employmentStatus); }

    /**
     * Returns a list of applicants with requested skill
     * @param skill
     * @return a list of applicants with requested skill
     */
    // May not be needed
    public List<Applicant> getApplicantSkillsIsContaining(Skill skill) { return applicantDao.findByApplicantSkillsIsContaining(skill); }


}
