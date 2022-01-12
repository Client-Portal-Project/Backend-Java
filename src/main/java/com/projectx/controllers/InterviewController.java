package com.projectx.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectx.services.InterviewService;
import com.projectx.utility.JwtUtil;
import com.projectx.Driver;
import com.projectx.models.Application;
import com.projectx.models.Interview;

@RestController("interviewController")
@RequestMapping(value = "api")
@CrossOrigin(value = Driver.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class InterviewController {
	@Autowired
	private InterviewService interviewService;
	@Autowired
	private JwtUtil jwtUtil;
	
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
	@GetMapping("{interviewId}")
	public ResponseEntity<Interview> findInterviewById(@PathVariable int id)
	{
		Interview view = this.interviewService.findInterviewById(id);
		if(view == null)
		{
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(view, HttpStatus.FOUND);
		}
	}
	@GetMapping
	public ResponseEntity<List<Interview>> findAllInterviews()
	{
		return new ResponseEntity<>(this.interviewService.findAllInterviews(), HttpStatus.OK);
	}
	@GetMapping
	public ResponseEntity<List<Interview>> findByApplication(@RequestBody Application app)
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
	@GetMapping
	public ResponseEntity<List<Interview>> findByDate(@RequestBody Date when)
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
}
