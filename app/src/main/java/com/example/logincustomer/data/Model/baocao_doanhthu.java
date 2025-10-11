package com.example.logincustomer.data.Model;

public class baocao_doanhthu {
    private int thang;
    private double tongtienHD;
    private double tongthu;
    private double tongchi;

    public baocao_doanhthu() {
    }

    public baocao_doanhthu(int thang, double tongthu, boolean isThu) {
        this.thang = thang;
        if(isThu){
            this.tongthu = tongthu;
        } else {
            this.tongchi = tongthu;
        }
    }

    public baocao_doanhthu(int thang, double tongtienHD) {
        this.thang = thang;
        this.tongtienHD = tongtienHD;
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
