package com.projectx.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.model.Applicant;
import com.projectx.service.ApplicantService;
import com.projectx.utility.CrossOriginUtil;
import com.projectx.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("applicantController")
@RequestMapping("/applicant")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ApplicantController {
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
        Applicant createdApplicant = applicantService.createApplicant(applicant);
        if (createdApplicant != null) {
            return new ResponseEntity<>(createdApplicant, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Applicant> updateApplicant(@RequestBody Applicant applicant) {
        Applicant updatedApplicant = applicantService.updateApplicant(applicant);
        if (updatedApplicant != null) {
            return new ResponseEntity<>(updatedApplicant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteApplicant(@RequestBody Applicant applicant) {
        Applicant foundApplicant = applicantService.getApplicant(applicant.getApplicantId());
        if (foundApplicant != null) {
            applicantService.deleteApplicant(applicant);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = {"/{id}", ""})
    public ResponseEntity<?> getApplicant(@PathVariable(required = false) String id) {
        if (id != null) {
            int applicantId = Integer.parseInt(id);
            Applicant applicant = applicantService.getApplicant(applicantId);
            System.out.println(applicant);
            if (applicant == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(applicant, HttpStatus.FOUND);
            }
        } else {
            return new ResponseEntity<>(applicantService.getAllApplicants(), HttpStatus.OK);
        }
    }
}
