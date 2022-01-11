package com.projectx.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectx.model.Application;
import com.projectx.model.Interview;

@Repository("interviewDao")
@Transactional
public interface InterviewDao extends JpaRepository<Interview, Integer> {
	List<Interview> findInterviewByDate(Date later);
	List<Interview> findInterviewByApp(Application application);
	List<Interview> findInterviewByMonth(int month);
	List<Interview> findInterviewsThisWeek();
	List<Interview> findInterviewsOnWeekOf(Date later);
}
