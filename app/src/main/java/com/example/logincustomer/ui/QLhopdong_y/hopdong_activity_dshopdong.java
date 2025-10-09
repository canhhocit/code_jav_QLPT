package com.example.logincustomer.ui.QLhopdong_y;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.qlhopdong_dsAdapter;
import com.example.logincustomer.data.DAO.qlhopdong_hopdongDAO;
import com.example.logincustomer.data.Model.hopdong_display;

import java.util.List;

public class hopdong_activity_dshopdong extends AppCompatActivity {

    private ListView lvHopDong;
    private qlhopdong_dsAdapter adapter;
    private qlhopdong_hopdongDAO hdDAO;
    private List<hopdong_display> listHopDong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlhopdong_activity_dshopdong);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainhopdong), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhXa();
        listviewShow();
        listControl();
    }

    private void anhXa() {
        lvHopDong = findViewById(R.id.hopdong_listphong);
        hdDAO = new qlhopdong_hopdongDAO(this);
    }

    private void listviewShow() {
        listHopDong = hdDAO.getAllHopDongDisplay();
        adapter = new qlhopdong_dsAdapter(this, listHopDong);
        lvHopDong.setAdapter(adapter);
    }

    private void listControl() {
        lvHopDong.setOnItemClickListener((parent, view, position, id) -> {
            hopdong_display hd = (hopdong_display) parent.getItemAtPosition(position);
            Toast.makeText(
                    this,
                    "Phòng: " + hd.getTenphong() + "\nNgười ký: " + hd.getHoten() + "\nNgày ký: " + hd.getNgaykyhopdong(),
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listviewShow();
    }
}
