package com.example.logincustomer.DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.logincustomer.Model.khachthue;
import com.example.logincustomer.R;

import java.util.List;

public class khachthueAdapter extends BaseAdapter {
    private Context context;
    private List<khachthue> list;

    public khachthueAdapter(Context context, List<khachthue> list) {
        this.context = context;
        this.list = list;
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
        return list.get(position).getIdkhach();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.qlkhachthue_itemlistview,parent,false);
        }
        khachthue khach = list.get(position);

        TextView tvHoTen = convertView.findViewById(R.id.khachthue_tv_hotenlv);
        TextView tvGioiTinh = convertView.findViewById(R.id.khachthue_tv_gioitinglv);
        TextView tvNgaySinh = convertView.findViewById(R.id.khachthue_tv_ngaysinhlv);
        TextView tvPhong = convertView.findViewById(R.id.khachthue_tv_phonglv);
        tvHoTen.setText(khach.getHoten());
        tvGioiTinh.setText(khach.getGioitinh());
        tvNgaySinh.setText(khach.getNgaysinh());
//        tvPhong.setText(khach.);
        return convertView;
    }
}
