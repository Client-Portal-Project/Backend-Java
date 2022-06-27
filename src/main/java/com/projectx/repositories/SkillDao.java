package com.projectx.repositories;

import com.projectx.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillDao extends JpaRepository<Skill, Integer> {
    List<Skill> findByApplicants_ApplicantId (int applicant_id);
}
