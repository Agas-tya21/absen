package com.example.absen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.absen.model.RoleUser;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, String> {
}