package com.projectx.service;

import com.projectx.model.Applicant;
import com.projectx.repository.ApplicantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("applicantService")
public class ApplicantService {
    @Autowired
    ApplicantDao applicantDao;

    public Applicant createApplicant(Applicant applicant) {
        return applicantDao.save(applicant);
    }
}
