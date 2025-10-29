// package com.TuBes.HewanKu.Akun;

// import java.util.UUID;

// import jakarta.persistence.*;

// @Entity
// public class Akun {
//     @Id
//     @GeneratedValue
//     private UUID id;
//     private String nama;
//     private String noTelepon;
//     private String email;
//     private String role;
//     private String password;

//     public Akun() {
//     }

//     public Akun(String email, String nama, String password, String noTelepon, String role) {
//         this.email = email;
//         this.nama = nama;
//         this.password = password;
//         this.noTelepon = noTelepon;
//         this.role = role;
//     }

//     public UUID getId() {
//         return id;
//     }

//     public void setId(UUID id) {
//         this.id = id;
//     }

//     public String getNama() {
//         return nama;
//     }

//     public void setNama(String nama) {
//         this.nama = nama;
//     }

//     public String getNoTelepon() {
//         return noTelepon;
//     }

//     public void setNoTelepon(String noTelepon) {
//         this.noTelepon = noTelepon;
//     }

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getRole() {
//         return role;
//     }

//     public void setRole(String role) {
//         this.role = role;
//     }

//     public String getPassword() {
//         return password;
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }

// }
