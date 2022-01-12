package com.projectx.repositories;

import com.projectx.models.ApplicantOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("applicantOccupationDao")
@Transactional
public interface ApplicantOccupationDao extends JpaRepository<ApplicantOccupation, Integer> {
}
