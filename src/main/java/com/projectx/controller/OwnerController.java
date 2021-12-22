package com.projectx.controller;

import com.projectx.DTOs.ResponseMessage;
import com.projectx.model.Owner;
import com.projectx.service.OwnerService;
import com.projectx.utility.CrossOriginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService){
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    public ResponseEntity<?> getAllOwner(){
        return ResponseEntity.ok(ownerService.getAll());
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable Integer id){
        Owner owner = ownerService.getOwnerById(id);
        if(owner !=null){
            return ResponseEntity.ok(owner);
        }
        ResponseMessage message = new ResponseMessage("No owners with that id");
        return ResponseEntity.badRequest().body(message);
    }

    @PostMapping("/owners")
    public ResponseEntity<?> createOwner(@RequestBody Owner owner){
        return ResponseEntity.ok(ownerService.createOrSave(owner));
    }
}
