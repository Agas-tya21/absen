package com.example.absen.repository;

import java.util.Optional; // Tambahkan import ini

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.absen.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email); // Tambahkan baris ini
}