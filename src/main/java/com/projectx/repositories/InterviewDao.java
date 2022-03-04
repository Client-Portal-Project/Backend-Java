package com.projectx.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projectx.models.Application;
import com.projectx.models.Client;
import com.projectx.models.Interview;
import com.projectx.models.Need;
import com.projectx.models.Skill;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository("interviewDao")
@Transactional
public interface InterviewDao extends JpaRepository<Interview, Integer> {
	List<Interview> findInterviewByDate(Date later);
	List<Interview> findInterviewByApplication(Application application);
	//List<Interview> findInterviewByMonth(int month);
	//List<Interview> findInterviewsThisWeek();
	//List<Interview> findInterviewsOnWeekOf(Date later);
	List<Interview> findInterviewsByClient(Client clients);
	//List<Interview> findInterviewBySkill(Set<Skill> skills);
	List<Interview> findInterviewByNeed(Need need);
}
