package com.projectx.repositories;

import com.projectx.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("applicationDao")
@Transactional
public interface ApplicationDao extends JpaRepository<Application, Integer> {
    List<Application> findByApplicant_ApplicantId (int applicantId);
    List<Application> findByApplicantOccupation_ApplicantOccupationalId (int applicantOccupationId);
    List<Application> findByNeed_NeedId (int needId);
}
