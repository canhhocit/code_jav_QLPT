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
    public List<baocao_doanhthu> getAllMoney(){
        List<baocao_doanhthu> list = new ArrayList<>();
        // dd/MM/yyyy
        String sql = "SELECT \n" +
                "    SUBSTR(h.ngaytaohdon, 4, 2) AS thang,\n" +
                "    SUBSTR(h.ngaytaohdon, 7, 4) AS nam,\n" +
                "\n" +
                "    IFNULL(SUM(h.tongtien), 0) AS tonghoadon,\n" +
                "\n" +
                "    (\n" +
                "        SELECT IFNULL(SUM(sotien), 0)\n" +
                "        FROM ThuChi\n" +
                "        WHERE loai = 'thu'\n" +
                "          AND SUBSTR(ngaythuchi, 4, 2) = SUBSTR(h.ngaytaohdon, 4, 2)\n" +
                "          AND SUBSTR(ngaythuchi, 7, 4) = SUBSTR(h.ngaytaohdon, 7, 4)\n" +
                "    ) AS tongthu,\n" +
                "\n" +
                "    (\n" +
                "        SELECT IFNULL(SUM(sotien), 0)\n" +
                "        FROM ThuChi\n" +
                "        WHERE loai = 'chi'\n" +
                "          AND SUBSTR(ngaythuchi, 4, 2) = SUBSTR(h.ngaytaohdon, 4, 2)\n" +
                "          AND SUBSTR(ngaythuchi, 7, 4) = SUBSTR(h.ngaytaohdon, 7, 4)\n" +
                "    ) AS tongchi,\n" +
                "\n" +
                "    (\n" +
                "        IFNULL(SUM(h.tongtien), 0)\n" +
                "        + (\n" +
                "            SELECT IFNULL(SUM(sotien), 0)\n" +
                "            FROM ThuChi\n" +
                "            WHERE loai = 'thu'\n" +
                "              AND SUBSTR(ngaythuchi, 4, 2) = SUBSTR(h.ngaytaohdon, 4, 2)\n" +
                "              AND SUBSTR(ngaythuchi, 7, 4) = SUBSTR(h.ngaytaohdon, 7, 4)\n" +
                "        )\n" +
                "        - (\n" +
                "            SELECT IFNULL(SUM(sotien), 0)\n" +
                "            FROM ThuChi\n" +
                "            WHERE loai = 'chi'\n" +
                "              AND SUBSTR(ngaythuchi, 4, 2) = SUBSTR(h.ngaytaohdon, 4, 2)\n" +
                "              AND SUBSTR(ngaythuchi, 7, 4) = SUBSTR(h.ngaytaohdon, 7, 4)\n" +
                "        )\n" +
                "    ) AS loinhuan\n" +
                "\n" +
                "FROM HoaDon h\n" +
                "GROUP BY nam, thang\n" +
                "ORDER BY nam, thang;\n";

        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                list.add(new baocao_doanhthu(cursor.getInt(0),cursor.getInt(1),
                        cursor.getDouble(2),cursor.getDouble(3),
                        cursor.getDouble(4),cursor.getDouble(5)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public List<Integer> getYears(){
        List<Integer> years = new ArrayList<>();
        String sql="select SUBSTR(h.ngaytaohdon, 1, 4) AS nam\n" +
                "FROM HoaDon h\n" +
                "GROUP BY nam\n" +
                "ORDER BY nam desc";
        Cursor cursor= db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                years.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        return years;
    }
    public List<Integer> getMonths(){
        List<Integer> months = new ArrayList<>();
        String sql="select SUBSTR(h.ngaytaohdon, 4, 2) AS thang\n" +
                "FROM HoaDon h\n" +
                "GROUP BY  thang\n" +
                "ORDER BY thang asc";
        Cursor cursor= db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                months.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        return months;
    }
    public List<baocao_doanhthu> getMoneybyYear(String year){
        List<baocao_doanhthu> list = new ArrayList<>();
        // yyyy-MM-dd
        String sql =
                "SELECT " +
                        "  SUBSTR(h.ngaytaohdon, 6, 2) AS thang, " +
                        "  IFNULL(SUM(h.tongtien), 0) AS tonghoadon, " +

                        "  (SELECT IFNULL(SUM(sotien), 0) " +
                        "   FROM ThuChi " +
                        "   WHERE loai = 'Thu' " +
                        "     AND SUBSTR(ngaythuchi, 6, 2) = SUBSTR(h.ngaytaohdon, 6, 2) " +
                        "     AND SUBSTR(ngaythuchi, 1, 4) = SUBSTR(h.ngaytaohdon, 1, 4)) AS tongthu, " +

                        "  (SELECT IFNULL(SUM(sotien), 0) " +
                        "   FROM ThuChi " +
                        "   WHERE loai = 'Chi' " +
                        "     AND SUBSTR(ngaythuchi, 6, 2) = SUBSTR(h.ngaytaohdon, 6, 2) " +
                        "     AND SUBSTR(ngaythuchi, 1, 4) = SUBSTR(h.ngaytaohdon, 1, 4)) AS tongchi, " +

                        "  (IFNULL(SUM(h.tongtien), 0) " +
                        "   + (SELECT IFNULL(SUM(sotien), 0) " +
                        "        FROM ThuChi " +
                        "        WHERE loai = 'Thu' " +
                        "          AND SUBSTR(ngaythuchi, 6, 2) = SUBSTR(h.ngaytaohdon, 6, 2) " +
                        "          AND SUBSTR(ngaythuchi, 1, 4) = SUBSTR(h.ngaytaohdon, 1, 4)) " +
                        "   - (SELECT IFNULL(SUM(sotien), 0) " +
                        "        FROM ThuChi " +
                        "        WHERE loai = 'Chi' " +
                        "          AND SUBSTR(ngaythuchi, 6, 2) = SUBSTR(h.ngaytaohdon, 6, 2) " +
                        "          AND SUBSTR(ngaythuchi, 1, 4) = SUBSTR(h.ngaytaohdon, 1, 4)) " +
                        "  ) AS loinhuan " +

                        "FROM HoaDon h " +
                        "WHERE SUBSTR(h.ngaytaohdon, 1, 4) = ? " +
                        "GROUP BY thang " +
                        "ORDER BY CAST(thang AS INTEGER);";

        Cursor cursor = db.rawQuery(sql,new String[]{year});
        if(cursor.moveToFirst()){
            do{
                list.add(new baocao_doanhthu(cursor.getInt(0),
                        cursor.getDouble(1),cursor.getDouble(2),
                        cursor.getDouble(3),cursor.getDouble(4)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    //lay tong tienchi
    public List<baocao_doanhthu> getChibyYear(String year){
        List<baocao_doanhthu> list = new ArrayList<>();
        String sql= "SELECT SUBSTR(ngaythuchi, 6, 2) AS thang, SUM(sotien) AS tongchi\n" +
                "FROM ThuChi\n" +
                "WHERE loai = 'Chi' AND SUBSTR(ngaythuchi, 1, 4) = ? \n" +
                "GROUP BY thang  \n" +
                "ORDER BY CAST(thang AS INTEGER);  ";
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
