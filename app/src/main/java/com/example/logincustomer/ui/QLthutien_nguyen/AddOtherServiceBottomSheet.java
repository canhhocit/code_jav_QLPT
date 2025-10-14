package com.example.logincustomer.ui.QLthutien_nguyen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Model.qlthutien_DichVuCon;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AddOtherServiceBottomSheet extends BottomSheetDialog {

    private EditText edtName, edtPrice;
    private Button btnBack, btnSave;
    private OnSaveListener listener;
    private qlthutien_DichVuCon existingService;

    public interface OnSaveListener {
        void onSave(qlthutien_DichVuCon service);
    }

    public AddOtherServiceBottomSheet(@NonNull Context context, OnSaveListener listener) {
        super(context);
        this.listener = listener;
    }

    public AddOtherServiceBottomSheet(@NonNull Context context, qlthutien_DichVuCon service, OnSaveListener listener) {
        super(context);
        this.existingService = service;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.qlthutien_child_setgiatrimacdinh, null);
        setContentView(view);

        edtName = view.findViewById(R.id.edt_Name_OtherService);
        edtPrice = view.findViewById(R.id.edt_Price_OtherService);
        btnBack = view.findViewById(R.id.btn_back_OtherService);
        btnSave = view.findViewById(R.id.btn_update_OtherService);

        if (existingService != null) {
            edtName.setText(existingService.getTendichvu());
            edtPrice.setText(String.valueOf(existingService.getGiatien()));
        }

        btnBack.setOnClickListener(v -> dismiss());

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            if (name.isEmpty()) {
                edtName.setError("Vui lòng nhập tên dịch vụ");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(edtPrice.getText().toString().trim());
            } catch (NumberFormatException e) {
                edtPrice.setError("Giá không hợp lệ");
                return;
            }

            if (existingService == null) {
                listener.onSave(new qlthutien_DichVuCon(name, price));
            } else {
                existingService.setTendichvu(name);
                existingService.setGiatien(price);
                listener.onSave(existingService);
            }
            dismiss();
        });
    }
}
