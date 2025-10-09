package com.example.logincustomer.data.Model;

public class hopdong_ifRoom {
    private int idphong;
    private String tenphong;
    private int songuoi;
    private double gia;

    public hopdong_ifRoom() {}

    public hopdong_ifRoom(int idphong, String tenphong, int songuoi, double gia) {
        this.idphong = idphong;
        this.tenphong = tenphong;
        this.songuoi = songuoi;
        this.gia = gia;
    }

    public hopdong_ifRoom(String tenphong, int songuoi, double gia) {
        this.tenphong = tenphong;
        this.songuoi = songuoi;
        this.gia = gia;
    }

    public int getIdphong() {
        return idphong;
    }

    public void setIdphong(int idphong) {
        this.idphong = idphong;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public int getSonguoi() {
        return songuoi;
    }

    public void setSonguoi(int songuoi) {
        this.songuoi = songuoi;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
