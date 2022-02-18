package com.projectx.repositories;



import com.projectx.models.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("applicantDao")
@Transactional
public interface ApplicantDao extends JpaRepository<Applicant, Integer> {
    @Nullable
    Applicant findByUser_UserId(int userId);

    @Nullable
    List<Applicant> findByEmploymentStatus(String employmentStatus);




}
