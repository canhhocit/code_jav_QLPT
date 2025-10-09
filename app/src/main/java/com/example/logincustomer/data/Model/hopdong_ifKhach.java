package com.example.logincustomer.data.Model;

public class hopdong_ifKhach {
    private String tenkhach;
    private String sdt;
    private String ngaysinh;

    public hopdong_ifKhach() {
    }

    public hopdong_ifKhach(String tenkhach, String ngaysinh, String sdt) {
        this.tenkhach = tenkhach;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
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

    public String getTenkhach() {
        return tenkhach;
    }

    public void setTenkhach(String tenkhach) {
        this.tenkhach = tenkhach;
    }
}
