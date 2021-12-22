package com.projectx.controller;

import com.projectx.model.Skill;
import com.projectx.service.SkillService;
import com.projectx.utility.CrossOriginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class SkillController {
    private final SkillService service;

    @Autowired
    public SkillController(SkillService service){
        this.service = service;
    }

    @GetMapping("/skills")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/skills")
    public ResponseEntity<?> create(@RequestBody Skill skill){
        return ResponseEntity.ok(service.createOrSave(skill));
    }
}
