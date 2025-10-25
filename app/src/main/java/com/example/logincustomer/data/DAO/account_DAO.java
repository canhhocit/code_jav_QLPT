package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.Account;
import com.example.logincustomer.data.Model.baocao_thuchi;

import java.util.ArrayList;
import java.util.List;

public class account_DAO {
    private SQLiteDatabase db;
    public account_DAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    public void insertAccount(Account account){
        ContentValues vlu = new ContentValues();
        vlu.put("username", account.getUsername());
        vlu.put("password",account.getPass());
        db.insert("TaiKhoan",null,vlu);
    }
    public void updateAccount(Account account){
        ContentValues vlu = new ContentValues();
        vlu.put("username", account.getUsername());
        vlu.put("password",account.getPass());
        db.update("TaiKhoan",vlu,"idTK =? or username =?",new String[]{String.valueOf(account.getId()), account.getUsername()});
    }
    public void deleteAcoount(int id){
        db.delete("TaiKhoan","idTK =?",new String[]{String.valueOf(id)});
    }

    public List<Account> getAllAccount(){
        List<Account> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from TaiKhoan",null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Account(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
