package com.TuBes.HewanKu.Ulasan;

public class UlasanDTO {
    private String komen;
    private Double rating;

    public UlasanDTO(String komen, Double rating) {
        this.komen = komen;
        this.rating = rating;
    }

    public UlasanDTO() {
    }

    public String getKomen() {
        return komen;
    }

    public void setKomen(String komen) {
        this.komen = komen;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
