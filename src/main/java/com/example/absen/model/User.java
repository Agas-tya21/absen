package com.example.absen.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "nik", unique = true, nullable = false)
    private String nik;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nama")
    private String nama;

    @Column(name = "tanggallahir")
    private Date tanggallahir;

    @Column(name = "nohp")
    private String nohp;

    @ManyToOne
    @JoinColumn(name = "idroleuser", referencedColumnName = "idrole")
    private RoleUser roleUser;

    @Column(name = "fotoselfie")
    private String fotoselfie;

    @ManyToOne
    @JoinColumn(name = "idkantor", referencedColumnName = "idkantor")
    private Kantor kantor;
}