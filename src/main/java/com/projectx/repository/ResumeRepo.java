package com.projectx.repository;

import com.projectx.model.ResumeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepo extends JpaRepository<ResumeFile, String> {
}
