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

import com.example.absen.model.Status;
import com.example.absen.service.StatusService;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    @Autowired
    private StatusService statusService;

    // Create a new Status (POST)
    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        try {
            Status newStatus = statusService.createStatus(status);
            return new ResponseEntity<>(newStatus, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all Statuses (READ)
    @GetMapping
    public ResponseEntity<List<Status>> getAllStatus() {
        try {
            List<Status> statuses = statusService.getAllStatus();
            if (statuses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(statuses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Status by id (READ)
    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable("id") String id) {
        Optional<Status> statusData = statusService.getStatusById(id);
        if (statusData.isPresent()) {
            return new ResponseEntity<>(statusData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a Status by id (UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable("id") String id, @RequestBody Status status) {
        Status updatedStatus = statusService.updateStatus(id, status);
        if (updatedStatus != null) {
            return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Status by id (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStatus(@PathVariable("id") String id) {
        try {
            statusService.deleteStatus(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}