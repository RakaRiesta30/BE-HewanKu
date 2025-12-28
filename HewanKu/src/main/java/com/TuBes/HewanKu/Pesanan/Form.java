package com.TuBes.HewanKu.Pesanan;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "form")
public class Form implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nama;
    private String email;
    private String noTelepon;
    private String tanggalLahir;
    private String jenisKelamin;
    private String daerah;
    private String jalan;
    private String zipCode;
    private String pekerjaanStatus;
    private String tempatTinggal;
    private boolean hewanSebelumnya;
    private String jenisHewan;
    private String tanggalHewan;
    private boolean memilikiHewan;
    private boolean keluargaAlergi;
    private boolean lingkunganAman;

    @OneToOne
    @JoinColumn(name="pesanan_id",unique=true)
    @JsonIgnore
    private Pesanan pesanan;

    public Form() {
    }

    public Form(String daerah, String email, boolean hewanSebelumnya, String jalan, String jenisHewan, String jenisKelamin, boolean keluargaAlergi, boolean lingkunganAman, boolean memilikiHewan, String nama, String noTelepon, String pekerjaanStatus, Pesanan pesanan, String tanggalHewan, String tanggalLahir, String tempatTinggal, String zipCode) {
        this.daerah = daerah;
        this.email = email;
        this.hewanSebelumnya = hewanSebelumnya;
        this.jalan = jalan;
        this.jenisHewan = jenisHewan;
        this.jenisKelamin = jenisKelamin;
        this.keluargaAlergi = keluargaAlergi;
        this.lingkunganAman = lingkunganAman;
        this.memilikiHewan = memilikiHewan;
        this.nama = nama;
        this.noTelepon = noTelepon;
        this.pekerjaanStatus = pekerjaanStatus;
        this.pesanan = pesanan;
        this.tanggalHewan = tanggalHewan;
        this.tanggalLahir = tanggalLahir;
        this.tempatTinggal = tempatTinggal;
        this.zipCode = zipCode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getDaerah() {
        return daerah;
    }

    public void setDaerah(String daerah) {
        this.daerah = daerah;
    }

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPekerjaanStatus() {
        return pekerjaanStatus;
    }

    public void setPekerjaanStatus(String pekerjaanStatus) {
        this.pekerjaanStatus = pekerjaanStatus;
    }

    public String getTempatTinggal() {
        return tempatTinggal;
    }

    public void setTempatTinggal(String tempatTinggal) {
        this.tempatTinggal = tempatTinggal;
    }

    public boolean isHewanSebelumnya() {
        return hewanSebelumnya;
    }

    public void setHewanSebelumnya(boolean hewanSebelumnya) {
        this.hewanSebelumnya = hewanSebelumnya;
    }

    public String getJenisHewan() {
        return jenisHewan;
    }

    public void setJenisHewan(String jenisHewan) {
        this.jenisHewan = jenisHewan;
    }

    public String getTanggalHewan() {
        return tanggalHewan;
    }

    public void setTanggalHewan(String tanggalHewan) {
        this.tanggalHewan = tanggalHewan;
    }

    public boolean isMemilikiHewan() {
        return memilikiHewan;
    }

    public void setMemilikiHewan(boolean memilikiHewan) {
        this.memilikiHewan = memilikiHewan;
    }

    public boolean isKeluargaAlergi() {
        return keluargaAlergi;
    }

    public void setKeluargaAlergi(boolean keluargaAlergi) {
        this.keluargaAlergi = keluargaAlergi;
    }

    public boolean isLingkunganAman() {
        return lingkunganAman;
    }

    public void setLingkunganAman(boolean lingkunganAman) {
        this.lingkunganAman = lingkunganAman;
    }

    public Pesanan getPesanan() {
        return pesanan;
    }

    public void setPesanan(Pesanan pesanan) {
        this.pesanan = pesanan;
    }
}