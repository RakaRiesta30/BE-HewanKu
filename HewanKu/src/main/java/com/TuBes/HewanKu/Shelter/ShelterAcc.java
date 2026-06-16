package com.TuBes.HewanKu.Shelter;

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
@Table(name = "shelteracc")
public class ShelterAcc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namaShelter;
    private String deskripsi;
    private String nomorRekening;
    private String namaPemilikRekening;
    private String metodePembayaran;
    private String alamatLengkap;
    private String urlLogo;

    @OneToOne
    @JoinColumn(name = "shelter_id", unique = true)
    @JsonIgnore
    private Shelter shelter;

    public ShelterAcc() {
    }

    public ShelterAcc(String alamatLengkap, String deskripsi, String metodePembayaran, String namaPemilikRekening,
            String namaShelter, String nomorRekening, String urlLogo, Shelter shelter) {
        this.alamatLengkap = alamatLengkap;
        this.deskripsi = deskripsi;
        this.metodePembayaran = metodePembayaran;
        this.namaPemilikRekening = namaPemilikRekening;
        this.namaShelter = namaShelter;
        this.nomorRekening = nomorRekening;
        this.urlLogo = urlLogo;
        this.shelter = shelter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaShelter() {
        return namaShelter;
    }

    public void setNamaShelter(String namaShelter) {
        this.namaShelter = namaShelter;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public String getNamaPemilikRekening() {
        return namaPemilikRekening;
    }

    public void setNamaPemilikRekening(String namaPemilikRekening) {
        this.namaPemilikRekening = namaPemilikRekening;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getNomorRekening() {
        return nomorRekening;
    }

    public void setNomorRekening(String nomorRekening) {
        this.nomorRekening = nomorRekening;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

}