package com.example.logincustomer.data.Model;

public class baocao_thuchi {
    private int idthuchi;
    private String tenthuchi;
    private double sotienthuchi;
    private String loaithuchi;
    private String ngaythuchi;

    public baocao_thuchi() {
    }

    public baocao_thuchi(int idthuchi, String tenthuchi, double sotienthuchi, String loaithuchi, String ngaythuchi) {
        this.idthuchi = idthuchi;
        this.tenthuchi = tenthuchi;
        this.sotienthuchi = sotienthuchi;
        this.loaithuchi = loaithuchi;
        this.ngaythuchi = ngaythuchi;
    }

    public baocao_thuchi(String tenthuchi, double sotienthuchi, String ngaythuchi, String loaithuchi) {
        this.tenthuchi = tenthuchi;
        this.sotienthuchi = sotienthuchi;
        this.ngaythuchi = ngaythuchi;
        this.loaithuchi = loaithuchi;
    }

    public int getIdthuchi() {
        return idthuchi;
    }

    public void setIdthuchi(int idthuchi) {
        this.idthuchi = idthuchi;
    }

    public String getTenthuchi() {
        return tenthuchi;
    }

    public void setTenthuchi(String tenthuchi) {
        this.tenthuchi = tenthuchi;
    }

    public double getSotienthuchi() {
        return sotienthuchi;
    }

    public void setSotienthuchi(int sotienthuchi) {
        this.sotienthuchi = sotienthuchi;
    }

    public String getLoaithuchi() {
        return loaithuchi;
    }

    public void setLoaithuchi(String loaithuchi) {
        this.loaithuchi = loaithuchi;
    }

    public String getNgaythuchi() {
        return ngaythuchi;
    }

    public void setNgaythuchi(String ngaythuchi) {
        this.ngaythuchi = ngaythuchi;
    }
}
