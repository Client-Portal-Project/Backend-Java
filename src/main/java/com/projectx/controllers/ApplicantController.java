/**
 * @author April Weaver
 * @since  2021-12-21
 */
package com.projectx.controllers;

import com.projectx.Driver;
import com.projectx.models.Applicant;
import com.projectx.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("applicantController")
@RequestMapping(value = "applicant")
@CrossOrigin(value = Driver.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ApplicantController {
    @Autowired
    private ApplicantService applicantService;

    /**
     * Adds an applicant to the database
     *
     * The request body must contain an applicant
     *
     * @param applicant  the applicant to be added to the database
     * @return a http response with an applicant object in a {@link ResponseEntity} that contains a created request if
     *          the applicant is added, bad request otherwise
     */
    @PostMapping
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
        Applicant createdApplicant = applicantService.createApplicant(applicant);
        if (createdApplicant != null) {
            return new ResponseEntity<>(createdApplicant, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates the applicant in the database
     *
     * The request body must contain an applicant
     *
     * @param applicant  the applicant to be updated in the database
     * @return a http response with an applicant object in a {@link ResponseEntity} that contains an ok request if
     *          the applicant is updated, bad request otherwise
     */
    @PutMapping
    public ResponseEntity<Applicant> updateApplicant(@RequestBody Applicant applicant) {
        Applicant updatedApplicant = applicantService.updateApplicant(applicant);
        if (updatedApplicant != null) {
            return new ResponseEntity<>(updatedApplicant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes an applicant from the database
     *
     * The request body must contain an applicant
     *
     * @param applicant  the applicant to be deleted in the database
     * @return a http response and <code>true</code> in a {@link ResponseEntity} that contains an ok request if
     *          the applicant is deleted, bad request and <code>false</code> otherwise
     */
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

    /**
     * Returns a list of applicants in the database.
     *
     * @return a http response with a list of applicants in
     *          a {@link ResponseEntity} that contains an ok request.
     */
    @GetMapping
    public ResponseEntity<List<Applicant>> getApplicant() {
        return new ResponseEntity<>(applicantService.getAllApplicants(), HttpStatus.OK);
    }

    /**
     * Returns an applicant with the given id in the path variable.
     *
     * The path variable must be an integer.
     *
     * @param id  not required String that is an id of an applicant
     * @return a http response with an applicant object in a {@link ResponseEntity} that contains an ok request if
     *          the specific applicant is found, bad request otherwise.
     */
    @GetMapping("{id}")
    public ResponseEntity<Applicant> getAllApplicants(@PathVariable int id) {
        Applicant applicant = applicantService.getApplicant(id);
        System.out.println(applicant);
        if (applicant == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(applicant, HttpStatus.FOUND);
        }
    }
}
