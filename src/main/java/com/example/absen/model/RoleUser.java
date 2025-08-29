package com.example.absen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_users")
public class RoleUser {

    @Id
    @Column(name = "idrole", nullable = false)
    private String idrole;

    @Column(name = "namarole")
    private String namarole;
}