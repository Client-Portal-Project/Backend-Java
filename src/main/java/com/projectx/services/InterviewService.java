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
	public boolean deleteInterview(Interview interview)
	{
		this.interviewDao.delete(interview);
		if(this.interviewDao.findById(interview.getInterviewId()) == null ||
				!this.interviewDao.findById(interview.getInterviewId()).equals(Optional.of(interview)))
		{
			return true;
		}
		else
		{
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
			//to do
			return this.interviewDao.save(interview);
		}
	}
	public List<Interview> findInterviewbyClient(Set<Client> clients)
	{
		return this.interviewDao.findInterviewsByClient(clients);
	}
	public List<Interview> findInterviewBySkill(Set<Skill> skills)
	{
		return this.interviewDao.findInterviewBySkill(skills);
	}
	public List<Interview> findInterviewByNeed(Need need)
	{
		return this.interviewDao.findInterviewByNeed(need);
	}
}
