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

import com.example.absen.model.Kantor;
import com.example.absen.service.KantorService;

@RestController
@RequestMapping("/api/kantors")
public class KantorController {

    @Autowired
    private KantorService kantorService;

    // Create a new Kantor (POST)
    @PostMapping
    public ResponseEntity<Kantor> createKantor(@RequestBody Kantor kantor) {
        try {
            Kantor newKantor = kantorService.createKantor(kantor);
            return new ResponseEntity<>(newKantor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all Kantors (READ)
    @GetMapping
    public ResponseEntity<List<Kantor>> getAllKantor() {
        try {
            List<Kantor> kantors = kantorService.getAllKantor();
            if (kantors.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(kantors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Kantor by id (READ)
    @GetMapping("/{id}")
    public ResponseEntity<Kantor> getKantorById(@PathVariable("id") String id) {
        Optional<Kantor> kantorData = kantorService.getKantorById(id);
        if (kantorData.isPresent()) {
            return new ResponseEntity<>(kantorData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a Kantor by id (UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<Kantor> updateKantor(@PathVariable("id") String id, @RequestBody Kantor kantor) {
        Kantor updatedKantor = kantorService.updateKantor(id, kantor);
        if (updatedKantor != null) {
            return new ResponseEntity<>(updatedKantor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Kantor by id (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteKantor(@PathVariable("id") String id) {
        try {
            kantorService.deleteKantor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}