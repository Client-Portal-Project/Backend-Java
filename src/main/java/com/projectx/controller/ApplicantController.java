package com.projectx.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.model.Applicant;
import com.projectx.service.ApplicantService;
import com.projectx.utility.CrossOriginUtil;
import com.projectx.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("applicantController")
@RequestMapping(value = "api")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class ApplicantController {
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private JwtUtil jwtUtil;

    //create
    @PostMapping
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant,
                                                     @RequestHeader Map<String, String> headers) {
        DecodedJWT decodedJWT = jwtUtil.verify(headers.get("authorization"));
        if (decodedJWT == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            
        }
        return new ResponseEntity<>(applicant, HttpStatus.OK);
    }
    //edit

    //delete

    //get/getAll
}
