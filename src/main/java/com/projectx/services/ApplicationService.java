/**
 * @authors  Steven Hanley
 * @since  2022-02-18
 * @lastupdate 2022-02-23
 */

package com.projectx.services;


import com.projectx.exception.ApplicationRequestException;
import com.projectx.models.Applicant;
import com.projectx.models.ApplicantOccupation;
import com.projectx.models.Application;
import com.projectx.models.Need;
import com.projectx.repositories.ApplicationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("applicationService")
public class ApplicationService {
    @Autowired
    private ApplicationDao applicationDao;

    /**
     * Creates or updates an application in the database
     *
     * @param application application object
     * @return application object if saved successfully, null otherwise
     */
    public Application saveApplication(Application application) {
        return applicationDao.save(application);
    }

    /**
     * Deletes an application optional in the database
     *
     * @param application application object
     */
    public void deleteApplication(Application application) {
        applicationDao.delete(application);
    }

    /**
     * Gets an application optional from the database using its id
     *
     * @param applicationId id of an application object
     * @return Optional application object if found, null otherwise
     */
    public Optional<Application> findById(int applicationId) {
        return applicationDao.findById(applicationId);
    }

    /**
     * Gets a List of application optionals from the database with an associated applicant object
     *
     * @param applicant applicant object
     * @return Optional List of application objects that are associated with the applicant object
     */
    public List<Application> getAllApplicationsByApplicant(Applicant applicant) {
        return applicationDao.findByApplicant_ApplicantId(applicant.getApplicantId());
    }

    /**
     * Gets a List of application optionals from the database with an associated applicantOccupation object
     *
     * @param applicantOccupation applicantOccupation object
     * @return Optional List of application objects that are associated with the applicantOccupation object
     */
    public List<Application> getAllApplicationsByApplicantOccupation(ApplicantOccupation applicantOccupation) {
        return applicationDao.findByApplicantOccupation_ApplicantOccupationalId(applicantOccupation
                .getApplicantOccupationalId());
    }

    /**
     * Gets a List of application optionals from the database with an associated need object
     *
     * @param need need object
     * @return Optional List of application objects that are associated with the need object
     */
    public List<Application> getAllApplicationsByNeed(Need need) {
        return applicationDao.findByNeed_NeedId(need.getNeedId());
    }

    /**
     * Gets a list of application optionals with employmentStatus and clientId
     *
     * @param employmentStatus
     * @param clientId integer
     * @return Optional List of applications with employmentStatus and clientId
     */
    public List<Application> getApplicationByEmploymentStatusAndClient(String employmentStatus, int clientId){
        return applicationDao.findApplicationsByApplicant_EmploymentStatusAndClient_ClientId(employmentStatus, clientId);
    }

    public List<Application> getApplicationByEmploymentStatusAndNeed(String employmentStatus, int needId){
        return applicationDao.findApplicationsByApplicant_EmploymentStatusAndNeed_NeedId(employmentStatus, needId);
    }

    public List<Application> getApplicationByClient(int clientId) {
        return applicationDao.findApplicationsByClient_ClientId(clientId);
    }

    public List<Application> getAllApplications(){
        return applicationDao.findAll();
    }

}
