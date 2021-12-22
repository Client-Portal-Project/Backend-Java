package com.projectx.controller;

import com.projectx.model.Applicant;
import com.projectx.model.Owner;
import com.projectx.model.User;
import com.projectx.service.ApplicantService;
import com.projectx.service.OwnerService;
import com.projectx.service.UserService;
import com.projectx.utility.CrossOriginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ApplicantController {
    private final ApplicantService applicantService;
    private final OwnerService ownerService;
    private final UserService userService;

    @Autowired
    public ApplicantController(ApplicantService applicantService, OwnerService ownerService, UserService userService){
        this.applicantService = applicantService;
        this.ownerService = ownerService;
        this.userService = userService;
    }

    @GetMapping("/applicants")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(applicantService.getAll());
    }

    @GetMapping("/applicants/owner/{ownerId}")
    public ResponseEntity<?> getListByOwner(@PathVariable Integer ownerId){
        Owner owner = ownerService.getOwnerById(ownerId);

        return ResponseEntity.ok(applicantService.getListByOwner(owner));
    }

    @GetMapping("/applicants/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        return ResponseEntity.ok(applicantService.getById(id));
    }


    @PostMapping("/applicant/user/{userId}")
    public ResponseEntity<?> create(@RequestBody Applicant applicant, @PathVariable Integer userId){
        User user = userService.findUserById(userId);
        applicant.setUserId(user);

        Applicant newApplicant = applicantService.createOrSave(applicant);

        user.setApplicant(newApplicant);
        userService.createUser(user);
        return ResponseEntity.ok(newApplicant);
    }
}
