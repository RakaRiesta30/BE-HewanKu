package com.TuBes.HewanKu.Pesanan;

public class FormDTO {
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
    
    public FormDTO() {
    }

    public FormDTO(String nama, String email, String noTelepon, String tanggalLahir, String jenisKelamin, String daerah,
            String jalan, String zipCode, String pekerjaanStatus, String tempatTinggal, boolean hewanSebelumnya,
            String jenisHewan, String tanggalHewan, boolean memilikiHewan, boolean keluargaAlergi,
            boolean lingkunganAman) {
        this.nama = nama;
        this.email = email;
        this.noTelepon = noTelepon;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.daerah = daerah;
        this.jalan = jalan;
        this.zipCode = zipCode;
        this.pekerjaanStatus = pekerjaanStatus;
        this.tempatTinggal = tempatTinggal;
        this.hewanSebelumnya = hewanSebelumnya;
        this.jenisHewan = jenisHewan;
        this.tanggalHewan = tanggalHewan;
        this.memilikiHewan = memilikiHewan;
        this.keluargaAlergi = keluargaAlergi;
        this.lingkunganAman = lingkunganAman;
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
}
