package com.example.logincustomer.Model;

public class PhongTro {
    private int idphong;
    private String tenphong;
    private int songuoi;
    private double gia;
    private int idhopdong;

    public PhongTro(){}
    public PhongTro(int idphong, String tenphong, int songuoi, double gia, int idhopdong) {
        this.idphong = idphong;
        this.tenphong = tenphong;
        this.songuoi = songuoi;
        this.gia = gia;
        this.idhopdong = idhopdong;
    }

    // Getter - Setter
    public int getIdphong() { return idphong; }
    public String getTenphong() { return tenphong; }
    public int getSonguoi() { return songuoi; }
    public double getGia() { return gia; }
    public int getIdhopdong() { return idhopdong; }

    public void setIdphong(int idphong){this.idphong = idphong;}
    public void setTenphong(String tenphong){this.tenphong = tenphong;}
    public void setSonguoi(int songuoi){this.songuoi = songuoi;}
    public void setGia(double gia){this.gia = gia;}
    public void setIdhopdong(int idhopdong){this.idhopdong = idhopdong;}
}
