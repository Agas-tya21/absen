package com.example.absen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.absen.model.Aksi;

@Repository
public interface AksiRepository extends JpaRepository<Aksi, String> {
}