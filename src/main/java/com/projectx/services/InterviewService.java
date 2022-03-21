package com.projectx.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.models.Application;
import com.projectx.models.Client;
import com.projectx.models.Interview;
import com.projectx.models.Need;
import com.projectx.models.Skill;
import com.projectx.repositories.InterviewDao;

import javax.swing.text.html.Option;

@Service("interviewService")
public class InterviewService {
	private InterviewDao interviewDao;
	
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
		return this.interviewDao.findInterviewByApplication(app);
	}
	public List<Interview> findByDate(Date when)
	{
		return this.interviewDao.findInterviewByDate(when);
	}
	public Interview createInterview(Interview interview)
	{
		return this.interviewDao.save(interview);
	}
	public boolean deleteInterview(Interview interview)
	{
		Optional<Interview> check = interviewDao.findById(interview.getInterviewId());
		if(check.isPresent()) {
			interviewDao.delete(interview);
			return true;
		} else {
			return false;
		}
	}
	public Interview editInterview(Interview interview)
	{
		Interview temp = this.interviewDao.findById(interview.getInterviewId()).orElse(null);
		if(temp == null)
		{
			return null;
		}
		else
		{
			return this.interviewDao.save(interview);
		}
	}
	public List<Interview> findInterviewsbyClient(Client client)
	{
		return this.interviewDao.findInterviewsByClient(client);
	}
	
	public List<Interview> findInterviewByNeed(Need need)
	{
		return this.interviewDao.findInterviewByNeed(need);
	}
}
