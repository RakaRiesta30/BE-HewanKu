package com.TuBes.HewanKu.Shelter;

import java.io.Serializable;
import java.util.List;

import com.TuBes.HewanKu.Hewan.Hewan;
import com.TuBes.HewanKu.Pesanan.Pesanan;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shelter")
public class Shelter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama;
    private String namaDepan;
    private String namaBelakang;
    private String noTelepon;
    private String email;
    private boolean status_shelter = false;
    @JsonIgnore
    private String password;
    private String otp;
    private int hewanDibeli;

    @OneToMany(mappedBy = "shelter")
    @JsonIgnore
    private List<Hewan> daftarHewan;

    @OneToMany(mappedBy = "shelter")
    @JsonIgnore
    private List<Pesanan> pesanan;

    @OneToOne(mappedBy = "shelter", cascade = CascadeType.ALL)
    private ShelterAcc shelterAcc;

    public ShelterAcc getShelterAcc() {
        return shelterAcc;
    }

    public void setShelterAcc(ShelterAcc shelterAcc) {
        this.shelterAcc = shelterAcc;
    }

    public Shelter() {
    }

    public Shelter(String email, String namaDepan, String namaBelakang, String noTelepon, String password) {
        this.email = email;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.noTelepon = noTelepon;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Hewan> getDaftarHewan() {
        return daftarHewan;
    }

    public void setDaftarHewan(List<Hewan> daftarHewan) {
        this.daftarHewan = daftarHewan;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public List<Pesanan> getPesanan() {
        return pesanan;
    }

    public void setPesanan(List<Pesanan> pesanan) {
        this.pesanan = pesanan;
    }

    public boolean isStatusShelter() {
        return status_shelter;
    }

    public void setStatusShelter(boolean status_shelter) {
        this.status_shelter = status_shelter;
    }

    public int getHewanDibeli() {
        return hewanDibeli;
    }

    public void setHewanDibeli(int hewanDibeli) {
        this.hewanDibeli = hewanDibeli;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}