package com.projectx.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.models.Application;
import com.projectx.models.Interview;
import com.projectx.repositories.InterviewDao;

@Service("interviewService")
public class InterviewService {
	InterviewDao interviewDao;
	
	@Autowired
	public InterviewService(InterviewDao interviewDao)
	{
		this.interviewDao = interviewDao;
	}

	public Interview findInterviewById(int id)
	{
		return this.interviewDao.findById(id).orElse(null);
	}
	public List<Interview> findAllInterviews()
	{
		return this.interviewDao.findAll();
	}
	public List<Interview> findByApplication(Application app)
	{
		return this.interviewDao.findInterviewByApp(app);
	}
	public List<Interview> findByDate(Date when)
	{
		return this.interviewDao.findInterviewByDate(when);
	}
	public Interview createInterview(Interview interview)
	{
		return this.interviewDao.save(interview);
	}
}
