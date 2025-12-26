package com.TuBes.HewanKu.Pesanan;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

import com.TuBes.HewanKu.Hewan.Hewan;
import com.TuBes.HewanKu.Pengguna.Pengguna;
import com.TuBes.HewanKu.Shelter.Shelter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pesanan")
public class Pesanan implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    Random random = new Random();
    private String kodePemesanan = "#" + String.valueOf(10000000 + random.nextInt(90000000));
    private String form;
    private String status;
    private LocalDate timeLeft;
    
    @ManyToOne
    @JoinColumn(name = "pengguna_id")
    @JsonIgnore
    private Pengguna pengguna;

    @ManyToOne
    @JoinColumn(name = "hewan_id")
    private Hewan hewan;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    @JsonIgnore
    private Shelter shelter;
    
    public Pesanan() {
    }

    public Pesanan(String form, Hewan hewan, Pengguna pengguna, String status, LocalDate timeLeft) {
        this.form = form;
        this.hewan = hewan;
        this.pengguna = pengguna;
        this.status = status;
        this.timeLeft = timeLeft;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodePemesanan() {
        return kodePemesanan;
    }

    public void setKodePemesanan(String kodePemesanan) {
        this.kodePemesanan = kodePemesanan;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(LocalDate timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Pengguna getPengguna() {
        return pengguna;
    }

    public void setPengguna(Pengguna pengguna) {
        this.pengguna = pengguna;
    }

    public Hewan getHewan() {
        return hewan;
    }

    public void setHewan(Hewan hewan) {
        this.hewan = hewan;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }


}