package com.TuBes.HewanKu.Shelter;

public class ShelterAccDTO {
    private String namaShelter;
    private String namaOwner;
    private String nomorHandphone;
    private String email;
    private String metodePembayaran;
    private String negaraDaerah;
    private String jalan;
    private String zipCode;

    public ShelterAccDTO() {
    }

    public ShelterAccDTO(String email, String jalan, String metodePembayaran, String namaOwner, String namaShelter, String negaraDaerah, String nomorHandphone, String zipCode) {
        this.email = email;
        this.jalan = jalan;
        this.metodePembayaran = metodePembayaran;
        this.namaOwner = namaOwner;
        this.namaShelter = namaShelter;
        this.negaraDaerah = negaraDaerah;
        this.nomorHandphone = nomorHandphone;
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNamaShelter() {
        return namaShelter;
    }

    public void setNamaShelter(String namaShelter) {
        this.namaShelter = namaShelter;
    }

    public String getNamaOwner() {
        return namaOwner;
    }

    public void setNamaOwner(String namaOwner) {
        this.namaOwner = namaOwner;
    }

    public String getNomorHandphone() {
        return nomorHandphone;
    }

    public void setNomorHandphone(String nomorHandphone) {
        this.nomorHandphone = nomorHandphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public String getNegaraDaerah() {
        return negaraDaerah;
    }

    public void setNegaraDaerah(String negaraDaerah) {
        this.negaraDaerah = negaraDaerah;
    }

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }
}
