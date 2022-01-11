package com.projectx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectx.service.InterviewService;
import com.projectx.utility.CrossOriginUtil;
import com.projectx.utility.JwtUtil;

@RestController("interviewController")
@RequestMapping(value = "api")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class InterviewController {
	private InterviewService interviewService;
	private JwtUtil jwtUtil;
	
	@Autowired
	public InterviewController(InterviewService interviewService, JwtUtil jwtUtil)
	{
		this.interviewService = interviewService;
		this.jwtUtil = jwtUtil;
	}
}
