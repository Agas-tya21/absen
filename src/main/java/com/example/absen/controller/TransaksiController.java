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

import com.example.absen.model.Transaksi;
import com.example.absen.service.FileStorageService;
import com.example.absen.service.TransaksiService;

@RestController
@RequestMapping("/api/transaksis")
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private FileStorageService fileStorageService;

    // Create a new transaction with optional photo (POST)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Transaksi> createTransaksi(
            @RequestPart("transaksi") Transaksi transaksi,
            @RequestPart(value = "fotobukti", required = false) MultipartFile fotobuktiFile) {
        try {
            if (fotobuktiFile != null && !fotobuktiFile.isEmpty()) {
                String fileName = fileStorageService.storeFile(fotobuktiFile);
                transaksi.setFotobukti(fileName); // Simpan hanya nama file
            }

            Transaksi newTransaksi = transaksiService.createTransaksi(transaksi);
            buildFotoUrl(newTransaksi); // Bangun URL untuk respons
            return new ResponseEntity<>(newTransaksi, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all transaksis (READ)
    @GetMapping
    public ResponseEntity<List<Transaksi>> getAllTransaksis() {
        try {
            List<Transaksi> transaksis = transaksiService.getAllTransaksis();
            if (transaksis.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            transaksis.forEach(this::buildFotoUrl);
            return new ResponseEntity<>(transaksis, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get transaksi by id (READ)
    @GetMapping("/{id}")
    public ResponseEntity<Transaksi> getTransaksiById(@PathVariable("id") String id) {
        Optional<Transaksi> transaksiData = transaksiService.getTransaksiById(id);
        if (transaksiData.isPresent()) {
            Transaksi transaksi = transaksiData.get();
            buildFotoUrl(transaksi);
            return new ResponseEntity<>(transaksi, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a transaksi by id (UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<Transaksi> updateTransaksi(@PathVariable("id") String id, @RequestBody Transaksi transaksi) {
        Transaksi updatedTransaksi = transaksiService.updateTransaksi(id, transaksi);
        if (updatedTransaksi != null) {
            buildFotoUrl(updatedTransaksi);
            return new ResponseEntity<>(updatedTransaksi, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a transaksi by id (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTransaksi(@PathVariable("id") String id) {
        try {
            transaksiService.deleteTransaksi(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // New Endpoint: Update a transaction's photo by id
    @PutMapping(value = "/{idtransaksi}/upload-bukti", consumes = {"multipart/form-data"})
    public ResponseEntity<Transaksi> uploadFotoBukti(
            @PathVariable("idtransaksi") String idtransaksi,
            @RequestPart(value = "fotobukti", required = false) MultipartFile fotobuktiFile) {
        try {
            Optional<Transaksi> existingTransaksiOptional = transaksiService.getTransaksiById(idtransaksi);
            if (!existingTransaksiOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Transaksi existingTransaksi = existingTransaksiOptional.get();

            if (fotobuktiFile != null && !fotobuktiFile.isEmpty()) {
                String fileName = fileStorageService.storeFile(fotobuktiFile);
                existingTransaksi.setFotobukti(fileName); // Simpan hanya nama file
            }

            Transaksi updatedTransaksi = transaksiService.updateTransaksi(idtransaksi, existingTransaksi);
            if (updatedTransaksi != null) {
                buildFotoUrl(updatedTransaksi);
                return new ResponseEntity<>(updatedTransaksi, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void buildFotoUrl(Transaksi transaksi) {
        String fileName = transaksi.getFotobukti();
        if (fileName != null && !fileName.isEmpty() && !fileName.startsWith("http")) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();
            transaksi.setFotobukti(fileDownloadUri);
        }
    }
}