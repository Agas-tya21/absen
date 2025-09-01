package com.example.absen.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.absen.model.Aksi;
import com.example.absen.model.Status;
import com.example.absen.model.Transaksi;
import com.example.absen.model.User;
import com.example.absen.repository.AksiRepository;
import com.example.absen.repository.StatusRepository;
import com.example.absen.repository.TransaksiRepository;
import com.example.absen.repository.UserRepository;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AksiRepository aksiRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Transactional
    public Transaksi createTransaksi(Transaksi transaksi, MultipartFile fotobuktiFile) throws IOException {
        // Retrieve full entities from repositories using IDs
        User user = Optional.ofNullable(transaksi.getUser())
            .flatMap(u -> userRepository.findById(u.getNik()))
            .orElseThrow(() -> new RuntimeException("User with NIK " + (transaksi.getUser() != null ? transaksi.getUser().getNik() : "null") + " not found."));

        Aksi aksi = Optional.ofNullable(transaksi.getAksi())
            .flatMap(a -> aksiRepository.findById(a.getIdaksi()))
            .orElseThrow(() -> new RuntimeException("Aksi with ID " + (transaksi.getAksi() != null ? transaksi.getAksi().getIdaksi() : "null") + " not found."));

        Status status = Optional.ofNullable(transaksi.getStatus())
            .flatMap(s -> statusRepository.findById(s.getIdstatus()))
            .orElseThrow(() -> new RuntimeException("Status with ID " + (transaksi.getStatus() != null ? transaksi.getStatus().getIdstatus() : "null") + " not found."));

        // Generate ID for transaction
        transaksi.setIdtransaksi(UUID.randomUUID().toString());
        transaksi.setWaktutransaksi(new Date());

        // Handle file upload
        if (fotobuktiFile != null && !fotobuktiFile.isEmpty()) {
            String fileName = fileStorageService.storeFile(fotobuktiFile);
            transaksi.setFotobukti(fileName);
        }

        // Set the full entities
        transaksi.setUser(user);
        transaksi.setAksi(aksi);
        transaksi.setStatus(status);

        return transaksiRepository.save(transaksi);
    }

    public List<Transaksi> getAllTransaksis() {
        return transaksiRepository.findAll();
    }

    public Optional<Transaksi> getTransaksiById(String idtransaksi) {
        return transaksiRepository.findById(idtransaksi);
    }

    @Transactional
    public Transaksi updateTransaksi(String idtransaksi, Transaksi transaksiDetails) {
        Optional<Transaksi> transaksiData = transaksiRepository.findById(idtransaksi);
        if (transaksiData.isPresent()) {
            Transaksi updatedTransaksi = transaksiData.get();
            updatedTransaksi.setKeterangan(transaksiDetails.getKeterangan());
            updatedTransaksi.setWaktutransaksi(transaksiDetails.getWaktutransaksi());
            updatedTransaksi.setKoordinat(transaksiDetails.getKoordinat());
            updatedTransaksi.setFotobukti(transaksiDetails.getFotobukti());
            
            // Perbarui relasi User dengan pengecekan
            User user = Optional.ofNullable(transaksiDetails.getUser())
                .flatMap(u -> userRepository.findById(u.getNik()))
                .orElseThrow(() -> new RuntimeException("User with NIK " + (transaksiDetails.getUser() != null ? transaksiDetails.getUser().getNik() : "null") + " not found."));
            updatedTransaksi.setUser(user);

            // Perbarui relasi Aksi dengan pengecekan
            Aksi aksi = Optional.ofNullable(transaksiDetails.getAksi())
                .flatMap(a -> aksiRepository.findById(a.getIdaksi()))
                .orElseThrow(() -> new RuntimeException("Aksi with ID " + (transaksiDetails.getAksi() != null ? transaksiDetails.getAksi().getIdaksi() : "null") + " not found."));
            updatedTransaksi.setAksi(aksi);

            // Perbarui relasi Status dengan pengecekan
            Status status = Optional.ofNullable(transaksiDetails.getStatus())
                .flatMap(s -> statusRepository.findById(s.getIdstatus()))
                .orElseThrow(() -> new RuntimeException("Status with ID " + (transaksiDetails.getStatus() != null ? transaksiDetails.getStatus().getIdstatus() : "null") + " not found."));
            updatedTransaksi.setStatus(status);

            return transaksiRepository.save(updatedTransaksi);
        }
        return null;
    }

    public void deleteTransaksi(String idtransaksi) {
        transaksiRepository.deleteById(idtransaksi);
    }
}