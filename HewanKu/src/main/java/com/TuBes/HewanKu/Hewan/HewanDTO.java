package com.TuBes.HewanKu.Hewan;


public class HewanDTO {
    private String nama;
    private String jenis;
    private Long harga;
    private String status;
    private String kesehatan;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Long getHarga() {
        return harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKesehatan() {
        return kesehatan;
    }

    public void setKesehatan(String kesehatan) {
        this.kesehatan = kesehatan;
    }
}
