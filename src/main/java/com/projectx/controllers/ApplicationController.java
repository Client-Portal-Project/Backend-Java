/**
 * @authors  Steven Hanley
 * @since  2022-02-18
 * @lastupdate 2022-02-23
 */

package com.projectx.controllers;

import com.projectx.Driver;
import com.projectx.models.Applicant;
import com.projectx.models.ApplicantOccupation;
import com.projectx.models.Application;
import com.projectx.models.Need;
import com.projectx.services.ApplicationService;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
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
     * Creates a new application in the database through the service after checking if the application does
     * not exist
     *
     * @param application application object in the request body
     * @return a http response with an application object in a {@link ResponseEntity} that contains a created request if
     *      the application is added, bad request and null otherwise
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
     * Updates an application in the database through the service after checking if the application does
     * exist
     *
     * @param application application object in the request body
     * @return a http response with an application object in a {@link ResponseEntity} that contains an ok request if
     *      the application is updated, bad request and null otherwise
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
     * Deletes an application in the database through the service after checking if the application does
     * exist
     *
     * @param application application object in the request body
     * @return a http response in a {@link ResponseEntity} that contains a ok request if
     *      the application is deleted, bad request otherwise
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
     * Gets an application object from the database through the service with an id
     *
     * @param id id of an application object in the path variable
     * @return a http response with an application object in a {@link ResponseEntity} that contains a found request if
     *      the application is found, not found request and null otherwise
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
     * Gets a List of application objects from the database through the service that are associated with
     * the applicant object
     *
     * @param applicant applicant object in the request body
     * @return a http response with a List of application objects in a {@link ResponseEntity} that contains an ok
     *      request
     */
    @GetMapping("applicant")
    public ResponseEntity<List<Application>> getApplicationByApplicant(@RequestBody Applicant applicant) {
        return new ResponseEntity<>(applicationService.getAllApplicationsByApplicant(applicant), HttpStatus.OK);
    }

    /**
     * Gets a List of application objects from the database through the service that are associated with
     * the applicantOccupation object
     *
     * @param applicantOccupation applicantOccupation object in the request body
     * @return a http response with a List of application objects in a {@link ResponseEntity} that contains an ok
     *      request
     */
    @GetMapping("occupation")
    public ResponseEntity<List<Application>> getApplicationByOccupation(@RequestBody ApplicantOccupation
                                                                                    applicantOccupation) {

        return new ResponseEntity<>(applicationService
                .getAllApplicationsByApplicantOccupation(applicantOccupation), HttpStatus.OK);
    }

    /**
     * Gets a List of application objects from the database through the service that are associated with
     * the need object
     *
     * @param need need object in the request body
     * @return a http response with a List of application objects in a {@link ResponseEntity} that contains an ok
     *      request
     */
    @GetMapping("need")
    public ResponseEntity<List<Application>> getApplicationByNeed(@RequestBody Need need) {
        return new ResponseEntity<>(applicationService.getAllApplicationsByNeed(need), HttpStatus.OK);
    }

    /**
     * Gets a List of application objects associated with a client and a certain applicant employment status.
     * @param employmentStatus That status to be matched with applicants
     * @param clientId The client to be matched with applications
     * @return http response with the list of application objects in a {@Link ResponseEntity} or NOT_FOUND response
     */
    @GetMapping
    public ResponseEntity<List<Application>> getApplicationByEmploymentStatusAndClient(@RequestParam String employmentStatus,
                                                                                       @RequestParam int clientId){

        List<Application> applications = applicationService.getApplicationByEmploymentStatusAndClient(employmentStatus, clientId);

        if (applications.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // null pointer exception from isEmpty()?
        else return new ResponseEntity<>(applications, HttpStatus.FOUND);
    }

    /**
     * Gets a List of application objects associated with a need and a certain applicant employment status.
     * @param employmentStatus That status to be matched with applicants
     * @param clientId The need to be matched with applications
     * @return http response with the list of application objects in a {@Link ResponseEntity} or NOT_FOUND response
     */
    @GetMapping
    public ResponseEntity<List<Application>> getApplicationsByEmploymentStatusAndNeed(@RequestParam int needId,
                                                                                     @RequestParam String employmentStatus){

        List<Application> applications = applicationService.getApplicationByEmploymentStatusAndNeed(employmentStatus, needId);
        if (applications.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // null pointer exception from isEmpty()?
        else return new ResponseEntity<>(applications, HttpStatus.FOUND);

    }
}
