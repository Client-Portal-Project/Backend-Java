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
     * @param application
     * @return
     */
    public Application saveApplication(Application application) {
        return applicationDao.save(application);
    }

    /**
     * @param application
     */
    public void deleteApplication(Application application) {
        applicationDao.delete(application);
    }

    /**
     * @param id
     * @return
     */
    public Application getApplication(int id) {
        return applicationDao.findById(id).orElse(null);
    }

    /**
     * @param applicant
     * @return
     */
    public List<Application> getAllApplicationsByApplicant(Applicant applicant) {
        return applicationDao.findByApplicant_ApplicantId(applicant.getApplicantId());
    }

    /**
     * @param applicantOccupation
     * @return
     */
    public List<Application> getAllApplicationsByApplicantOccupation(ApplicantOccupation applicantOccupation) {
        return applicationDao.findByApplicantOccupation_ApplicantOccupationId(applicantOccupation
                .getApplicantOccupationalId());
    }

    /**
     * @param need
     * @return
     */
    public List<Application> getAllApplicationsByNeed(Need need) {
        return applicationDao.findByNeed_NeedId(need.getNeedId());
    }
}
