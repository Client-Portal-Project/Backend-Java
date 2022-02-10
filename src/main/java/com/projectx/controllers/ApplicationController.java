package com.projectx.controllers;

import com.projectx.Driver;
import com.projectx.models.Application;
import com.projectx.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
