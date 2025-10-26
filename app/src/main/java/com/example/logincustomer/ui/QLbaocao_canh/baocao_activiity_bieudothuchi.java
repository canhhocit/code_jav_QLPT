package com.example.logincustomer.ui.QLbaocao_canh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class baocao_activiity_bieudothuchi extends AppCompatActivity {
    private Context context = baocao_activiity_bieudothuchi.this;
    private TextView tvMenu;
    private BarChart chartThu, chartChi;
    private ListView lvNam;
    private LinearLayout lnNam;
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
        loadData();
    }

    private void loadData() {
        dtDAO = new baocao_doanhthuDAO(context);

        // listview năm
        List<Integer> years = dtDAO.getYears();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, years);
        lvNam.setAdapter(adapter);

        // show bieu do nam hientai
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        showbarchart(String.valueOf(currentYear));

        lvNam.setOnItemClickListener((parent, view, position, id) -> {
            String selectedYear = years.get(position).toString();
            showbarchart(selectedYear);
        });
    }

    private void showbarchart(String year) {
        List<baocao_doanhthu> thuList = dtDAO.getThubyYear(year);
        List<baocao_doanhthu> chiList = dtDAO.getChibyYear(year);

        showBarchart(chartThu, thuList, "Thu", year, Color.parseColor("#4CAF50"));
        showBarchart(chartChi, chiList, "Chi", year, Color.parseColor("#F44336"));
    }

    private void showBarchart(BarChart chart, List<baocao_doanhthu> listBC, String loai, String year, int mainColor) {
        double[] data_thang = new double[12];

        // gan dl cho thang
        for (baocao_doanhthu item : listBC) {
            int thang = item.getThang();

            if (thang >= 1 && thang <= 12) {
                double giaTri = loai.equalsIgnoreCase("Thu") ? item.getTongthu() : item.getTongchi();
                data_thang[thang - 1] = giaTri;
            }
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i + 1, (float) data_thang[i]));
        }


        BarDataSet dataSet = new BarDataSet(entries, loai + " năm " + year);

        // Màu
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int year_int = Integer.parseInt(year);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            if (i == currentMonth && year_int == currentYear) {
                colors.add(Color.parseColor("#2196F3"));
            } else {
                colors.add(mainColor);
            }
        }
        dataSet.setColors(colors);

        // format hien thi gia
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0) {
                    return "";
                } else if (value >= 1_000_000f) {
                    return String.format("%.1fTr", value / 1_000_000f);
                } else if (value >= 100_000f) {
                    return String.format("%.0fK", value / 1_000f);
                } else {
                    return String.format("%.0f", value);
                }
            }
        });
        dataSet.setValueTextSize(9f);
        dataSet.setValueTextColor(Color.BLACK);

        // Tạo BarData và cấu hình chart
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.8f);

        // Oy left
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setTextColor(Color.BLACK);
        chart.getAxisLeft().setTextSize(10f);

        chart.getAxisRight().setEnabled(false);

        // Ox
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0.5f);
        xAxis.setAxisMaximum(12.5f);
        xAxis.setLabelCount(12, false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(false);

        // Format Ox
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "T" + (int) value;
            }
        });

        // Cấu hình chung
        chart.setData(barData);
        chart.setFitBars(true);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(true);
        chart.getLegend().setTextSize(12f);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.animateY(1000);

        // Refresh chart
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
                    if (lnNam.getVisibility() == View.INVISIBLE) {
                        lnNam.setVisibility(View.VISIBLE);
                    } else {
                        lnNam.setVisibility(View.INVISIBLE);
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
        lnNam = findViewById(R.id.baocao_bieudothuchi_lnnam);
        lnNam.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}