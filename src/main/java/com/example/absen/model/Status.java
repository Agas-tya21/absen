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
@Table(name = "statuses")
public class Status {

    @Id
    @Column(name = "idstatus", nullable = false)
    private String idstatus;

    @Column(name = "namastatus")
    private String namastatus;
}