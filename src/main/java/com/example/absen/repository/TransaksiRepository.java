package com.example.absen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.absen.model.Transaksi;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, String> {
}