package com.projectx.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projectx.models.Application;
import com.projectx.models.Interview;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository("interviewDao")
@Transactional
public interface InterviewDao extends JpaRepository<Interview, Integer> {
	List<Interview> findInterviewByDate(Date later);
	List<Interview> findInterviewByApp(Application application);
	List<Interview> findInterviewByMonth(int month);
	List<Interview> findInterviewsThisWeek();
	List<Interview> findInterviewsOnWeekOf(Date later);
}
