/**
 * @authors April Weaver, Steven Hanley
 * @since  2021-12-21
 * @lastupdate 2022-02-23
 */
package com.projectx.controllers;

import java.util.List;
import java.util.Set;

import com.projectx.Driver;
import com.projectx.models.Applicant;
import com.projectx.models.Skill;
import com.projectx.services.ApplicantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Applicant>> getAllApplicants() {
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
    public ResponseEntity<Applicant> getApplicant(@PathVariable int id) {
        Applicant applicant = applicantService.getApplicant(id);
        if (applicant == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(applicant, HttpStatus.FOUND);
        }
    }

  /* @GetMapping("status")

   public ResponseEntity<List<Applicant>> getApplicantByEmploymentStatus(@RequestParam String employmentStatus){

            List<Applicant> applicants = applicantService.getApplicantByEmploymentStatus(employmentStatus);

            if (applicants.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(applicants, HttpStatus.FOUND);
            }

    }
*/




    @GetMapping("skill")
    public ResponseEntity<Set<Applicant>> getApplicantBySkillsIsContaining(@RequestParam Skill skill){
        Set<Applicant> applicants = applicantService.getApplicantSkillsIsContaining(skill);
        if (applicants == null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(applicants, HttpStatus.FOUND);
        }
    }
}
