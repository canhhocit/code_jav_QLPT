package com.example.logincustomer.data.Model;

public class PhongTro {
    private int idphong;
    private String tenphong;
    private int songuoi;
    private double gia;
    private String trangthai;
    private int idhopdong;

    public PhongTro(int idphong, String tenphong, int songuoi, double gia, String trangthai, int idhopdong) {
        this.idphong = idphong;
        this.tenphong = tenphong;
        this.songuoi = songuoi;
        this.gia = gia;
        this.trangthai = trangthai;
        this.idhopdong = idhopdong;
    }

    // Getter - Setter
    public int getIdphong() { return idphong; }
    public String getTenphong() { return tenphong; }
    public int getSonguoi() { return songuoi; }
    public double getGia() { return gia; }
    public String getTrangthai() { return trangthai; }
    public int getIdhopdong() { return idhopdong; }
}
