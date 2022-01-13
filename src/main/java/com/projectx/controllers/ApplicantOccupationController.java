package com.projectx.controllers;

import com.projectx.Driver;
import com.projectx.models.ApplicantOccupation;
import com.projectx.services.ApplicantOccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("applicantOccupationController")
@RequestMapping(value = "occupation")
@CrossOrigin(value = Driver.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ApplicantOccupationController {
    @Autowired
    ApplicantOccupationService applicantOccupationService;

    @PostMapping
    public ResponseEntity<ApplicantOccupation> createApplicantOccupation(@RequestBody ApplicantOccupation
                                                                                     applicantOccupation) {
        ApplicantOccupation check = applicantOccupationService
                .getApplicantOccupation(applicantOccupation.getApplicantOccupationalId());
        if (check == null) {
            return new ResponseEntity<>(applicantOccupationService
                    .saveApplicantOccupation(applicantOccupation), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
