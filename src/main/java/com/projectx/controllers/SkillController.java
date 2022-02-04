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
     * @param skill
     * @return
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
     * @param skill
     * @return
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
     * @param skill
     * @return
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
     * @param id
     * @return
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
     * @param applicant
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills(@RequestBody Applicant applicant) {
        return new ResponseEntity<>(skillService.getAllSkills(applicant), HttpStatus.OK);
    }
}
