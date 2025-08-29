package com.example.absen.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.absen.model.User;
import com.example.absen.service.FileStorageService;
import com.example.absen.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    // Create a new user with optional foto selfie (POST)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<User> createUser(
            @RequestPart("user") User user,
            @RequestPart(value = "fotoselfie", required = false) MultipartFile fotoselfieFile) {
        try {
            if (fotoselfieFile != null && !fotoselfieFile.isEmpty()) {
                String fileName = fileStorageService.storeFile(fotoselfieFile);
                user.setFotoselfie(fileName); // Simpan hanya nama file
            }

            User newUser = userService.createUser(user);
            buildFotoUrl(newUser); // Bangun URL untuk respons
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all users (READ)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            users.forEach(this::buildFotoUrl);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get user by nik (READ)
    @GetMapping("/{nik}")
    public ResponseEntity<User> getUserByNik(@PathVariable("nik") String nik) {
        Optional<User> userData = userService.getUserByNik(nik);
        if (userData.isPresent()) {
            User user = userData.get();
            buildFotoUrl(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a user by nik (UPDATE)
    @PutMapping("/{nik}")
    public ResponseEntity<User> updateUser(@PathVariable("nik") String nik, @RequestBody User user) {
        User updatedUser = userService.updateUser(nik, user);
        if (updatedUser != null) {
            buildFotoUrl(updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a user by nik (DELETE)
    @DeleteMapping("/{nik}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("nik") String nik) {
        try {
            userService.deleteUser(nik);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint baru: Update foto selfie saja
    @PutMapping(value = "/{nik}/upload-foto", consumes = {"multipart/form-data"})
    public ResponseEntity<User> uploadFotoSelfie(
            @PathVariable("nik") String nik,
            @RequestPart(value = "fotoselfie", required = false) MultipartFile fotoselfieFile) {
        try {
            Optional<User> existingUserOptional = userService.getUserByNik(nik);
            if (!existingUserOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            User existingUser = existingUserOptional.get();

            if (fotoselfieFile != null && !fotoselfieFile.isEmpty()) {
                String fileName = fileStorageService.storeFile(fotoselfieFile);
                existingUser.setFotoselfie(fileName);
            }

            User updatedUser = userService.updateUser(nik, existingUser);
            if (updatedUser != null) {
                buildFotoUrl(updatedUser);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void buildFotoUrl(User user) {
        String fileName = user.getFotoselfie();
        if (fileName != null && !fileName.isEmpty() && !fileName.startsWith("http")) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();
            user.setFotoselfie(fileDownloadUri);
        }
    }
}