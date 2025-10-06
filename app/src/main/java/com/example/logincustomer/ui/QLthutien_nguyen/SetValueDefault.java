package com.example.logincustomer.ui.QLthutien_nguyen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.OtherServiceAdapter;
import com.example.logincustomer.data.DAO.DichVuConDAO;
import com.example.logincustomer.data.Model.DichVuCon;
import com.example.logincustomer.data.DAO.GiaMacDinhDAO;

import java.util.ArrayList;

public class SetValueDefault extends AppCompatActivity {

    private EditText edtElectric, edtWater;
    private GiaMacDinhDAO giaMacDinhDAO;

    private RecyclerView recyclerView;
    private Button btnAdd, btnSave;
    private DichVuConDAO dao;
    private ArrayList<DichVuCon> list;
    private OtherServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlthutien_layout_setgiatrimacdinh);

        recyclerView = findViewById(R.id.recycler_setDefaultPrice_SD);
        btnAdd = findViewById(R.id.btn_addOtherService_SD);
        btnSave = findViewById(R.id.btn_Save_SD);
        edtElectric = findViewById(R.id.edt_setdefaultElectric_SD);
        edtWater = findViewById(R.id.edt_setdefaultWater_SD);

        dao = new DichVuConDAO(this);
        list = dao.getAll();

        giaMacDinhDAO = new GiaMacDinhDAO(this);
        // Nạp giá trị mặc định đã lưu
        edtElectric.setText(String.valueOf(giaMacDinhDAO.getGiaDien()));
        edtWater.setText(String.valueOf(giaMacDinhDAO.getGiaNuoc()));

        adapter = new OtherServiceAdapter(this, list, new OtherServiceAdapter.OnItemClickListener() {
            @Override
            public void onEdit(DichVuCon service) {
                AddOtherServiceBottomSheet dialog = new AddOtherServiceBottomSheet(SetValueDefault.this, service, updated -> {
                    dao.update(updated);
                    refreshData();
                });
                dialog.show();
            }

            @Override
            public void onDelete(DichVuCon service) {
                dao.delete(service.getIddichvucon());
                refreshData();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 👉 Nút thêm dịch vụ mới
        btnAdd.setOnClickListener(v -> {
            AddOtherServiceBottomSheet dialog = new AddOtherServiceBottomSheet(this, newService -> {
                dao.insert(newService);
                refreshData();
            });
            dialog.show();
        });

        // 👉 Nút lưu toàn bộ danh sách hiện tại
        btnSave.setOnClickListener(v -> {
            try {
                // --- Lưu giá điện và giá nước ---
                double giaDien = Double.parseDouble(edtElectric.getText().toString().trim());
                double giaNuoc = Double.parseDouble(edtWater.getText().toString().trim());

                // Gọi DAO để cập nhật vào bảng giá mặc định
                GiaMacDinhDAO giaMacDinhDAO = new GiaMacDinhDAO(this);
                giaMacDinhDAO.updateGiaMacDinh(giaDien, giaNuoc);

                // --- Lưu danh sách dịch vụ con ---
                dao.deleteAll(); // Xóa dữ liệu cũ
                for (DichVuCon service : list) {
                    dao.insert(service); // Lưu lại dịch vụ mới
                }

                Toast.makeText(this, "Đã lưu giá trị mặc định thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Vui lòng nhập giá điện và nước hợp lệ!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Lưu thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }

    private void refreshData() {
        list.clear();
        list.addAll(dao.getAll());
        adapter.notifyDataSetChanged();
    }
}
