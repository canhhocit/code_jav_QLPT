package com.example.logincustomer.data.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.qlthutien_DichVuConDAO;
import com.example.logincustomer.data.Model.qlthutien_DichVuCon;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class qlthutien_TaoHoaDonAdapter {

    private Context context;
    private TextView txtOtherServiceTotal;
    private ImageView imgExpandOther;
    private RecyclerView recyclerOtherService;
    private boolean isExpanded = false;

    private qlthutien_DichVuConAdapter adapter;
    private ArrayList<qlthutien_DichVuCon> list;
    private double totalOtherServices = 0.0;

    private final DecimalFormat df = new DecimalFormat("#,###");

    public interface OnTotalChangeListener {
        void onTotalChange(double total);
    }
    private OnTotalChangeListener listener;

    public qlthutien_TaoHoaDonAdapter(Context context, View rootView, OnTotalChangeListener listener) {
        this.context = context;
        this.listener = listener;

        txtOtherServiceTotal = rootView.findViewById(R.id.txt_otherService_totalPrice);
        imgExpandOther = rootView.findViewById(R.id.img_expand_OtherService_totalPrice);
        recyclerOtherService = rootView.findViewById(R.id.recycler_OtherService_totalPrice);

        setupRecycler();
        setupToggle();
    }

    private void setupRecycler() {
        qlthutien_DichVuConDAO dao = new qlthutien_DichVuConDAO(context);
        list = dao.getAll();

        calculateTotal();
        adapter = new qlthutien_DichVuConAdapter(list);
        recyclerOtherService.setLayoutManager(new LinearLayoutManager(context));
        recyclerOtherService.setAdapter(adapter);
        recyclerOtherService.setVisibility(View.GONE);
        txtOtherServiceTotal.setText(df.format(totalOtherServices) + " đ");
    }

    private void setupToggle() {
        View.OnClickListener toggle = v -> {
            if (isExpanded) {
                recyclerOtherService.setVisibility(View.GONE);
                imgExpandOther.animate().rotation(0).setDuration(200).start();
            } else {
                recyclerOtherService.setVisibility(View.VISIBLE);
                imgExpandOther.animate().rotation(180).setDuration(200).start();
            }
            isExpanded = !isExpanded;
        };
        imgExpandOther.setOnClickListener(toggle);
        txtOtherServiceTotal.setOnClickListener(toggle);
    }

    private void calculateTotal() {
        totalOtherServices = 0.0;
        for (qlthutien_DichVuCon dv : list) {
            totalOtherServices += dv.getGiatien();
        }
        if (listener != null) listener.onTotalChange(totalOtherServices);
    }

    public double getTotal() {
        return totalOtherServices;
    }
}
