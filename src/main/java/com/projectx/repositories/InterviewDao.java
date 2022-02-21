package com.projectx.repositories;

import org.springframework.data.jpa.repository.Query;
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
	@Query("SELECT i FROM Interview i WHERE YEAR(i.date) = ?1 AND MONTH(i.date) = ?2")
	List<Interview> findInterviewByMonth(int year, int month);
	List<Interview> findInterviewsByClient(Client client);
	List<Interview> findInterviewBySkill(Skill skill);
	List<Interview> findInterviewByNeed(Need need);
}
