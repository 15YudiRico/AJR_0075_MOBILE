package com.yudiriconapitupulu.ajr.models;

public class Driver {

    long id;
    long id_driver;
    private String format_id_driver;
    private String nama_driver;
    private String alamat_driver;
    private String email_driver;
    private String no_telp_driver;
    private float rerata_rating_driver;
    private int status_ketersediaan_driver;
    private String pass_driver;

    public Driver(String nama_driver, String alamat_driver, String email_driver, String no_telp_driver, int status_ketersediaan_driver, String pass_driver) {
        this.nama_driver = nama_driver;
        this.alamat_driver = alamat_driver;
        this.email_driver = email_driver;
        this.no_telp_driver = no_telp_driver;
        this.status_ketersediaan_driver = status_ketersediaan_driver;
        this.pass_driver = pass_driver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_driver() {
        return id_driver;
    }

    public void setId_driver(long id_driver) {
        this.id_driver = id_driver;
    }

    public String getFormat_id_driver() {
        return format_id_driver;
    }

    public void setFormat_id_driver(String format_id_driver) {
        this.format_id_driver = format_id_driver;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getAlamat_driver() {
        return alamat_driver;
    }

    public void setAlamat_driver(String alamat_driver) {
        this.alamat_driver = alamat_driver;
    }

    public String getEmail_driver() {
        return email_driver;
    }

    public void setEmail_driver(String email_driver) {
        this.email_driver = email_driver;
    }

    public String getNo_telp_driver() {
        return no_telp_driver;
    }

    public void setNo_telp_driver(String no_telp_driver) {
        this.no_telp_driver = no_telp_driver;
    }

    public float getRerata_rating_driver() {
        return rerata_rating_driver;
    }

    public void setRerata_rating_driver(float rerata_rating_driver) {
        this.rerata_rating_driver = rerata_rating_driver;
    }

    public int getStatus_ketersediaan_driver() {
        return status_ketersediaan_driver;
    }

    public void setStatus_ketersediaan_driver(int status_ketersediaan_driver) {
        this.status_ketersediaan_driver = status_ketersediaan_driver;
    }

    public String getPass_driver() {
        return pass_driver;
    }

    public void setPass_driver(String pass_driver) {
        this.pass_driver = pass_driver;
    }
}
