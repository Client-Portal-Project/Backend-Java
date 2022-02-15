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
    private ApplicantOccupationService applicantOccupationService;

    /**
     * Checks to see if the applicant occupation already exists. if not, makes a new applicant occupation.
     *
     * @param applicantOccupation an ApplicantOccupation within the request body
     * @return a http response with an applicant occupation object in a {@link ResponseEntity} that
     *      contains a created request, bad request  and null object otherwise.
     */
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

    /**
     * Checks to see if the applicant occupation already exists. if it does, updates the applicant occupation.
     *
     * @param applicantOccupation an ApplicantOccupation within the request body
     * @return a http response with an applicant occupation object in a {@link ResponseEntity} that
     *      contains an ok request, bad request and null object otherwise.
     */
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

    /**
     * Checks to see if the applicant occupation already exists. if it does, deletes the applicant occupation.
     *
     * @param applicantOccupation an ApplicantOccupation within the request body
     * @return a http response with a true object in a {@link ResponseEntity} that
     *      contains an ok request, bad request and false object otherwise.
     */
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

    /**
     * Checks and returns an applicant occupation by an id number.
     *
     * @param id id of the applicant occupation in the path
     * @return a http response with an applicant occupation object in a {@link ResponseEntity} that
     *      contains an found request, not found and null object otherwise.
     */
    @GetMapping("{id}")
    public ResponseEntity<ApplicantOccupation> getApplicantOccupation (@PathVariable int id) {
        ApplicantOccupation temp = applicantOccupationService.getApplicantOccupation(id);
        if (temp != null) {
            return new ResponseEntity<>(temp, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets a list of applicant occupations that the applicant has
     *
     * @param applicant an Applicant in the request body
     * @return a http response with a list of applicant occupations in a {@link ResponseEntity} that
     *      contains an ok request.
     */
    @GetMapping
    public ResponseEntity<List<ApplicantOccupation>> getAllApplicantOccupation(@RequestBody Applicant applicant) {
        //gets all the applicant occupations from the applicant in the body
        return new ResponseEntity<>(applicantOccupationService.getAllApplicantOccupation(applicant), HttpStatus.OK);
    }
}
