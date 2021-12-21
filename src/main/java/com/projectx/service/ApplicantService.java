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

    public Applicant createApplicant(Applicant applicant) {
        return applicantDao.save(applicant);
    }

    public Applicant updateApplicant(Applicant applicant) {
        return applicantDao.save(applicant);
    }

    public Applicant deleteApplicant(Applicant applicant) {
        try {
            applicantDao.delete(applicant);
        } catch (Exception e) {
            return null;
        }
        return applicant;
    }

    public List<Applicant> getAllApplicants() {
        return applicantDao.findAll();
    }

    public Applicant getApplicant(int id) {
        return applicantDao.findByUser_UserId(id);
    }
}