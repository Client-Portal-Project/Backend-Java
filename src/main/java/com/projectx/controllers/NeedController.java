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
    NeedService needService;

    @PostMapping
    public ResponseEntity<Need> createNeed(@RequestBody Need need) {
        Need check = needService.getNeed(need.getNeedId());
        if (check == null) {
            return new ResponseEntity<>(needService.saveNeed(need), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Need> editNeed(@RequestBody Need need) {
        Need check = needService.getNeed(need.getNeedId());
        if (check != null) {
            return new ResponseEntity<>(needService.saveNeed(need), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteNeed(@RequestBody Need need) {
        Need check = needService.getNeed(need.getNeedId());
        if (check != null) {
            needService.deleteNeed(need);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Need> getNeed(@PathVariable int id) {
        Need need = needService.getNeed(id);
        if (need != null) {
            return new ResponseEntity<>(need, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Need>> getNeedsByClient(@RequestBody Client client) {
        return new ResponseEntity<>(needService.getAllNeeds(client), HttpStatus.OK);
    }
}
