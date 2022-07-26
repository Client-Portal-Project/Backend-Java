package com.projectx.repositories;

import com.projectx.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDao extends JpaRepository<File, Integer> {
    List<File> findByApplicant_ApplicantId (int applicant_id);
}
