package com.example.logincustomer.data.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Model.hopdong_display;
import com.example.logincustomer.data.Model.hopdong_ifRoom;

import java.util.List;

public class qlhopdong_dsAdapter extends BaseAdapter {

    private Context context;
    private List<hopdong_display> listHopdong;

    public qlhopdong_dsAdapter(Context context, List<hopdong_display> listHopdong) {
        this.context = context;
        this.listHopdong = listHopdong;
    }

    @Override
    public int getCount() {
        return listHopdong.size();
    }

    @Override
    public Object getItem(int position) {
        return listHopdong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Dùng ViewHolder để tối ưu hiệu năng listview
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.qlhopdong_layout_item_dshopdong, parent, false);

            holder = new ViewHolder();
            holder.tvPhong = convertView.findViewById(R.id.dshopdong_tvphong);
            holder.tvTenKH = convertView.findViewById(R.id.dshopdong_tvtenkh);
            holder.tvNgayKy = convertView.findViewById(R.id.dshopdong_tvngayky);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        hopdong_display hd = listHopdong.get(position);

        holder.tvPhong.setText(hd.getTenphong() != null ? hd.getTenphong() : "Không rõ");
        holder.tvTenKH.setText(hd.getHoten() != null ? hd.getHoten() : "Chưa có khách");
        holder.tvNgayKy.setText(hd.getNgaykyhopdong() != null ? hd.getNgaykyhopdong() : "-");

        return convertView;
    }

    static class ViewHolder {
        TextView tvPhong, tvTenKH, tvNgayKy;
    }
    public void updateData(List<hopdong_display> newList) {
        listHopdong.clear();
        listHopdong.addAll(newList);
        notifyDataSetChanged();
    }
}
