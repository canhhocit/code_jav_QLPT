package com.example.logincustomer.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.baocao_thuchi;

public class baocao_thuchiDAO {
    private SQLiteDatabase db;

    public baocao_thuchiDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }
    //them thu chi
    private void insertthuchi(baocao_thuchi thuchi){
        ContentValues values = new ContentValues();

    }
}
