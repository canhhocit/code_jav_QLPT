package com.example.logincustomer.data.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.baocao_hopdongDAO;
import com.example.logincustomer.data.Model.baocao_hopdong;

import java.util.List;

public class baocao_hopdongAdapter extends BaseAdapter {
    private Context context;
    private List<baocao_hopdong> list;
    private baocao_hopdongDAO bchd_DAO;

    public baocao_hopdongAdapter(Context context, List<baocao_hopdong> list, baocao_hopdongDAO bchd_DAO) {
        this.context = context;
        this.list = list;
        this.bchd_DAO = bchd_DAO;
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
        return list.get(position).getIdp();// vi putextra idphong
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.baocao_layout_hd_listitem, parent, false);
        }

        TextView tvPhong = convertView.findViewById(R.id.baocao_hopdong_lisitem_tvphong);
        TextView tvNgayKy = convertView.findViewById(R.id.baocao_hopdong_lisitem_tvngayky);
        TextView tvHanConLai = convertView.findViewById(R.id.baocao_hopdong_lisitem_tvhanconlai);

        baocao_hopdong bchd = list.get(position);
        tvPhong.setText(bchd_DAO.getTenphongbyID(bchd.getIdp()));
        tvNgayKy.setText(bchd_DAO.getNgaykybyIDHD(bchd.getIdhopdong()));
        int ngay =bchd.getHanconlai();
        tvHanConLai.setText("Còn " + ngay + " ngày");
        if (ngay <= 30) {
            tvHanConLai.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            tvHanConLai.setTextColor(ContextCompat.getColor(context, R.color.purple));
        }


        return convertView;
    }
}
