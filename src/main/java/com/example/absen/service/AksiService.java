package com.example.absen.service;

import com.example.absen.model.Aksi;
import com.example.absen.repository.AksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AksiService {

    @Autowired
    private AksiRepository aksiRepository;

    public Aksi createAksi(Aksi aksi) {
        if (aksi.getIdaksi() == null || aksi.getIdaksi().isEmpty()) {
            // Logika untuk membuat ID otomatis
            long count = aksiRepository.count();
            String newId = String.format("aks%03d", count + 1); // Format: aks001, aks002, dst.
            aksi.setIdaksi(newId);
        }
        return aksiRepository.save(aksi);
    }

    public List<Aksi> getAllAksi() {
        return aksiRepository.findAll();
    }

    public Optional<Aksi> getAksiById(String idaksi) {
        return aksiRepository.findById(idaksi);
    }

    public Aksi updateAksi(String idaksi, Aksi aksiDetails) {
        Optional<Aksi> aksiData = aksiRepository.findById(idaksi);
        if (aksiData.isPresent()) {
            Aksi updatedAksi = aksiData.get();
            updatedAksi.setNamaaksi(aksiDetails.getNamaaksi());
            return aksiRepository.save(updatedAksi);
        }
        return null;
    }

    public void deleteAksi(String idaksi) {
        aksiRepository.deleteById(idaksi);
    }
}