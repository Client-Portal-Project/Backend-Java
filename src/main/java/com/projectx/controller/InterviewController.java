package com.projectx.controller;

import com.projectx.DTOs.ResponseMessage;
import com.projectx.model.Applicant;
import com.projectx.model.Interview;
import com.projectx.model.Order;
import com.projectx.service.ApplicantService;
import com.projectx.service.InterviewService;
import com.projectx.service.OrderService;
import com.projectx.utility.CrossOriginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class InterviewController {
    private final InterviewService interviewService;
    private final OrderService orderService;
    private final ApplicantService applicantService;

    @Autowired
    public InterviewController(InterviewService interviewService, OrderService orderService, ApplicantService applicantService){
        this.interviewService = interviewService;
        this.orderService = orderService;
        this.applicantService = applicantService;
    }

    @GetMapping("/interviews")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(interviewService.getAll());
    }

    @GetMapping("/interviews/order/{orderId}")
    public ResponseEntity<?> getListByOrder(@PathVariable Integer orderId){
        Order order = orderService.getById(orderId);
        return ResponseEntity.ok(interviewService.getListByOrder(order));
    }

    @GetMapping("/interviews/applicant/{id}")
    public ResponseEntity<?> getListByApplicant(@PathVariable Integer id){
        Applicant applicant = applicantService.getById(id);
        return ResponseEntity.ok(interviewService.getListByApplicant(applicant));
    }

    @GetMapping("/interviews/{id}")
    public ResponseEntity<?> getInterviewById(@PathVariable Integer id){
        Interview interview = interviewService.getById(id);

        if(interview !=null){
            return ResponseEntity.ok(interview);
        }
        ResponseMessage message = new ResponseMessage("No Interview with that id.");
        return ResponseEntity.badRequest().body(message);
    }

    @PostMapping("/interviews/applicant/{id}")
    public ResponseEntity<?> create(@RequestBody Interview interview, @PathVariable Integer id){
        Applicant applicant = applicantService.getById(id);

        interview.setApplicant(applicant);
        Interview newInterview = interviewService.createOrSave(interview);
        applicant.getInterviews().add(newInterview);
        applicantService.createOrSave(applicant);

        return ResponseEntity.ok(newInterview);


    }

}
