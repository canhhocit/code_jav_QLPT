package com.example.logincustomer.ui.QLthutien_nguyen;

public class s {
    //1A : Tiền dịch vụ khác


    // trong oncreat {
    //         anhxa();
    // ----------id  1A-----------------------------------------------------
    //        // Lấy giá điện nước từ database (nếu có)
    //        layGiaMacDinhTuDatabase();
    //        // --- SETUP "Tiền dịch vụ khác" (RecyclerView con, adapter, DAO) ---
    //        dichVuConDAO = new qlthutien_DichVuConDAO(this);
    //        listOtherServices = dichVuConDAO.getAll(); // lấy tất cả dich vu con từ DB
    //        // tính tổng các dịch vụ con
    //        calculateTotalOtherServices();
    //        otherAdapter = new qlthutien_DichVuConAdapter(listOtherServices);
    //        recyclerOtherService.setLayoutManager(new LinearLayoutManager(this));
    //        recyclerOtherService.setAdapter(otherAdapter);
    //        recyclerOtherService.setVisibility(View.GONE); // mặc định ẩn
    //        // hiển thị tổng dịch vụ khác (đã format)
    //        txtOtherServiceTotal.setText(df.format(totalOtherServices));//+đ
    //
    //        // hành vi mở/đóng khi bấm mũi tên hoặc bấm vào tổng tiền
    //        View.OnClickListener toggleOther = v -> toggleOtherServices();
    //        imgExpandMoneyBillRoom.setOnClickListener(toggleOther);
    //        txtOtherServiceTotal.setOnClickListener(toggleOther);
    // ---------------------------------------------------------------
    // }
    //            func
    // ----------id  1A-----------------------------------------------------
//    private void toggleOtherServices() {
//        if (isOtherExpanded) {
//            recyclerOtherService.setVisibility(View.GONE);
//            imgExpandMoneyBillRoom.animate().rotation(0).setDuration(200).start();
//        } else {
//            recyclerOtherService.setVisibility(View.VISIBLE);
//            imgExpandMoneyBillRoom.animate().rotation(180).setDuration(200).start(); // xoay chỉ minh hoạ
//        }
//        isOtherExpanded = !isOtherExpanded;
//    }
//
//    //nếu có thì dùng, không có thì dùng mặc định
//    private void layGiaMacDinhTuDatabase() {
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT giadien, gianuoc FROM GiaMacDinh WHERE id = 1", null);
//
//        if (cursor.moveToFirst()) {
//            giaDien = cursor.getDouble(0);
//            giaNuoc = cursor.getDouble(1);
//        } else {
//            giaDien = GIA_DIEN_MACDINH;
//            giaNuoc = GIA_NUOC_MACDINH;
//        }
//
//        cursor.close();
//        db.close();
//    }
//
//    private void calculateTotalOtherServices() {
//        totalOtherServices = 0.0;
//        if (listOtherServices != null) {
//            for (DichVuCon dv : listOtherServices) {
//                totalOtherServices += dv.getGiatien();
//            }
//        }
//    }
    // ---------------------------------------------------------------
//                <ImageView
//    android:id="@+id/btn_Delete_billRoom"
//    android:layout_width="28dp"
//    android:layout_height="28dp"
//    android:src="@drawable/img_trash"
//    app:tint="#FF0000" />
}



//-----------------luuq ya
//trong giamacdinhdiennuocDAO để ý hàm getgiadien & getgianuoc