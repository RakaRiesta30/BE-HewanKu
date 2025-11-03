package com.TuBes.HewanKu.Hewan;

import com.TuBes.HewanKu.Shelter.Shelter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "hewan")
public class Hewan implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hewan_sequence")
    @SequenceGenerator(name="hewan_sequence", sequenceName="hewan_sequence", allocationSize=100)
    private Long id;
    private String nama;
    private String jenis;
    private Long harga;
    private String status;
    private String kesehatan;
    private LocalDate updatedDate;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    @JsonIgnore
    private Shelter shelter;

    public Hewan() {
    }

    public Hewan(Long harga, String jenis, String kesehatan, String nama, String status, LocalDate updatedDate, Shelter shelter) {
        this.harga = harga;
        this.jenis = jenis;
        this.kesehatan = kesehatan;
        this.nama = nama;
        this.status = status;
        this.updatedDate = updatedDate;
        this.shelter = shelter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Long getHarga() {
        return harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKesehatan() {
        return kesehatan;
    }

    public void setKesehatan(String kesehatan) {
        this.kesehatan = kesehatan;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }


}