package com.example.logincustomer.data.Model;

public class HoaDon {
    private int Idhoadon;
    private int Idphong;
    private String Ngaytaohdon;
    private String Ghichu;
    private String Image;
    private double Tongtien;

    public HoaDon() {}

    public HoaDon(int idhoadon, int idphong, String ngaytaohdon, String ghichu, String image, double tongtien) {
        Idhoadon = idhoadon;
        Idphong = idphong;
        Ngaytaohdon = ngaytaohdon;
        Ghichu = ghichu;
        Image = image;
        Tongtien = tongtien;
    }

    // getter - setter
    public int getIdhoadon() { return Idhoadon; }
    public void setIdhoadon(int idhoadon) { Idhoadon = idhoadon; }

    public int getIdphong() { return Idphong; }
    public void setIdphong(int idphong) { Idphong = idphong; }

    public String getNgaytaohdon() { return Ngaytaohdon; }
    public void setNgaytaohdon(String ngaytaohdon) { Ngaytaohdon = ngaytaohdon; }

    public String getGhichu() { return Ghichu; }
    public void setGhichu(String ghichu) { Ghichu = ghichu; }

    public String getImage() { return Image; }
    public void setImage(String image) { Image = image; }

    public double getTongtien() { return Tongtien; }
    public void setTongtien(double tongtien) { Tongtien = tongtien; }
}
