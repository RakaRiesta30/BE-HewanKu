package com.TuBes.HewanKu.Hewan;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.TuBes.HewanKu.Shelter.Shelter;
import com.TuBes.HewanKu.Ulasan.Ulasan;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "hewan")
public class Hewan implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hewan_sequence")
    @SequenceGenerator(name="hewan_sequence", sequenceName="hewan_sequence", allocationSize=100)
    private Long id;
    private String nama;
    private String jenis;
    private double harga;
    private String status;
    private String kesehatan;
    private LocalDate updatedDate;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    @JsonIgnore
    private Shelter shelter;

    @OneToMany(mappedBy="hewan")
    @JsonIgnore
    private List<Ulasan> ulasan;

    public Hewan() {
    }

    public Hewan(double harga, String jenis, String kesehatan, String nama, double rating, Shelter shelter, String status, LocalDate updatedDate) {
        this.harga = harga;
        this.jenis = jenis;
        this.kesehatan = kesehatan;
        this.nama = nama;
        this.rating = rating;
        this.shelter = shelter;
        this.status = status;
        this.updatedDate = updatedDate;
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

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Ulasan> getUlasan() {
        return ulasan;
    }

    public void setUlasan(List<Ulasan> ulasan) {
        this.ulasan = ulasan;
    }
}