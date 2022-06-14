package com.yudiriconapitupulu.ajr.models;

public class Transaksi {
    private long id;
    private String format_id_transaksi;
    private long  id_transaksi;
    private String tgl_transaksi;
    private String status_transaksi;
    private String nama_customer;
    private String nama_driver;
    private String nama_pegawai;
    private String kode_promo;
    private String nama_mobil;

    public Transaksi(String format_id_transaksi, long id_transaksi, String tgl_transaksi, String status_transaksi, String nama_customer, String nama_driver, String nama_pegawai, String kode_promo, String nama_mobil) {
        this.format_id_transaksi = format_id_transaksi;
        this.id_transaksi = id_transaksi;
        this.tgl_transaksi = tgl_transaksi;
        this.status_transaksi = status_transaksi;
        this.nama_customer = nama_customer;
        this.nama_driver = nama_driver;
        this.nama_pegawai = nama_pegawai;
        this.kode_promo = kode_promo;
        this.nama_mobil = nama_mobil;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormat_id_transaksi() {
        return format_id_transaksi;
    }

    public void setFormat_id_transaksi(String format_id_transaksi) {
        this.format_id_transaksi = format_id_transaksi;
    }

    public long getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(long id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getKode_promo() {
        return kode_promo;
    }

    public void setKode_promo(String kode_promo) {
        this.kode_promo = kode_promo;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }
}
