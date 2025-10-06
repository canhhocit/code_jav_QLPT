package com.example.logincustomer.data.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.baocao_thuchiDAO;
import com.example.logincustomer.data.Model.baocao_thuchi;

import java.util.List;

public class baocao_thuchiAdapter extends BaseAdapter {
    private Context context;
    private List<baocao_thuchi> list;
    private baocao_thuchiDAO thuchiDAO;
    public baocao_thuchiAdapter(Context context, List<baocao_thuchi> list, baocao_thuchiDAO thuchiDAO) {
        this.context = context;
        this.list = list;
        this.thuchiDAO = thuchiDAO;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getIdthuchi();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater =LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.baocao_layout_itemds_thuchi,parent,false);
        }
        baocao_thuchi thuchi = list.get(position);
        TextView ten= convertView.findViewById(R.id.baocao_tv_tenthuchi);
        TextView tien = convertView.findViewById(R.id.baocao_tv_sotien);
        TextView loai = convertView.findViewById(R.id.baocao_tv_loaithuchi);
        TextView ngay = convertView.findViewById(R.id.baocao_tv_ngaythuchi);

        ten.setText(thuchi.getTenthuchi()); tien.setText(String.valueOf(thuchi.getSotienthuchi()));
        loai.setText(thuchi.getLoaithuchi()); ngay.setText(thuchi.getNgaythuchi());
        return convertView;
    }
}
