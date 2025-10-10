package com.example.logincustomer.data.Model;

public class baocao_hopdong {
    private int idp;
    private int idhopdong;
    private int hanconlai;

    public baocao_hopdong() {
    }

    public baocao_hopdong(int idp, int idhopdong, int hanconlai) {
        this.idp = idp;
        this.idhopdong = idhopdong;
        this.hanconlai = hanconlai;
    }

    public baocao_hopdong(int idp, int idhopdong) {
        this.idp = idp;
        this.idhopdong = idhopdong;
    }

    public int getHanconlai() {
        return hanconlai;
    }

    public void setHanconlai(int hanconlai) {
        this.hanconlai = hanconlai;
    }

    public int getIdhopdong() {
        return idhopdong;
    }

    public void setIdhopdong(int idhopdong) {
        this.idhopdong = idhopdong;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }
}
