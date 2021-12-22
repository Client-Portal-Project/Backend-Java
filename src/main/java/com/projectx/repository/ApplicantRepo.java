package com.projectx.repository;

import com.projectx.model.Applicant;
import com.projectx.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant, Integer> {
    List<Applicant> getListByOwner(Owner owner);
}
