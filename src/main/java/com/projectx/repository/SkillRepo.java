package com.projectx.repository;

import com.projectx.model.Applicant;
import com.projectx.model.Order;
import com.projectx.model.Owner;
import com.projectx.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Integer> {
}
