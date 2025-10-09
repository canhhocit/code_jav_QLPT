package com.example.logincustomer.data.Model;

public class hopdong_display {
    private String tenphong;
    private String hoten;
    private String ngaykyhopdong;

    public hopdong_display(String tenphong, String hoten, String ngaykyhopdong) {
        this.tenphong = tenphong;
        this.hoten = hoten;
        this.ngaykyhopdong = ngaykyhopdong;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaykyhopdong() {
        return ngaykyhopdong;
    }

    public void setNgaykyhopdong(String ngaykyhopdong) {
        this.ngaykyhopdong = ngaykyhopdong;
    }
}
