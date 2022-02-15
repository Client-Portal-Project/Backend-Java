package com.projectx.controllers;

import com.projectx.models.Applicant;
import com.projectx.models.Skill;
import com.projectx.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("skillController")
@RequestMapping("skill")
@CrossOrigin
public class SkillController {
    @Autowired
    private SkillService skillService;

    /**
     * Checks if the skill exist in the database and then creates a skill for the database
     * through the service if it does not exist.
     *
     * @param skill skill object in the request body
     * @return a http response with a skill object in a {@link ResponseEntity} that contains a created request if
     *      skill does not exist, otherwise sends back a null object and a bad request status code
     */
    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        Skill check = skillService.getSkill(skill.getSkillId());
        if (check == null) {
            return new ResponseEntity<>(skillService.saveSkill(skill), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Checks if the skill exist in the database and then updates a skill for the database
     * through the service if it does exist.
     *
     * @param skill skill object in the request body
     * @return a http response with a skill object in a {@link ResponseEntity} that contains an ok request if
     *      skill does exist, otherwise sends back a null object and a bad request status code
     */
    @PutMapping
    public ResponseEntity<Skill> editSkill(@RequestBody Skill skill) {
        Skill check = skillService.getSkill(skill.getSkillId());
        if (check != null) {
            return new ResponseEntity<>(skillService.saveSkill(skill), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Checks if the skill exists in the database and then deletes it if it does exist through the service
     *
     * @param skill skill object in the request body
     * @return a http response in a {@link ResponseEntity} that contains an ok request if
     *      skill is deleted, otherwise sends back a bad request status code
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteSkill(@RequestBody Skill skill) {
        Skill check = skillService.getSkill(skill.getSkillId());
        if (check != null) {
            skillService.deleteSkill(skill);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets a skill by using the id of that skill in the path variable
     *
     * @param id id of the skill in the path variable
     * @return a http response with a skill object in a {@link ResponseEntity} that contains a found request if
     *      skill is found, otherwise sends back null object and a not found status code
     */
    @GetMapping("{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable int id) {
        Skill temp = skillService.getSkill(id);
        if (temp != null) {
            return new ResponseEntity<>(temp, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets a List of skill objects associated with an applicant that was sent in the request body
     *
     * @param applicant applicant object in the request body
     * @return a http response with a List of skill objects in a {@link ResponseEntity} that
     *      contains an ok request
     */
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills(@RequestBody Applicant applicant) {
        return new ResponseEntity<>(skillService.getAllSkills(applicant), HttpStatus.OK);
    }
}
