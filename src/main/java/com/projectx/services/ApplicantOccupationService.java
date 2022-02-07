package com.projectx.services;

import com.projectx.models.Applicant;
import com.projectx.models.ApplicantOccupation;
import com.projectx.repositories.ApplicantOccupationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("applicantOccupationService")
public class ApplicantOccupationService {
    @Autowired
    ApplicantOccupationDao applicantOccupationDao;

    /**
     * Saves a ApplicantOccupation in the database for posting or updating
     *
     * @param applicantOccupation ApplicantOccupation object
     * @return a ApplicantOccupation object if saved successfully, null otherwise
     */
    public ApplicantOccupation saveApplicantOccupation(ApplicantOccupation applicantOccupation) {
        return applicantOccupationDao.save(applicantOccupation);
    }

    /**
     * Deletes a ApplicantOccupation object in the database
     *
     * @param applicantOccupation ApplicantOccupation object
     */
    public void deleteApplicantOccupation(ApplicantOccupation applicantOccupation) {
        applicantOccupationDao.delete(applicantOccupation);
    }

    /**
     * Gets an ApplicantOccupation object with its id
     *
     * @param id id of an ApplicantOccupation object
     * @return a ApplicantOccupation object or null
     */
    public ApplicantOccupation getApplicantOccupation(int id) {
        return applicantOccupationDao.findById(id).orElse(null);
    }

    /**
     * Gets a List of ApplicantOccupation objects in the database connected to an applicant object
     *
     * @param applicant applicant object
     * @return a List of ApplicantOccupation objects
     */
    public List<ApplicantOccupation> getAllApplicantOccupation(Applicant applicant) {
        return applicantOccupationDao.findByApplicant_ApplicantId(applicant.getApplicantId());
    }
}
