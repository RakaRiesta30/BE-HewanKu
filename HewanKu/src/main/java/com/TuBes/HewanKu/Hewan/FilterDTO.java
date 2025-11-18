package com.TuBes.HewanKu.Hewan;

public class FilterDTO {
    private String jenis;
    private Double hargaMin;
    private Double hargaMax;

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Double getHargaMin() {
        return hargaMin;
    }

    public void setHargaMin(Double hargaMin) {
        this.hargaMin = hargaMin;
    }

    public Double getHargaMax() {
        return hargaMax;
    }

    public void setHargaMax(Double hargaMax) {
        this.hargaMax = hargaMax;
    }
}
