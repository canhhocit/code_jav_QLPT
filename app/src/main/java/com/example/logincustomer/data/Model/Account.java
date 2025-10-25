package com.example.logincustomer.data.Model;

public class Account {
    private int id;
    private String username;
    private String pass;

    public Account(int id, String username, String pass) {
        this.id = id;
        this.username = username;
        this.pass = pass;
    }

    public Account() {
    }

    public Account(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
