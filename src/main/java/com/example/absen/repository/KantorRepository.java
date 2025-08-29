package com.example.absen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.absen.model.Kantor;

@Repository
public interface KantorRepository extends JpaRepository<Kantor, String> {
}