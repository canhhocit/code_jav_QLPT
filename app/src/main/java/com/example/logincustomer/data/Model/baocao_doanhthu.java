package com.example.logincustomer.data.Model;

public class baocao_doanhthu {
    private int thang;
    private int nam;
    private double tongtienHD;
    private double tongthu;
    private double tongchi;
    private double loinhuan;

    public baocao_doanhthu() {
    }

    public baocao_doanhthu(int thang, int nam, double tongtienHD, double tongthu, double tongchi, double loinhuan) {
        this.thang = thang;
        this.nam = nam;
        this.tongtienHD = tongtienHD;
        this.tongthu = tongthu;
        this.tongchi = tongchi;
        this.loinhuan = loinhuan;
    }


    public baocao_doanhthu(int thang, double tongtienHD, double tongthu, double tongchi, double loinhuan) {
        this.thang = thang;
        this.tongtienHD = tongtienHD;
        this.tongthu = tongthu;
        this.tongchi = tongchi;
        this.loinhuan = loinhuan;
    }

    public baocao_doanhthu(int thang, double tongchi) {
        this.thang = thang;
        this.tongchi = tongchi;
    }

    public baocao_doanhthu(double tongthu, int thang) {
        this.tongthu = tongthu;
        this.thang = thang;
    }

    public double getLoinhuan() {
        return loinhuan;
    }

    public void setLoinhuan(double loinhuan) {
        this.loinhuan = loinhuan;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public double getTongchi() {
        return tongchi;
    }

    public void setTongchi(double tongchi) {
        this.tongchi = tongchi;
    }

    public double getTongthu() {
        return tongthu;
    }

    public void setTongthu(double tongthu) {
        this.tongthu = tongthu;
    }

    public double getTongtienHD() {
        return tongtienHD;
    }

    public void setTongtienHD(double tongtienHD) {
        this.tongtienHD = tongtienHD;
    }
}
