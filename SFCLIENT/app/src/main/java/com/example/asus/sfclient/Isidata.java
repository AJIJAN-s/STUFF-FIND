package com.example.asus.sfclient;

public class Isidata {

    private String idBarang;
    private String namaBarang;
    private String hargaBarang;
    private String statusBarang;
    private String lokasiBarang;

    public Isidata(){
        //this constructor is required
    }

    public Isidata(String idBarang, String namaBarang, String hargaBarang, String statusBarang, String lokasiBarang) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.statusBarang = statusBarang;
        this.lokasiBarang = lokasiBarang;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public String getStatusBarang() {
        return statusBarang;
    }

    public String getLokasiBarang() {
        return lokasiBarang;
    }

}
