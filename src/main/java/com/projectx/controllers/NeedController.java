package com.projectx.controllers;

import com.projectx.models.Client;
import com.projectx.models.Need;
import com.projectx.services.NeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("needController")
@RequestMapping("need")
@CrossOrigin
public class NeedController {
    @Autowired
    private NeedService needService;

    /**
     * Creates a new need in the database through the need service class
     *
     * @param need need object in the request body
     * @return a http response with a need object in a {@link ResponseEntity} that
     *      contains a created request, otherwise a null object with a bad request status code
     */
    @PostMapping
    public ResponseEntity<Need> createNeed(@RequestBody Need need) {
        Need check = needService.getNeed(need.getNeed_id());
        if (check == null) {
            return new ResponseEntity<>(needService.saveNeed(need), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Edits the need object in the database through the need service class
     *
     * @param need need object in the request body
     * @return a http response with a need object in a {@link ResponseEntity} that
     *      contains an ok request, otherwise a null object with a bad request status code
     */
    @PutMapping
    public ResponseEntity<Need> editNeed(@RequestBody Need need) {
        Need check = needService.getNeed(need.getNeed_id());
        if (check != null) {
            return new ResponseEntity<>(needService.saveNeed(need), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes the need object in the database through the need service class
     *
     * @param need need object in the request body
     * @return a http response with a boolean object in a {@link ResponseEntity} that
     *      contains an ok request and true, otherwise false with a bad request status code
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteNeed(@RequestBody Need need) {
        Need check = needService.getNeed(need.getNeed_id());
        if (check != null) {
            needService.deleteNeed(need);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets a need with its own id
     *
     * @param id id of the need in the path variable
     * @return a http response with a need object in a {@link ResponseEntity} that
     *      contains a found request, otherwise a null object with a bad request status code
     */
    @GetMapping("{id}")
    public ResponseEntity<Need> getNeed(@PathVariable int id) {
        Need need = needService.getNeed(id);
        if (need != null) {
            return new ResponseEntity<>(need, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets a list of need objects that are connected to a client object
     *
     * @param client client object in the request body
     * @return a http response with a List of need objects in a {@link ResponseEntity} that
     *     contains a ok request
     */
    @GetMapping
    public ResponseEntity<List<Need>> getNeedsByClient(@RequestBody Client client) {
        return new ResponseEntity<>(needService.getAllNeeds(client), HttpStatus.OK);
    }
}
