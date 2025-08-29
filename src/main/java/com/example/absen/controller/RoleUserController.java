package com.example.absen.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.absen.model.RoleUser;
import com.example.absen.service.RoleUserService;

@RestController
@RequestMapping("/api/roleusers")
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    // Create a new RoleUser (POST)
    @PostMapping
    public ResponseEntity<RoleUser> createRoleUser(@RequestBody RoleUser roleUser) {
        try {
            RoleUser newRoleUser = roleUserService.createRoleUser(roleUser);
            return new ResponseEntity<>(newRoleUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all RoleUsers (READ)
    @GetMapping
    public ResponseEntity<List<RoleUser>> getAllRoleUsers() {
        try {
            List<RoleUser> roleUsers = roleUserService.getAllRoleUsers();
            if (roleUsers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(roleUsers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get RoleUser by id (READ)
    @GetMapping("/{id}")
    public ResponseEntity<RoleUser> getRoleUserById(@PathVariable("id") String id) {
        Optional<RoleUser> roleUserData = roleUserService.getRoleUserById(id);
        if (roleUserData.isPresent()) {
            return new ResponseEntity<>(roleUserData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a RoleUser by id (UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<RoleUser> updateRoleUser(@PathVariable("id") String id, @RequestBody RoleUser roleUser) {
        RoleUser updatedRoleUser = roleUserService.updateRoleUser(id, roleUser);
        if (updatedRoleUser != null) {
            return new ResponseEntity<>(updatedRoleUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a RoleUser by id (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRoleUser(@PathVariable("id") String id) {
        try {
            roleUserService.deleteRoleUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}