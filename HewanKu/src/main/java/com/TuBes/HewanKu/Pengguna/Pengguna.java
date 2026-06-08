package com.TuBes.HewanKu.Pengguna;

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
@Table(name = "pengguna")
public class Pengguna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namaDepan;
    private String namaBelakang;
    private String noTelepon;
    private String email;
    private String negaraDaerah;
    private String jalan;
    private String zipCode;
    private String username;
    private String displayName;
    @JsonIgnore
    private String password;
    private String otp;
    private List<Hewan> favorit;

    public Pengguna() {
    }

    @OneToMany(mappedBy = "pengguna")
    @JsonIgnore
    private List<Pesanan> pesanan;

    public Pengguna(String namaDepan, String namaBelakang, String noTelepon, String email, String password) {
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.noTelepon = noTelepon;
        this.email = email;
        this.password = password;
    }

    public Pengguna(String otp) {
        this.otp = otp;
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

    public List<Hewan> getFavorit() {
        return favorit;
    }

    public void setFavorit(List<Hewan> favorit) {
        this.favorit = favorit;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public String getNegaraDaerah() {
        return negaraDaerah;
    }

    public void setNegaraDaerah(String negaraDaerah) {
        this.negaraDaerah = negaraDaerah;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
