package com.TuBes.HewanKu.Shelter;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "shelter")
public class Shelter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shelter_sequence")
    @SequenceGenerator(name = "shelter_sequence", sequenceName = "shelter_sequence", allocationSize = 100)
    private Long id;
    private String nama;
    private String noTelepon;
    private String email;
    private String password;

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

}