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
@Table(name = "transaksis")
public class Transaksi {

    @Id
    @Column(name = "idtransaksi", nullable = false)
    private String idtransaksi;

    @ManyToOne
    @JoinColumn(name = "nik", referencedColumnName = "nik")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idaksi", referencedColumnName = "idaksi")
    private Aksi aksi;

    @Column(name = "keterangan")
    private String keterangan;

    @Column(name = "waktutransaksi")
    private Date waktutransaksi;

    @ManyToOne
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    private Status status;

    @Column(name = "fotobukti")
    private String fotobukti;
}