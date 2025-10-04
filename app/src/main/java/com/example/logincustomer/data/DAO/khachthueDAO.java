package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.khachthue;

import java.util.ArrayList;
import java.util.List;

public class khachthueDAO {
    private SQLiteDatabase db;
    public khachthueDAO(Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    //them khach
    public void insertKhachThue(khachthue kt){
        ContentValues vlu = new ContentValues();
        vlu.put("hoten",kt.getHoten());
        vlu.put("ngaysinh",kt.getNgaysinh());
        vlu.put("sdt",kt.getSdt());
        vlu.put("diachi",kt.getDiachi());
        vlu.put("idphong", kt.getIdphong());
        db.insert("KhachThue", null, vlu);
    }
    // update
    public void updateKhachThue(khachthue kt) {
        ContentValues values = new ContentValues();
        values.put("hoten", kt.getHoten());
        values.put("ngaysinh", kt.getNgaysinh());
        values.put("sdt", kt.getSdt());
        values.put("diachi", kt.getDiachi());
        values.put("idphong", kt.getIdphong());
        db.update("KhachThue", values, "idkhach=?", new String[]{String.valueOf(kt.getIdkhach())});
    }
    //delete
    public void deleteKhacThue(int idkhach) {
        db.delete("KhachThue", "idkhach=?", new String[]{String.valueOf(idkhach)});
    }

    //lay toan bo ds khach
    public List<khachthue> getAllKhachThue(){
        List<khachthue> list = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from KhachThue",null);
        if(cur.getCount()>0) cur.moveToFirst();
        do{
            list.add(new khachthue(cur.getInt(0),
                    cur.getString(1),
                    cur.getString(2),
                    cur.getString(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getInt(6)));
        }while(cur.moveToNext());
        cur.close();
        return list;
    }

    //find
    public List<khachthue> findbyname(String name){
        List<khachthue> list = new ArrayList<>();
        Cursor cur = db.rawQuery("select * from KhachThue where hoten like ?",
                                    new String[]{"%"+ name+"%"});
        if(cur.getCount()>0) cur.moveToFirst();
        do{
            list.add(new khachthue(cur.getInt(0),
                    cur.getString(1),
                    cur.getString(2),
                    cur.getString(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getInt(6)));
        }while(cur.moveToNext());
        return list;
    }
}
