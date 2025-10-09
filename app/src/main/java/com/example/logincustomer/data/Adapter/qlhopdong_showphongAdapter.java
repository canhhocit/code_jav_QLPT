package com.example.logincustomer.data.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.qlhopdong_hopdongDAO;
import com.example.logincustomer.data.Model.hopdong_ifRoom;

import java.util.List;

public class qlhopdong_showphongAdapter extends BaseAdapter {
    private Context context;
    private List<hopdong_ifRoom> listP;
    private qlhopdong_hopdongDAO hopdongDAO;

    public qlhopdong_showphongAdapter(Context context, qlhopdong_hopdongDAO hopdongDAO, List<hopdong_ifRoom> listP) {
        this.context = context;
        this.hopdongDAO = hopdongDAO;
        this.listP = listP;
    }

    @Override
    public int getCount() {
        return listP.size();
    }

    @Override
    public Object getItem(int position) {
        return listP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listP.get(position).getIdphong();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.qlhopdong_layout_item_showphong, parent, false);
        }

        hopdong_ifRoom room = listP.get(position);

        TextView tvTenPhong = convertView.findViewById(R.id.hopdong_itemphong_tvtenp);
        TextView tvSoNguoi = convertView.findViewById(R.id.hopdong_itemphong_tvsonguoi);
        TextView tvGia = convertView.findViewById(R.id.hopdong_itemphong_gia);

        tvTenPhong.setText(room.getTenphong());
        tvSoNguoi.setText(String.valueOf(room.getSonguoi()));
        if (room.getGia() > 0) {
            tvGia.setText(String.format("%,.0fđ", room.getGia()));
        } else {
            tvGia.setText("-");
        }

        return convertView;
    }
    public void updateData(List<hopdong_ifRoom> newList) {
        listP.clear();
        listP.addAll(newList);
        notifyDataSetChanged();
    }

}
