package com.TuBes.HewanKu.Ulasan;

import java.io.Serializable;
import java.time.LocalDate;

import com.TuBes.HewanKu.Hewan.Hewan;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ulasan")
public class Ulasan implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ulasan_sequence")
    @SequenceGenerator(name="ulasan_sequence", sequenceName="ulasan_sequence", allocationSize=100)
    private Long id;
    private String komen;
    private Double rating;
    private LocalDate dateAdded;

    @ManyToOne
    @JoinColumn(name = "hewan_id")
    @JsonIgnore
    private Hewan hewan;

    public Ulasan(LocalDate dateAdded, Hewan hewan, String komen, Double rating) {
        this.dateAdded = dateAdded;
        this.hewan = hewan;
        this.komen = komen;
        this.rating = rating;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getKomen() {
        return komen;
    }

    public void setKomen(String komen) {
        this.komen = komen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hewan getHewan() {
        return hewan;
    }

    public void setHewan(Hewan hewan) {
        this.hewan = hewan;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    
}