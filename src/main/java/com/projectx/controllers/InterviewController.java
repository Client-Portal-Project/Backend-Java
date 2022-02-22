package com.projectx.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectx.services.InterviewService;
import com.projectx.Driver;
import com.projectx.models.Application;
import com.projectx.models.Client;
import com.projectx.models.Interview;
import com.projectx.models.Need;
import com.projectx.models.Skill;

@RestController("interviewController")
@RequestMapping(value = "interview")
@CrossOrigin(value = Driver.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class InterviewController {
	@Autowired
	private InterviewService interviewService;
	/**
	 * Adds interview to the database
	 * 
	 * Request body must contain an interview
	 * 	
	 * @param interview to be added
	 * @return a response with an Interview object informing whether or not interview was added
	 */
	@PostMapping
	public ResponseEntity<Interview> createInterview(@RequestBody Interview interview)
	{
		Interview created = interviewService.createInterview(interview);
		if (created != null) {
	           return new ResponseEntity<>(created, HttpStatus.CREATED);
	       } else {
	           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	       }
	}
	/**
	 * Finds an interview based on input id which must be an integer
	 * 
	 * @param id identifying number for the interview being searched for
	 * @return response containing interview associated with input, if it exists
	 */
	@GetMapping("{interviewId}")
	public ResponseEntity<Interview> getInterview(@PathVariable int id)
	{
		Interview view = this.interviewService.findInterviewById(id);
		if(view == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<>(view, HttpStatus.FOUND);
		}
	}
	/**
	 * finds all interviews in database
	 * 
	 * @return response containing a list of all interviews
	 */
	@GetMapping
	public ResponseEntity<List<Interview>> getAllInterviews()
	{
		return new ResponseEntity<>(this.interviewService.findAllInterviews(), HttpStatus.OK);
	}
	/**
	 * finds all interviews with a specified application
	 * 
	 * @param app application being used to find interviews
	 * 
	 * @return response containing a list of all interviews with given application
	 */
	@GetMapping
	public ResponseEntity<List<Interview>> getByApplication(@RequestBody Application app)
	{
		List<Interview> view = this.interviewService.findByApplication(app);
		if(view.size() == 0)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<>(view, HttpStatus.FOUND);
		}
	}
	/**
	 * read descriptions of the 3 methods above
	 * 
	 * @param when date searched
	 * @return response containing list of all interviews on specified day, hopefully
	 */
	@GetMapping
	public ResponseEntity<List<Interview>> getByDate(@RequestBody Date when)
	{
		List<Interview> view = this.interviewService.findByDate(when);
		if(view.size() == 0)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<>(view, HttpStatus.FOUND);
		}
	}
	@GetMapping
	public ResponseEntity<List<Interview>> getByClient(@RequestBody Set<Client> clients)
	{
		List<Interview> view = this.interviewService.findInterviewbyClient(clients);
		if(view.size() == 0)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<>(view, HttpStatus.FOUND);
		}
	}
	@GetMapping
	public ResponseEntity<List<Interview>> getBySkill(@RequestBody Set<Skill> skills)
	{
		List<Interview> view = this.interviewService.findInterviewBySkill(skills);
		if(view.size() == 0)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<>(view, HttpStatus.FOUND);
		}
	}
	@GetMapping
	public ResponseEntity<List<Interview>> findByNeed(@RequestBody Need need)
	{
		List<Interview> view = this.interviewService.findInterviewByNeed(need);
		if(view.size() == 0)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<>(view, HttpStatus.FOUND);
		}
	}
	@PutMapping
	public ResponseEntity<Interview> editInterview(@RequestBody Interview interview)
	{
		Interview view = this.interviewService.editInterview(interview);
		if(view == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(view, HttpStatus.OK);
	}
}
