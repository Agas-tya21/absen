package com.example.absen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.absen.model.Kantor;
import com.example.absen.repository.KantorRepository;

@Service
public class KantorService {

    @Autowired
    private KantorRepository kantorRepository;

    public Kantor createKantor(Kantor kantor) {
        // Cek jika ID Kantor tidak ada, maka buat secara otomatis
        if (kantor.getIdkantor() == null || kantor.getIdkantor().isEmpty()) {
            long count = kantorRepository.count();
            String newId = String.format("k%03d", count + 1);
            kantor.setIdkantor(newId);
        }
        return kantorRepository.save(kantor);
    }

    public List<Kantor> getAllKantor() {
        return kantorRepository.findAll();
    }

    public Optional<Kantor> getKantorById(String idkantor) {
        return kantorRepository.findById(idkantor);
    }

    public Kantor updateKantor(String idkantor, Kantor kantorDetails) {
        Optional<Kantor> kantorData = kantorRepository.findById(idkantor);
        if (kantorData.isPresent()) {
            Kantor updatedKantor = kantorData.get();
            updatedKantor.setNamakantor(kantorDetails.getNamakantor());
            return kantorRepository.save(updatedKantor);
        }
        return null;
    }

    public void deleteKantor(String idkantor) {
        kantorRepository.deleteById(idkantor);
    }
}