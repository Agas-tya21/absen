package com.example.absen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aksis")
public class Aksi {

    @Id
    @Column(name = "idaksi", nullable = false)
    private String idaksi;

    @Column(name = "namaaksi")
    private String namaaksi;
}