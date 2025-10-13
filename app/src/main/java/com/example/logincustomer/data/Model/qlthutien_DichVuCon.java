package com.example.logincustomer.data.Model;

public class qlthutien_DichVuCon {
    private int iddichvucon;
    private int idchitiethoadon;
    private String tendichvu;
    private double giatien;

    public qlthutien_DichVuCon(int iddichvucon, int idchitiethoadon, String tendichvu, double giatien) {
        this.iddichvucon = iddichvucon;
        this.idchitiethoadon = idchitiethoadon;
        this.tendichvu = tendichvu;
        this.giatien = giatien;
    }

    public qlthutien_DichVuCon(String tendichvu, double giatien) {
        this.tendichvu = tendichvu;
        this.giatien = giatien;
    }

    public int getIddichvucon() { return iddichvucon; }
    public void setIddichvucon(int iddichvucon) { this.iddichvucon = iddichvucon; }

    public int getIdchitiethoadon() { return idchitiethoadon; }
    public void setIdchitiethoadon(int idchitiethoadon) { this.idchitiethoadon = idchitiethoadon; }

    public String getTendichvu() { return tendichvu; }
    public void setTendichvu(String tendichvu) { this.tendichvu = tendichvu; }

    public double getGiatien() { return giatien; }
    public void setGiatien(double giatien) { this.giatien = giatien; }
}