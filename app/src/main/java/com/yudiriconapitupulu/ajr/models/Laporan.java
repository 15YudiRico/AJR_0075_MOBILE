package com.yudiriconapitupulu.ajr.models;

public class Laporan {
    private String nama_mobil;
    private String tipe_mobil;
    private float harga_sewa_perhari;
    private int JUMLAH_PEMINJAMAN;
    private int TOTAL_DURASI;
    private float PENDAPATAN;
    private String nama_customer;
    private int jenis_transaksi;
    private int JUMLAH_TRANSAKSI;
    private float TOTAL_PENDAPATAN_DARI_CUSTOMER;
    private String format_id_driver;
    private long id_driver;
    private String nama_driver;
    private float RERATA_RATING_DRIVER;

    public Laporan(String nama_mobil, String tipe_mobil, float harga_sewa_perhari, int JUMLAH_PEMINJAMAN, int TOTAL_DURASI, float PENDAPATAN, String nama_customer, int jenis_transaksi, int JUMLAH_TRANSAKSI, float TOTAL_PENDAPATAN_DARI_CUSTOMER, String format_id_driver, long id_driver, String nama_driver, float RERATA_RATING_DRIVER) {
        this.nama_mobil = nama_mobil;
        this.tipe_mobil = tipe_mobil;
        this.harga_sewa_perhari = harga_sewa_perhari;
        this.JUMLAH_PEMINJAMAN = JUMLAH_PEMINJAMAN;
        this.TOTAL_DURASI = TOTAL_DURASI;
        this.PENDAPATAN = PENDAPATAN;
        this.nama_customer = nama_customer;
        this.jenis_transaksi = jenis_transaksi;
        this.JUMLAH_TRANSAKSI = JUMLAH_TRANSAKSI;
        this.TOTAL_PENDAPATAN_DARI_CUSTOMER = TOTAL_PENDAPATAN_DARI_CUSTOMER;
        this.format_id_driver = format_id_driver;
        this.id_driver = id_driver;
        this.nama_driver = nama_driver;
        this.RERATA_RATING_DRIVER = RERATA_RATING_DRIVER;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getTipe_mobil() {
        return tipe_mobil;
    }

    public void setTipe_mobil(String tipe_mobil) {
        this.tipe_mobil = tipe_mobil;
    }

    public float getHarga_sewa_perhari() {
        return harga_sewa_perhari;
    }

    public void setHarga_sewa_perhari(float harga_sewa_perhari) {
        this.harga_sewa_perhari = harga_sewa_perhari;
    }

    public int getJUMLAH_PEMINJAMAN() {
        return JUMLAH_PEMINJAMAN;
    }

    public void setJUMLAH_PEMINJAMAN(int JUMLAH_PEMINJAMAN) {
        this.JUMLAH_PEMINJAMAN = JUMLAH_PEMINJAMAN;
    }

    public int getTOTAL_DURASI() {
        return TOTAL_DURASI;
    }

    public void setTOTAL_DURASI(int TOTAL_DURASI) {
        this.TOTAL_DURASI = TOTAL_DURASI;
    }

    public float getPENDAPATAN() {
        return PENDAPATAN;
    }

    public void setPENDAPATAN(float PENDAPATAN) {
        this.PENDAPATAN = PENDAPATAN;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public int getJenis_transaksi() {
        return jenis_transaksi;
    }

    public void setJenis_transaksi(int jenis_transaksi) {
        this.jenis_transaksi = jenis_transaksi;
    }

    public int getJUMLAH_TRANSAKSI() {
        return JUMLAH_TRANSAKSI;
    }

    public void setJUMLAH_TRANSAKSI(int JUMLAH_TRANSAKSI) {
        this.JUMLAH_TRANSAKSI = JUMLAH_TRANSAKSI;
    }

    public float getTOTAL_PENDAPATAN_DARI_CUSTOMER() {
        return TOTAL_PENDAPATAN_DARI_CUSTOMER;
    }

    public void setTOTAL_PENDAPATAN_DARI_CUSTOMER(float TOTAL_PENDAPATAN_DARI_CUSTOMER) {
        this.TOTAL_PENDAPATAN_DARI_CUSTOMER = TOTAL_PENDAPATAN_DARI_CUSTOMER;
    }

    public String getFormat_id_driver() {
        return format_id_driver;
    }

    public void setFormat_id_driver(String format_id_driver) {
        this.format_id_driver = format_id_driver;
    }

    public long getId_driver() {
        return id_driver;
    }

    public void setId_driver(long id_driver) {
        this.id_driver = id_driver;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public float getRERATA_RATING_DRIVER() {
        return RERATA_RATING_DRIVER;
    }

    public void setRERATA_RATING_DRIVER(float RERATA_RATING_DRIVER) {
        this.RERATA_RATING_DRIVER = RERATA_RATING_DRIVER;
    }
}
