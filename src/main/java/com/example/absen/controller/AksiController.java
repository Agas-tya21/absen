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

import com.example.absen.model.Aksi;
import com.example.absen.service.AksiService;

@RestController
@RequestMapping("/api/aksi")
public class AksiController {

    @Autowired
    private AksiService aksiService;

    // Create a new Aksi (POST)
    @PostMapping
    public ResponseEntity<Aksi> createAksi(@RequestBody Aksi aksi) {
        try {
            Aksi newAksi = aksiService.createAksi(aksi);
            return new ResponseEntity<>(newAksi, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all Aksi (READ)
    @GetMapping
    public ResponseEntity<List<Aksi>> getAllAksi() {
        try {
            List<Aksi> aksi = aksiService.getAllAksi();
            if (aksi.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(aksi, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Aksi by id (READ)
    @GetMapping("/{id}")
    public ResponseEntity<Aksi> getAksiById(@PathVariable("id") String id) {
        Optional<Aksi> aksiData = aksiService.getAksiById(id);
        if (aksiData.isPresent()) {
            return new ResponseEntity<>(aksiData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an Aksi by id (UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<Aksi> updateAksi(@PathVariable("id") String id, @RequestBody Aksi aksi) {
        Aksi updatedAksi = aksiService.updateAksi(id, aksi);
        if (updatedAksi != null) {
            return new ResponseEntity<>(updatedAksi, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an Aksi by id (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAksi(@PathVariable("id") String id) {
        try {
            aksiService.deleteAksi(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}