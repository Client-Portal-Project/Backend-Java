package com.projectx.repositories;



import com.projectx.models.Applicant;
import com.projectx.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository("applicantDao")
@Transactional
public interface ApplicantDao extends JpaRepository<Applicant, Integer> {
    @Nullable
    Applicant findByUser_UserId(int userId);

    @Nullable
    List<Applicant> findByEmploymentStatus(String employmentStatus);

    @Nullable
    List<Applicant> findByApplicantSkillsIsContaining(Skill skill);

}
