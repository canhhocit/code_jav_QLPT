package com.example.logincustomer.data.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Model.qlthutien_DichVuCon;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class qlthutien_TotalPriceAdapter extends RecyclerView.Adapter<qlthutien_TotalPriceAdapter.ViewHolder> {

    private final ArrayList<qlthutien_DichVuCon> list;
    private double usedE, totalE, usedW, totalW;
    private final DecimalFormat df = new DecimalFormat("#,###");

    public qlthutien_TotalPriceAdapter(ArrayList<qlthutien_DichVuCon> list) {
        this.list = list;
    }

    public void setValues(double usedE, double totalE, double usedW, double totalW) {
        this.usedE = usedE;
        this.totalE = totalE;
        this.usedW = usedW;
        this.totalW = totalW;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qlthutien_item_total_priceroom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        qlthutien_DichVuCon dv = list.get(position);

        holder.txtTen.setText(dv.getTendichvu());
        if (position == 0) {
            holder.txtSoLuong.setText(String.valueOf((int) usedE));
            holder.txtGia.setText(df.format(dv.getGiatien()));
            holder.txtThanhTien.setText(df.format(totalE));
        } else {
            holder.txtSoLuong.setText(String.valueOf((int) usedW));
            holder.txtGia.setText(df.format(dv.getGiatien()));
            holder.txtThanhTien.setText(df.format(totalW));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtSoLuong, txtGia, txtThanhTien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txt_arti_itemTotalPrice);
            txtSoLuong = itemView.findViewById(R.id.txt_Quantity_itemTotalPrice);
            txtGia = itemView.findViewById(R.id.txt_Price_itemTotalPrice);
            txtThanhTien = itemView.findViewById(R.id.txt_Total_itemTotalPrice);
        }
    }
}
