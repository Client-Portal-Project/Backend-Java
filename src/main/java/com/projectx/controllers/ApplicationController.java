package com.projectx.controllers;

import com.projectx.Driver;
import com.projectx.models.Applicant;
import com.projectx.models.ApplicantOccupation;
import com.projectx.models.Application;
import com.projectx.models.Need;
import com.projectx.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("applicationController")
@RequestMapping(value = "application")
@CrossOrigin(value = Driver.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    /**
     * @param application
     * @return
     */
    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application check = applicationService.getApplication(application.getApplicationId());
        if (check == null) {
            return new ResponseEntity<>(applicationService.saveApplication(application), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param application
     * @return
     */
    @PutMapping
    public ResponseEntity<Application> updateApplication(@RequestBody Application application) {
        Application check = applicationService.getApplication(application.getApplicationId());
        if (check != null) {
            return new ResponseEntity<>(applicationService.saveApplication(application), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param application
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteApplication(@RequestBody Application application) {
        Application check = applicationService.getApplication(application.getApplicationId());
        if (check != null) {
            applicationService.deleteApplication(application);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Application> getApplication(@PathVariable int id) {
        Application temp = applicationService.getApplication(id);
        if (temp != null) {
            return new ResponseEntity<>(temp, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param applicant
     * @return
     */
    @GetMapping("applicant")
    public ResponseEntity<List<Application>> getApplicationByApplicant(@RequestBody Applicant applicant) {
        return new ResponseEntity<>(applicationService.getAllApplicationsByApplicant(applicant), HttpStatus.OK);
    }

    /**
     * @param applicantOccupation
     * @return
     */
    @GetMapping("occupation")
    public ResponseEntity<List<Application>> getApplicationByOccupation(@RequestBody ApplicantOccupation
                                                                                    applicantOccupation) {
        return new ResponseEntity<>(applicationService
                .getAllApplicationsByApplicantOccupation(applicantOccupation), HttpStatus.OK);
    }

    /**
     * @param need
     * @return
     */
    @GetMapping("need")
    public ResponseEntity<List<Application>> getApplicationByNeed(@RequestBody Need need) {
        return new ResponseEntity<>(applicationService.getAllApplicationsByNeed(need), HttpStatus.OK);
    }
}
