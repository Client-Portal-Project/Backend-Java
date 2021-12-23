/**
 * @author April Weaver
 * @since  2021-12-21
 */
package com.projectx.service;

import com.projectx.model.Applicant;
import com.projectx.repository.ApplicantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("applicantService")
public class ApplicantService {
    @Autowired
    ApplicantDao applicantDao;

    /**
     * Adds an applicant into the database
     *
     * @param applicant  the applicant to be added in the database
     * @return the applicant that was successfully added, null otherwise
     */
    public Applicant createApplicant(Applicant applicant) {
        if (applicant.getUser() == null) {
            return null;
        }
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
}
