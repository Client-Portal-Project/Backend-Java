package com.projectx.controllers;

/**
 * @authors  Steven Hanley
 * @since  2022-02-18
 * @lastupdate 2022-02-23
 */

import com.projectx.Driver;

import com.projectx.exception.ApplicationRequestException;
import com.projectx.models.Applicant;
import com.projectx.models.ApplicantOccupation;
import com.projectx.models.Application;
import com.projectx.models.Need;
import com.projectx.services.ApplicationService;
import lombok.NonNull;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * @return a http response with an application object in a {@link ResponseEntity} with a created status
     * @throws ApplicationRequestException when application already exists
     */
    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) throws ApplicationRequestException {
        Optional<Application> check = applicationService.findById(application.getApplicationId());
        if (!check.isPresent()) {
            return new ResponseEntity<>(applicationService.saveApplication(application), HttpStatus.CREATED);
        } else {
            throw new ApplicationRequestException("Application already exists.");
        }
    }

    /**
     * Updates an application in the database through the service after checking if the application does
     * exist
     *
     * @param application application object in the request body
     * @return a http response with an application object in a {@link ResponseEntity} with an accepted status
     * @throws ApplicationRequestException when application could not be found to be updated
     */
    @PutMapping
    public ResponseEntity<Application> updateApplication(@RequestBody Application application) throws ApplicationRequestException{
        Optional<Application> check = applicationService.findById(application.getApplicationId());
        if (check.isPresent()) {
            return new ResponseEntity<>(applicationService.saveApplication(application), HttpStatus.ACCEPTED);
        } else {
            throw new ApplicationRequestException("Could not update application with applicationId "+ application.getApplicationId());
        }
    }

    /**
     * Deletes an application in the database through the service after checking if the application does
     * exist
     *
     * @param application application object in the request body
     * @return a http response in a {@link ResponseEntity} with an accepted status
     * @throws ApplicationRequestException when application could not be found to be deleted
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteApplication(@RequestBody Application application) throws ApplicationRequestException{
        Optional<Application> check = applicationService.findById(application.getApplicationId());
        if (check.isPresent()) {
            applicationService.deleteApplication(application);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new ApplicationRequestException("Could not delete application with applicationId " + application.getApplicationId());
        }
    }

    /**
     * Gets an application object from the database through the service with an id
     *
     * @param id id of an application object in the path variable
     * @return a http response with an application object in a {@link ResponseEntity} with a found status
     * @throws ApplicationRequestException when no matching applications found
     */
    @GetMapping("id")
    public ResponseEntity<Application> getApplication(@RequestParam int id) throws ApplicationRequestException{
        Optional<Application> application = applicationService.findById(id);
        if (application.isPresent()){
            return new ResponseEntity<>(application.get(), HttpStatus.FOUND);
        } else {
            throw new ApplicationRequestException("Could not find application with applicationId " + id);
        }
    }

    /**
     * Gets a List of application objects from the database through the service that are associated with
     * the applicant object
     *
     * @param applicant applicant object in the request body
     * @return a http response with a List of application objects in a {@link ResponseEntity} with a found status
     * @throws ApplicationRequestException when no matching applications found
     */
    @GetMapping("applicant")
    public ResponseEntity<List<Application>> getApplicationByApplicant(@RequestBody Applicant applicant) throws ApplicationRequestException{
        List<Application> applications = applicationService.getAllApplicationsByApplicant(applicant);
        if(!applications.isEmpty()) {
            return new ResponseEntity(applications, HttpStatus.FOUND);
        } else {
            throw new ApplicationRequestException("Could not find any applications for applicant ");
        }
    }

    /**
     * Gets a List of application objects from the database through the service that are associated with
     * the applicantOccupation object
     *
     * @param applicantOccupation applicantOccupation object in the request body
     * @return a http response with a List of application objects in a {@link ResponseEntity} with a found status
     * @throws ApplicationRequestException when no matching applications found
     */
    @GetMapping("occupation")
    public ResponseEntity<List<Application>> getApplicationByOccupation(@RequestBody ApplicantOccupation
                                                                            applicantOccupation)
                                                                            throws ApplicationRequestException {

        List<Application> applications = applicationService.getAllApplicationsByApplicantOccupation(applicantOccupation);
        if (!applications.isEmpty()) {
            return new ResponseEntity(applications, HttpStatus.FOUND);
        } else {
            throw new ApplicationRequestException("Could not find any applications for occupation " + applicantOccupation.getJobTitle());
        }
    }

    /**
     * Gets a List of application objects from the database through the service that are associated with
     * the need object
     *
     * @param need need object in the request body
     * @return a http response with a List of application objects in a {@link ResponseEntity} with a found status
     * @throws ApplicationRequestException when no matching applications found
     */
    @GetMapping("need")
    public ResponseEntity<List<Application>> getApplicationByNeed(@RequestBody Need need) throws ApplicationRequestException {

        List<Application> applications = applicationService.getAllApplicationsByNeed(need);
        if(!applications.isEmpty()) {
            return new ResponseEntity(applications, HttpStatus.FOUND);
        } else {
            throw new ApplicationRequestException("Could not find applications for needId " + need.getNeedId());
        }
    }

    /**
     * Gets a List of application objects associated with a need and a certain applicant employment status.
     *
     * @param employmentStatus That status to be matched with applicants
     * @param needId The need to be matched with applications
     * @return http response with the list of application objects in a {@Link ResponseEntity} with a found status
     * @throws ApplicationRequestException when no matching applications found
     */
    @GetMapping("need/status")
    public ResponseEntity<List<Application>> getApplicationsByEmploymentStatusAndNeed(@RequestParam int needId,
                                                                                      @RequestParam String employmentStatus)
            throws ApplicationRequestException {

        List<Application> applications = applicationService.getApplicationByEmploymentStatusAndNeed(employmentStatus, needId);
        if(!applications.isEmpty()) {
            return new ResponseEntity(applications, HttpStatus.FOUND);
        } else {
            throw new ApplicationRequestException("Could not find " + employmentStatus + " applicants for needId " + needId);
        }
    }

    /**
     * Gets a List of applications associated with a certain client.
     *
     * @param clientId the client to be matched with applications
     * @return http response with the list of application objects in a (@Link ResponseEntity} with a found status
     * @throws ApplicationRequestException when no match found
     */
    @GetMapping("client")
    public ResponseEntity<List<Application>> getApplicationByClient(@RequestParam int clientId) throws ApplicationRequestException{

        List<Application> applications = applicationService.getApplicationByClient(clientId);
        if (!applications.isEmpty()) {
            return new ResponseEntity(applications, HttpStatus.FOUND);
        }
        else throw new ApplicationRequestException("Could not find applications matching clientId " + clientId);
    }

    /**
     * Gets a List of application objects associated with a client and a certain applicant employment status.
     *
     * @param employmentStatus That status to be matched with applicants
     * @param clientId The client to be matched with applications
     * @return http response with the list of application objects in a {@Link ResponseEntity} with a found status
     * @throws ApplicationRequestException when no matching applications found
     */

    @GetMapping("client/status")
    public ResponseEntity<List<Application>> getApplicationByEmploymentStatusAndClient(@RequestParam String employmentStatus,
                                                                                       @RequestParam int clientId)
                                                                                        throws ApplicationRequestException {

        List<Application> applications = applicationService.getApplicationByEmploymentStatusAndClient(employmentStatus, clientId);
        if(!applications.isEmpty()) {
            return new ResponseEntity(applications, HttpStatus.FOUND);
        } else {
            throw new ApplicationRequestException("Could not find " + employmentStatus + " applicants for clientId " + clientId);
        }
    }

//    @GetMapping
//    public ResponseEntity<List<Application>> getAllApplications() {
//        List<Application> applications = new ArrayList<>();//applicationService.getAllApplications();
//        Application app = new Application();
//        applications.add(app);
//        //if(!applications.isEmpty()){
//            return new ResponseEntity<>(applications, HttpStatus.FOUND);
//        //} else {
//         //   throw new ApplicationRequestException("No Applications");
//        //}

 //   }




}
