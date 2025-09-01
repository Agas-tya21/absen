package com.example.absen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

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

    @Column(name = "koordinat")
    private String koordinat;

    @ManyToOne
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    private Status status;

    @Column(name = "fotobukti")
    private String fotobukti;
}