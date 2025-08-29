package com.example.absen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.absen.model.Aksi;
import com.example.absen.repository.AksiRepository;

@Service
public class AksiService {

    @Autowired
    private AksiRepository aksiRepository;

    public Aksi createAksi(Aksi aksi) {
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