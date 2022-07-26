package com.projectx.repositories;

import com.projectx.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository()
public interface SkillDao extends JpaRepository<Skill, Integer> {
    Set<Skill> findBySkillIdIn(Set<Skill> set);
    }
 