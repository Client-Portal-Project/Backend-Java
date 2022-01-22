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

    public ApplicantOccupation saveApplicantOccupation(ApplicantOccupation applicantOccupation) {
        return applicantOccupationDao.save(applicantOccupation);
    }

    public void deleteApplicantOccupation(ApplicantOccupation applicantOccupation) {
        applicantOccupationDao.delete(applicantOccupation);
    }

    public ApplicantOccupation getApplicantOccupation(int id) {
        return applicantOccupationDao.getById(id);
    }

    public List<ApplicantOccupation> getAllApplicantOccupation(Applicant applicant) {
        return applicantOccupationDao.findByApplicant_ApplicantId(applicant.getApplicantId());
    }
}
