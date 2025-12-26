package com.TuBes.HewanKu.Shelter;

import java.io.Serializable;
import java.util.List;

import com.TuBes.HewanKu.Hewan.Hewan;
import com.TuBes.HewanKu.Pesanan.Pesanan;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "shelter")
public class Shelter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama;
    private String noTelepon;
    private String email;
    private String password;
    private String otp;
    
    @OneToMany(mappedBy = "shelter")
    @JsonIgnore
    private List<Hewan> daftarHewan;
    
    @OneToMany(mappedBy = "shelter")
    @JsonIgnore
    private List<Pesanan> pesanan;

    public Shelter() {
    }

    public Shelter(String email, String nama, String noTelepon, String password) {
        this.email = email;
        this.nama = nama;
        this.noTelepon = noTelepon;
        this.password = password;
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

}