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

	/**
	 * @param id
	 * @return
	 */
	public Interview findInterviewById(int id)
	{
		return this.interviewDao.findById(id).orElse(null);
	}

	/**
	 * @return
	 */
	public List<Interview> findAllInterviews()
	{
		return this.interviewDao.findAll();
	}

	/**
	 * @param app
	 * @return
	 */
	public List<Interview> findByApplication(Application app) {
		return this.interviewDao.findInterviewByApplication(app);
	}

	/**
	 * @param when
	 * @return
	 */
	public List<Interview> findByDate(Date when)
	{
		return this.interviewDao.findInterviewByDate(when);
	}

	/**
	 * @param interview
	 * @return
	 */
	public Interview createInterview(Interview interview)
	{
		return this.interviewDao.save(interview);
	}

	/**
	 * @param interview
	 * @return
	 */
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

	/**
	 * @param interview
	 * @return
	 */
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

	/**
	 * @param client
	 * @return
	 */
	public List<Interview> findInterviewByClient(Client client) {
		return this.interviewDao.findInterviewsByClient(client);
	}

	/**
	 * @param skill
	 * @return
	 */
	public List<Interview> findInterviewBySkill(Skill skill) {
		return this.interviewDao.findInterviewBySkill(skill);
	}

	/**
	 * @param need
	 * @return
	 */
	public List<Interview> findInterviewByNeed(Need need)
	{
		return this.interviewDao.findInterviewByNeed(need);
	}
}
