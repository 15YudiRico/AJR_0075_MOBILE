package com.yudiriconapitupulu.ajr.models;

public class Customer {
    long id;
    long id_customer;
    private String format_id;
    private String nama_customer;
    private String alamat_customer;
    private String email_customer;
    private String no_telepon_customer;
    private String pass_customer;

    public Customer(String nama_customer, String alamat_customer, String email_customer, String no_telepon_customer, String pass_customer) {
        this.nama_customer = nama_customer;
        this.alamat_customer = alamat_customer;
        this.email_customer = email_customer;
        this.no_telepon_customer = no_telepon_customer;
        this.pass_customer = pass_customer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public String getFormat_id() {
        return format_id;
    }

    public void setFormat_id(String format_id) {
        this.format_id = format_id;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat_customer() {
        return alamat_customer;
    }

    public void setAlamat_customer(String alamat_customer) {
        this.alamat_customer = alamat_customer;
    }

    public String getEmail_customer() {
        return email_customer;
    }

    public void setEmail_customer(String email_customer) {
        this.email_customer = email_customer;
    }

    public String getNo_telepon_customer() {
        return no_telepon_customer;
    }

    public void setNo_telepon_customer(String no_telepon_customer) {
        this.no_telepon_customer = no_telepon_customer;
    }

    public String getPass_customer() {
        return pass_customer;
    }

    public void setPass_customer(String pass_customer) {
        this.pass_customer = pass_customer;
    }
}
