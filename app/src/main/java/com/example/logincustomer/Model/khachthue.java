package com.example.logincustomer.Model;

public class khachthue {
    private int idkhach;
    private String hoten;
    private String ngaysinh;
    private String sdt;
    private String diachi;
    private int idphong;

    public khachthue() {
    }

    public khachthue(int idkhach, String hoten, String ngaysinh, String sdt, String diachi, int idphong) {
        this.idkhach = idkhach;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.diachi = diachi;
        this.idphong = idphong;
    }

    public khachthue(String hoten, String ngaysinh, String sdt, String diachi, int idphong) {
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.diachi = diachi;
        this.idphong = idphong;
    }

    public int getIdkhach() {
        return idkhach;
    }

    public void setIdkhach(int idkhach) {
        this.idkhach = idkhach;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getIdphong() {
        return idphong;
    }

    public void setIdphong(int idphong) {
        this.idphong = idphong;
    }

}
