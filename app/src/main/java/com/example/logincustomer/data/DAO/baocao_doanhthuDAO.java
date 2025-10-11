package com.example.logincustomer.data.DAO;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.baocao_doanhthu;

import java.util.ArrayList;
import java.util.List;

public class baocao_doanhthuDAO  {
    private SQLiteDatabase db;

    public baocao_doanhthuDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getReadableDatabase();
    }

    public List<Integer> getYears(){
        List<Integer> years = new ArrayList<>();
        String sql="SELECT nam FROM (\n" +
                "    SELECT SUBSTR(ngaytaohdon,1,4) AS nam FROM HoaDon\n" +
                "    UNION\n" +
                "    SELECT SUBSTR(ngaythuchi,1,4) AS nam FROM ThuChi\n" +
                ") \n" +
                "GROUP BY nam\n" +
                "ORDER BY nam DESC;\n";
        Cursor cursor= db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                years.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return years;
    }


    public List<baocao_doanhthu> getChibyYear(String year){
        List<baocao_doanhthu> list = new ArrayList<>();
        String sql= "SELECT SUBSTR(ngaythuchi, 6, 2) AS thang, SUM(sotien) AS tongchi\n" +
                "FROM ThuChi\n" +
                "WHERE loai = 'Chi' AND SUBSTR(ngaythuchi, 1, 4) = ? \n" +
                "GROUP BY thang  \n" +
                "ORDER BY CAST(thang AS INTEGER);";
        Cursor cursor = db.rawQuery(sql,new String[]{year});
        if(cursor.moveToFirst()){
            do {
                list.add(new baocao_doanhthu(cursor.getInt(0), cursor.getDouble(1), false));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<baocao_doanhthu> getThubyYear(String year){
        List<baocao_doanhthu> list = new ArrayList<>();
        String sql= "SELECT SUBSTR(ngaythuchi, 6, 2) AS thang, SUM(sotien) AS tongthu\n" +
                "FROM ThuChi\n" +
                "WHERE loai = 'Thu' AND SUBSTR(ngaythuchi, 1, 4) = ? \n" +
                "GROUP BY thang  \n" +
                "ORDER BY CAST(thang AS INTEGER);";
        Cursor cursor = db.rawQuery(sql,new String[]{year});
        if(cursor.moveToFirst()){
            do {
                list.add(new baocao_doanhthu(cursor.getInt(0), cursor.getDouble(1), true));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public List<baocao_doanhthu> getdtHDbyYear(String year){
        List<baocao_doanhthu> list = new ArrayList<>();
        String sql= "SELECT SUBSTR(ngaytaohdon, 6, 2) AS thang, SUM(tongtien) AS tienHD\n" +
                "FROM HoaDon\n" +
                "WHERE SUBSTR(ngaytaohdon,1,4)=?\n" +
                "GROUP BY thang\n" +
                "ORDER BY thang;";
        Cursor cursor = db.rawQuery(sql,new String[]{year});
        if(cursor.moveToFirst()){
            do {
                list.add(new baocao_doanhthu(cursor.getInt(0), cursor.getDouble(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


}
