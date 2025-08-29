package com.example.absen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.absen.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {
}