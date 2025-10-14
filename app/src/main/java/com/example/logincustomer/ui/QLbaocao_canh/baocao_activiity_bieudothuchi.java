package com.example.logincustomer.ui.QLbaocao_canh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.baocao_doanhthuDAO;
import com.example.logincustomer.data.Model.baocao_doanhthu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class baocao_activiity_bieudothuchi extends AppCompatActivity {
    private Context context = baocao_activiity_bieudothuchi.this;
    private TextView tvMenu;
    private BarChart chartThu, chartChi;
    private ListView lvNam;
    private baocao_doanhthuDAO dtDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.baocao_activiity_bieudothuchi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhXa();
        menu();
        listviewYear();
    }
    private void listviewYear() {
        dtDAO = new baocao_doanhthuDAO(context);
        List<Integer> years = dtDAO.getYears();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, years);
        lvNam.setAdapter(adapter);

        // Hiển thị chart mặc định năm hiện tại
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        showBarchart(chartThu, dtDAO.getThubyYear(String.valueOf(currentYear)), "Thu", String.valueOf(currentYear));
        showBarchart(chartChi, dtDAO.getChibyYear(String.valueOf(currentYear)), "Chi", String.valueOf(currentYear));

        lvNam.setOnItemClickListener((parent, view, position, id) -> {
            String year = years.get(position) + "";
            showBarchart(chartThu, dtDAO.getThubyYear(year), "Thu", year);
            showBarchart(chartChi, dtDAO.getChibyYear(year), "Chi", year);
        });
    }



    private void showBarchart(BarChart chart, List<baocao_doanhthu> listBC, String loai, String year) {
        // Khởi tạo dữ liệu 12 tháng = 0
        ArrayList<Double> data = new ArrayList<>();
        for (int i = 0; i < 12; i++){ data.add(0.0);}

        // Đổ dữ liệu từ listBC vào đúng tháng
        for (baocao_doanhthu x : listBC) {
            int thang = x.getThang() - 1;
            if (thang >= 0 && thang < 12) {
                if (loai.equalsIgnoreCase("Thu")){
                    data.set(thang, x.getTongthu());
                }
                else data.set(thang, x.getTongchi());
            }
        }

        // Tạo BarEntry
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) entries.add(new BarEntry(i + 1, data.get(i).floatValue()));

        // Tô màu, tháng hiện tại nổi bật
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        BarDataSet dataSet = new BarDataSet(entries, loai + " " + year);
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            colors.add(i + 1 == currentMonth ? Color.parseColor("#2196F3") : Color.parseColor("#FF9800"));
        }
        dataSet.setColors(colors);
        dataSet.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                if(value >=100_000 && value <1000_000){
                    return String.format("%.2f Nghìn", value / 1000f);
                }
                else if (value >= 1_000_000f) {
                    return String.format("%.1f Triệu", value / 1_000_000f);
                } else {
                    return String.format("%.0f", value);
                }
            }
        });

        // Set BarData
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.setData(barData);
        chart.setFitBars(true);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
    }

    private void menu() {
        tvMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, tvMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_bieudothuchi, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_bieudothuchi_quaylai) {
                    finish();
                    return true;
                } else if (itemId == R.id.menu_bieudothuchi_xemchitiet) {
                    Intent intent = new Intent(context, baocao_activity_homedsthuchi.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_bieudothuchi_chonnam) {
                    if (lvNam.getVisibility() == View.INVISIBLE) {
                        lvNam.setVisibility(View.VISIBLE);
                    } else {
                        lvNam.setVisibility(View.INVISIBLE);
                    }
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    private void anhXa() {
        tvMenu = findViewById(R.id.baocao_bieudothuchi_tvmenu);
        chartChi = findViewById(R.id.baocao_bieudochi);
        chartThu = findViewById(R.id.baocao_bieudothu);
        lvNam = findViewById(R.id.baocao_bieudothuchi_listviewnam);
        lvNam.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listviewYear();

    }
}
