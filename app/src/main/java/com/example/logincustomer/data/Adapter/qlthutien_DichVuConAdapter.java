package com.example.logincustomer.data.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Model.qlthutien_DichVuCon;

import java.util.List;

public class qlthutien_DichVuConAdapter extends RecyclerView.Adapter<qlthutien_DichVuConAdapter.ViewHolder> {

    private List<qlthutien_DichVuCon> list;

    public qlthutien_DichVuConAdapter(List<qlthutien_DichVuCon> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qlthutien_item_dichvucon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        qlthutien_DichVuCon dichVu = list.get(position);
        holder.txtTen.setText(dichVu.getTendichvu());
        holder.txtGia.setText(String.format("%,.0f", dichVu.getGiatien())); // format 50,000
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtGia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txt_item_Name_dichvucon);
            txtGia = itemView.findViewById(R.id.txt_item_gia_dichvucon);
        }
    }
}
