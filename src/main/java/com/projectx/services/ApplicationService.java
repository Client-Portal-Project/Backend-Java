package com.projectx.services;

import com.projectx.models.Applicant;
import com.projectx.models.ApplicantOccupation;
import com.projectx.models.Application;
import com.projectx.models.Need;
import com.projectx.repositories.ApplicationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("applicationService")
public class ApplicationService {
    @Autowired
    private ApplicationDao applicationDao;

    /**
     * Creates or updates an application object in the database
     *
     * @param application application object
     * @return application object if saved successfully, null otherwise
     */
    public Application saveApplication(Application application) {
        return applicationDao.save(application);
    }

    /**
     * Deletes an application object in the database
     *
     * @param application application object
     */
    public void deleteApplication(Application application) {
        applicationDao.delete(application);
    }

    /**
     * Gets an application from the database using its id
     *
     * @param id id of an application object
     * @return application object if found, null otherwise
     */
    public Application getApplication(int id) {
        return applicationDao.findById(id).orElse(null);
    }

    /**
     * Gets a List of application objects from the database with an associated applicant object
     *
     * @param applicant applicant object
     * @return List of application objects that are associated with the applicant object
     */
    public List<Application> getAllApplicationsByApplicant(Applicant applicant) {
        return applicationDao.findByApplicant_ApplicantId(applicant.getApplicantId());
    }

    /**
     * Gets a List of application objects from the database with an associated applicantOccupation object
     *
     * @param applicantOccupation applicantOccupation object
     * @return List of application objects that are associated with the applicantOccupation object
     */
    public List<Application> getAllApplicationsByApplicantOccupation(ApplicantOccupation applicantOccupation) {
        return applicationDao.findByApplicantOccupation_ApplicantOccupationId(applicantOccupation
                .getApplicantOccupationalId());
    }

    /**
     * Gets a List of application objects from the database with an associated need object
     *
     * @param need need object
     * @return List of application objects that are associated with the need object
     */
    public List<Application> getAllApplicationsByNeed(Need need) {
        return applicationDao.findByNeed_NeedId(need.getNeedId());
    }
}
