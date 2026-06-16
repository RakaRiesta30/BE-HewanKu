package com.TuBes.HewanKu.Shelter;

public class ShelterAccDTO {
    private String namaShelter;
    private String deskripsi;
    private String nomorRekening;
    private String namaPemilikRekening;
    private String metodePembayaran;
    private String alamatLengkap;
    private String urlLogo;

    public ShelterAccDTO() {
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNomorRekening() {
        return nomorRekening;
    }

    public void setNomorRekening(String nomorRekening) {
        this.nomorRekening = nomorRekening;
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

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }
}
