package com.example.absen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.absen.model.Transaksi;
import com.example.absen.repository.TransaksiRepository;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiRepository transaksiRepository;

    public Transaksi createTransaksi(Transaksi transaksi) {
        return transaksiRepository.save(transaksi);
    }

    public List<Transaksi> getAllTransaksis() {
        return transaksiRepository.findAll();
    }

    public Optional<Transaksi> getTransaksiById(String idtransaksi) {
        return transaksiRepository.findById(idtransaksi);
    }

    public Transaksi updateTransaksi(String idtransaksi, Transaksi transaksiDetails) {
        Optional<Transaksi> transaksiData = transaksiRepository.findById(idtransaksi);
        if (transaksiData.isPresent()) {
            Transaksi updatedTransaksi = transaksiData.get();
            updatedTransaksi.setUser(transaksiDetails.getUser());
            updatedTransaksi.setAksi(transaksiDetails.getAksi());
            updatedTransaksi.setKeterangan(transaksiDetails.getKeterangan());
            updatedTransaksi.setWaktutransaksi(transaksiDetails.getWaktutransaksi());
            updatedTransaksi.setStatus(transaksiDetails.getStatus());
            updatedTransaksi.setFotobukti(transaksiDetails.getFotobukti());
            return transaksiRepository.save(updatedTransaksi);
        }
        return null;
    }

    public void deleteTransaksi(String idtransaksi) {
        transaksiRepository.deleteById(idtransaksi);
    }
}