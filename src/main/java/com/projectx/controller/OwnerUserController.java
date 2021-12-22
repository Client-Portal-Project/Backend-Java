package com.projectx.controller;

import com.projectx.model.Owner;
import com.projectx.model.OwnerUser;
import com.projectx.model.User;
import com.projectx.service.OwnerService;
import com.projectx.service.OwnerUserService;
import com.projectx.service.UserService;
import com.projectx.utility.CrossOriginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class OwnerUserController {
    private final OwnerUserService ownerUserService;
    private final OwnerService ownerService;
    private final UserService userService;

    @Autowired
    public OwnerUserController(OwnerUserService ownerUserService, OwnerService ownerService, UserService userService){
        this.ownerUserService =ownerUserService;
        this.ownerService = ownerService;
        this.userService = userService;
    }

    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmin(){
        return ResponseEntity.ok(ownerUserService.getAll());
    }

    @GetMapping("/admins/owner/{ownerId}")
    public ResponseEntity<?> getAdminsByOwner(@PathVariable Integer ownerId){
        Owner owner = ownerService.getOwnerById(ownerId);
        return ResponseEntity.ok(ownerUserService.getListByOwner(owner));
    }

    @PostMapping("/admins/user/{userId}")
    public ResponseEntity<?> createAdmin(@RequestBody OwnerUser ownerUser, @PathVariable Integer userId){
        User user = userService.findUserById(userId);
        ownerUser.setUserId(user);
        OwnerUser admin = ownerUserService.createOrSave(ownerUser);
        user.setOwnerUser(admin);
        userService.createUser(user);
        return ResponseEntity.ok(admin);
    }

}
