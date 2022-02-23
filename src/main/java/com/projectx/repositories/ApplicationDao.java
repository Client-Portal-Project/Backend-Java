/**
 * @authors  Steven Hanley
 * @since  2022-02-18
 * @lastupdate 2022-02-23
 */

package com.projectx.repositories;

import com.projectx.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("applicationDao")
@Transactional
public interface ApplicationDao extends JpaRepository<Application, Integer> {
    List<Application> findByApplicant_ApplicantId (int applicantId);
    List<Application> findByApplicantOccupation_ApplicantOccupationalId (int applicantOccupationId);
    List<Application> findByNeed_NeedId (int needId);

    @Nullable
    List<Application> findApplicationsByApplicant_EmploymentStatusAndClient_ClientId(String employmentStatus, int clientId);

    @Nullable
    List<Application> findApplicationsByApplicant_EmploymentStatusAndNeed_NeedId(String employmentStatus, int NeedId);
}
