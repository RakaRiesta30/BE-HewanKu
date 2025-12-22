// package com.TuBes.HewanKu.Ulasan;

// import java.io.Serializable;
// import java.time.LocalDate;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "ulasan")
// public class Ulasan implements Serializable {
//     @Id
//     @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ulasan_sequence")
//     @SequenceGenerator(name="ulasan_sequence", sequenceName="ulasan_sequence", allocationSize=100)
//     private Long id;
//     private String komen;
//     private LocalDate dateAdded;

//     public Ulasan(LocalDate dateAdded, String komen) {
//         this.dateAdded = dateAdded;
//         this.komen = komen;
//     }

//     public LocalDate getDateAdded() {
//         return dateAdded;
//     }

//     public void setDateAdded(LocalDate dateAdded) {
//         this.dateAdded = dateAdded;
//     }

//     public String getKomen() {
//         return komen;
//     }

//     public void setKomen(String komen) {
//         this.komen = komen;
//     }

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }
    
    
// }