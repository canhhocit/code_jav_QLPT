package com.example.logincustomer.data.Model;

public class HoaDon {
    private int Idhoadon;
    private int Idphong;
    private String Ngaytaohdon;
    private boolean Trangthai = false; // false = chưa thanh toán, true = đã thanh toán
    private String Ghichu;
    private String Image;
    private double Tongtien;

    public HoaDon() {}

    // Constructor đầy đủ (bao gồm trạng thái)
    public HoaDon(int idhoadon, int idphong, String ngaytaohdon, boolean trangthai,
                  String ghichu, String image, double tongtien) {
        this.Idhoadon = idhoadon;
        this.Idphong = idphong;
        this.Ngaytaohdon = ngaytaohdon;
        this.Trangthai = trangthai;
        this.Ghichu = ghichu;
        this.Image = image;
        this.Tongtien = tongtien;
    }

    // Constructor không có trạng thái (mặc định là false)
    public HoaDon(int idhoadon, int idphong, String ngaytaohdon, String ghichu, String image, double tongtien) {
        this(idhoadon, idphong, ngaytaohdon, false, ghichu, image, tongtien);
    }

    // Getter - Setter
    public int getIdhoadon() { return Idhoadon; }
    public void setIdhoadon(int idhoadon) { this.Idhoadon = idhoadon; }

    public int getIdphong() { return Idphong; }
    public void setIdphong(int idphong) { this.Idphong = idphong; }

    public String getNgaytaohdon() { return Ngaytaohdon; }
    public void setNgaytaohdon(String ngaytaohdon) { this.Ngaytaohdon = ngaytaohdon; }

    public boolean isTrangthai() { return Trangthai; }
    public void setTrangthai(boolean trangthai) { this.Trangthai = trangthai; }

    public String getGhichu() { return Ghichu; }
    public void setGhichu(String ghichu) { this.Ghichu = ghichu; }

    public String getImage() { return Image; }
    public void setImage(String image) { this.Image = image; }

    public double getTongtien() { return Tongtien; }
    public void setTongtien(double tongtien) { this.Tongtien = tongtien; }
}
