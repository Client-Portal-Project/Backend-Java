package com.projectx.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.model.Application;
import com.projectx.model.Interview;
import com.projectx.repository.InterviewDao;

@Service("interviewService")
public class InterviewService {
	InterviewDao interviewDao;
	
	@Autowired
	public InterviewService(InterviewDao interviewDao)
	{
		this.interviewDao = interviewDao;
	}

	public Interview findInterviewById(Integer id)
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
}
