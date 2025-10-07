package com.example.logincustomer.data.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.Model.PhongTro;

import java.util.List;

public class baocao_phongAdapter extends BaseAdapter {
    private Context context;
    private List<PhongTro> listphongtro;
    private qlphongtro_PhongTroDAO ptDAO;

    public baocao_phongAdapter(Context context, List<PhongTro> listphongtro, qlphongtro_PhongTroDAO ptDAO) {
        this.context = context;
        this.listphongtro = listphongtro;
        this.ptDAO = ptDAO;
    }

    @Override
    public int getCount() {
        return listphongtro.size();
    }

    @Override
    public Object getItem(int position) {
        return listphongtro.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listphongtro.get(position).getIdphong();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.baocao_layout_itemds_phong,parent,false);
        }
        PhongTro pt = listphongtro.get(position);
        TextView tenphong = convertView.findViewById(R.id.baocao_phong_tvtenphong);
        TextView songuoi = convertView.findViewById(R.id.baocao_phong_tvsonguoi);
        TextView gia = convertView.findViewById(R.id.baocao_phong_tvgiatien);

        tenphong.setText(pt.getTenphong());
        songuoi.setText(String.valueOf(pt.getSonguoi()));
        gia.setText(String.valueOf(pt.getGia()));
        return convertView;
    }
}
