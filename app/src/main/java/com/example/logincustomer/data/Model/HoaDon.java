package com.example.logincustomer.data.Model;
public class HoaDon {
    private int idhoadon;
    private int idphong;
    private String ngaytaohdon;
    private boolean trangthai = false; // false = chưa
    private String ghichu;
    private double tongtien;
    private String imgDienCu;
    private String imgDienMoi;
    private String imgNuocCu;
    private String imgNuocMoi;
    public HoaDon() {}
    public HoaDon(int idhoadon, int idphong, String ngaytaohdon, boolean trangthai,
                  String ghichu, double tongtien,
                  String imgDienCu, String imgDienMoi, String imgNuocCu, String imgNuocMoi) {
        this.idhoadon = idhoadon;
        this.idphong = idphong;
        this.ngaytaohdon = ngaytaohdon;
        this.trangthai = trangthai;
        this.ghichu = ghichu;
        this.tongtien = tongtien;
        this.imgDienCu = imgDienCu;
        this.imgDienMoi = imgDienMoi;
        this.imgNuocCu = imgNuocCu;
        this.imgNuocMoi = imgNuocMoi;
    }
    // Constructor rút gọn khi tạo mới (trạng thái mặc định là chưa thanh toán)
    public HoaDon(int idphong, String ngaytaohdon, String ghichu, double tongtien,
                  String imgDienCu, String imgDienMoi, String imgNuocCu, String imgNuocMoi) {
        this.idphong = idphong;
        this.ngaytaohdon = ngaytaohdon;
        this.trangthai = false;
        this.ghichu = ghichu;
        this.tongtien = tongtien;
        this.imgDienCu = imgDienCu;
        this.imgDienMoi = imgDienMoi;
        this.imgNuocCu = imgNuocCu;
        this.imgNuocMoi = imgNuocMoi;
    }
    public int getIdhoadon() {
        return idhoadon;
    }
    public void setIdhoadon(int idhoadon) {
        this.idhoadon = idhoadon;
    }
    public int getIdphong() {
        return idphong;
    }
    public void setIdphong(int idphong) {
        this.idphong = idphong;
    }
    public String getNgaytaohdon() {
        return ngaytaohdon;
    }

    public void setNgaytaohdon(String ngaytaohdon) {
        this.ngaytaohdon = ngaytaohdon;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public String getImgDienCu() {
        return imgDienCu;
    }

    public void setImgDienCu(String imgDienCu) {
        this.imgDienCu = imgDienCu;
    }

    public String getImgDienMoi() {
        return imgDienMoi;
    }

    public void setImgDienMoi(String imgDienMoi) {
        this.imgDienMoi = imgDienMoi;
    }

    public String getImgNuocCu() {
        return imgNuocCu;
    }

    public void setImgNuocCu(String imgNuocCu) {
        this.imgNuocCu = imgNuocCu;
    }

    public String getImgNuocMoi() {
        return imgNuocMoi;
    }

    public void setImgNuocMoi(String imgNuocMoi) {
        this.imgNuocMoi = imgNuocMoi;
    }
}
