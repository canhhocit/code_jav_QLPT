package com.example.logincustomer.ui.QLthutien_nguyen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logincustomer.R;

public class BillRoomActivity extends AppCompatActivity {

    private ImageView imgBackBillRoom, btnDeleteBillRoom, imgStatusBillRoom, imgExpandMoneyBillRoom;
    private TextView txtRoomBillRoom, txtStatusBillRoom, txtDateBillRoom,
            txtQuantityPeopleBillRoom, txtOldWaterBillRoom, txtNewWaterBillRoom, txtPriceWaterBillRoom,
            txtSumServiceWaterBillRoom, txtOldElectricBillRoom, txtNewElectricBillRoom,
            txtPriceElectricBillRoom, tvNuocMoi, txtOtherServiceBillRoom, txtSumMoneyBillRoom;
    private EditText edtNoteBillRoom;
    private Button btnBackBillRoom, btnPayBillRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlthutien_layout_billroom);

        anhxa();

        txtRoomBillRoom.setText(getIntent().getStringExtra("tenPhong"));
        txtOldWaterBillRoom.setText(String.valueOf(getIntent().getIntExtra("Nuoccu", 0)));
        txtNewWaterBillRoom.setText(String.valueOf(getIntent().getIntExtra("Nuocmoi", 0)));
        txtOldElectricBillRoom.setText(String.valueOf(getIntent().getIntExtra("Diencu", 0)));
        txtNewElectricBillRoom.setText(String.valueOf(getIntent().getIntExtra("Dienmoi", 0)));

    }

    private void anhxa(){
        // Ánh xạ
        imgBackBillRoom = findViewById(R.id.img_back_billRoom);
        btnDeleteBillRoom = findViewById(R.id.btn_Delete_billRoom);
        imgStatusBillRoom = findViewById(R.id.img_status_billRoom);
        imgExpandMoneyBillRoom = findViewById(R.id.img_expandMoney_billRoom);

        txtRoomBillRoom = findViewById(R.id.txt_room_billRoom);
        txtStatusBillRoom = findViewById(R.id.txt_status_billRoom);
        txtDateBillRoom = findViewById(R.id.txt_date_billRoom);
        txtQuantityPeopleBillRoom = findViewById(R.id.txt_quantityPeople_billRoom);
        txtOldWaterBillRoom = findViewById(R.id.txt_oldWater_billRoom);
        txtNewWaterBillRoom = findViewById(R.id.txt_newWater_billRoom);
        txtPriceWaterBillRoom = findViewById(R.id.txt_priceWater_billRoom);
        txtSumServiceWaterBillRoom = findViewById(R.id.txt_sumServiceWater_billRoom);
        txtOldElectricBillRoom = findViewById(R.id.txt_oldElectric_billRoom);
        txtNewElectricBillRoom = findViewById(R.id.txt_newElectric_billRoom);
        txtPriceElectricBillRoom = findViewById(R.id.txt_priceElectric_billRoom);
        tvNuocMoi = findViewById(R.id.tvNuocMoi);
        txtOtherServiceBillRoom = findViewById(R.id.txt_otherService_billRoom);
        txtSumMoneyBillRoom = findViewById(R.id.txt_sumMoney_billRoom);

        edtNoteBillRoom = findViewById(R.id.edt_note_billRoom);

        btnBackBillRoom = findViewById(R.id.btn_back_billRoom);
        btnPayBillRoom = findViewById(R.id.btn_pay_billRoom);
    }

}

