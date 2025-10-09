package com.example.logincustomer.data.Model;

public class hopdong {
    private int idhopdong;
    private String cccd;
    private String ngayky;
    private int idphong;
    private int idkhach;

    public hopdong() {
    }

    public hopdong(String cccd, String ngayky, int idphong, int idkhach) {
        this.cccd = cccd;
        this.ngayky = ngayky;
        this.idphong = idphong;
        this.idkhach = idkhach;
    }

    public hopdong(int idhopdong, String cccd, String ngayky) {
        this.idhopdong = idhopdong;
        this.cccd = cccd;
        this.ngayky = ngayky;
    }

    public hopdong(int idhopdong, String cccd, String ngayky, int idphong, int idkhach) {
        this.idhopdong = idhopdong;
        this.cccd = cccd;
        this.ngayky = ngayky;
        this.idphong = idphong;
        this.idkhach = idkhach;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public int getIdhopdong() {
        return idhopdong;
    }

    public void setIdhopdong(int idhopdong) {
        this.idhopdong = idhopdong;
    }

    public int getIdkhach() {
        return idkhach;
    }

    public void setIdkhach(int idkhach) {
        this.idkhach = idkhach;
    }

    public int getIdphong() {
        return idphong;
    }

    public void setIdphong(int idphong) {
        this.idphong = idphong;
    }

    public String getNgayky() {
        return ngayky;
    }

    public void setNgayky(String ngayky) {
        this.ngayky = ngayky;
    }
}
