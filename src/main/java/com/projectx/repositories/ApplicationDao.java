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

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository("applicationDao")
@Transactional
public interface ApplicationDao extends JpaRepository<Application, Integer> {

    List<Application> findByApplicant_ApplicantId (int applicant_id);
    List<Application> findByApplicantOccupation_ApplicantOccupationalId (int applicant_occupation_id);
    List<Application> findByNeed_NeedId(int need_id);
    List<Application> findApplicationsByApplicant_EmploymentStatusAndClient_ClientId(String employment_status, int client_id);
    List<Application> findApplicationsByClient_ClientId(int client_id);
    List<Application> findApplicationsByApplicant_EmploymentStatusAndNeed_NeedId(String employment_status, int need_id);
}
