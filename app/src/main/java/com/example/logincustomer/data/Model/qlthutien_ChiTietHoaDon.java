package com.example.logincustomer.data.Model;

public class qlthutien_ChiTietHoaDon {
    private int idchitiethoadon;
    private int idhoadon;
    private String tendichvu;
    private int sodiencu;
    private int sodienmoi;
    private int sonuoccu;
    private int sonuocmoi;
    private int sosudung;
    private double thanhtien;

    public qlthutien_ChiTietHoaDon() {}

    // full constructor
    public qlthutien_ChiTietHoaDon(int idhoadon, String tendichvu, int sodiencu, int sodienmoi,
                                   int sonuoccu, int sonuocmoi, int sosudung, double thanhtien) {
        this.idhoadon = idhoadon;
        this.tendichvu = tendichvu;
        this.sodiencu = sodiencu;
        this.sodienmoi = sodienmoi;
        this.sonuoccu = sonuoccu;
        this.sonuocmoi = sonuocmoi;
        this.sosudung = sosudung;
        this.thanhtien = thanhtien;
    }

    // getters / setters
    public int getIdchitiethoadon() { return idchitiethoadon; }
    public void setIdchitiethoadon(int idchitiethoadon) { this.idchitiethoadon = idchitiethoadon; }

    public int getIdhoadon() { return idhoadon; }
    public void setIdhoadon(int idhoadon) { this.idhoadon = idhoadon; }

    public String getTendichvu() { return tendichvu; }
    public void setTendichvu(String tendichvu) { this.tendichvu = tendichvu; }

    public int getSodiencu() { return sodiencu; }
    public void setSodiencu(int sodiencu) { this.sodiencu = sodiencu; }

    public int getSodienmoi() { return sodienmoi; }
    public void setSodienmoi(int sodienmoi) { this.sodienmoi = sodienmoi; }

    public int getSonuoccu() { return sonuoccu; }
    public void setSonuoccu(int sonuoccu) { this.sonuoccu = sonuoccu; }

    public int getSonuocmoi() { return sonuocmoi; }
    public void setSonuocmoi(int sonuocmoi) { this.sonuocmoi = sonuocmoi; }

    public int getSosudung() { return sosudung; }
    public void setSosudung(int sosudung) { this.sosudung = sosudung; }

    public double getThanhtien() { return thanhtien; }
    public void setThanhtien(double thanhtien) { this.thanhtien = thanhtien; }
}
