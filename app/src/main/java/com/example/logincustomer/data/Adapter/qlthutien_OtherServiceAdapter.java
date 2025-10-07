package com.example.logincustomer.data.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Model.DichVuCon;

import java.util.ArrayList;

public class qlthutien_OtherServiceAdapter extends RecyclerView.Adapter<qlthutien_OtherServiceAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DichVuCon> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEdit(DichVuCon service);
        void onDelete(DichVuCon service);
    }

    public qlthutien_OtherServiceAdapter(Context context, ArrayList<DichVuCon> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.qlthutien_item_setgiatrimacdinh, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DichVuCon service = list.get(position);
        holder.txtTen.setText(service.getTendichvu());
        holder.txtGia.setText(String.valueOf(service.getGiatien()));

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(service));
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc muốn xoá dịch vụ \"" + service.getTendichvu() + "\" không?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        listener.onDelete(service); // Gọi hàm xoá thật sự
                    })
                    .setNegativeButton("Huỷ", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtGia;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txt_item_NameService_SD);
            txtGia = itemView.findViewById(R.id.txt_item_PriceService_SD);
            btnEdit = itemView.findViewById(R.id.btn_item_edit_Service_SD);
            btnDelete = itemView.findViewById(R.id.btn_item_delete_Service_SD);
        }
    }
}