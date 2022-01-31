package com.projectx.controllers;

import com.projectx.Driver;
import com.projectx.models.Applicant;
import com.projectx.models.ApplicantOccupation;
import com.projectx.services.ApplicantOccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("applicantOccupationController")
@RequestMapping(value = "occupation")
@CrossOrigin(value = Driver.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ApplicantOccupationController {
    @Autowired
    ApplicantOccupationService applicantOccupationService;

    @PostMapping
    public ResponseEntity<ApplicantOccupation> createApplicantOccupation(@RequestBody ApplicantOccupation
                                                                                     applicantOccupation) {
        //make sure the applicant does not exist
        ApplicantOccupation check = applicantOccupationService
                .getApplicantOccupation(applicantOccupation.getApplicantOccupationalId());
        if (check == null) {
            return new ResponseEntity<>(applicantOccupationService
                    .saveApplicantOccupation(applicantOccupation), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ApplicantOccupation> updateApplicantOccupation(@RequestBody ApplicantOccupation
                                                                                     applicantOccupation) {
        //make sure the applicant does exist
        ApplicantOccupation check = applicantOccupationService
                .getApplicantOccupation(applicantOccupation.getApplicantOccupationalId());
        if (check != null) {
            return new ResponseEntity<>(applicantOccupationService
                    .saveApplicantOccupation(applicantOccupation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteApplicantOccupation(@RequestBody ApplicantOccupation applicantOccupation) {
        //checking if applicant exists
        ApplicantOccupation check = applicantOccupationService
                .getApplicantOccupation(applicantOccupation.getApplicantOccupationalId());
        if (check != null) {
            applicantOccupationService.deleteApplicantOccupation(applicantOccupation);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ApplicantOccupation> getApplicantOccupation (@PathVariable int id) {
        ApplicantOccupation temp = applicantOccupationService.getApplicantOccupation(id);
        if (temp != null) {
            return new ResponseEntity<>(temp, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ApplicantOccupation>> getAllApplicantOccupation(@RequestBody Applicant applicant) {
        //gets all the applicant occupations from the applicant in the body
        return new ResponseEntity<>(applicantOccupationService.getAllApplicantOccupation(applicant), HttpStatus.OK);
    }
}
