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
@Table(name = "kantors")
public class Kantor {

    @Id
    @Column(name = "idkantor", nullable = false)
    private String idkantor;

    @Column(name = "namakantor")
    private String namakantor;
}