package com.example.logincustomer.ui.QLthutien_nguyen;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.logincustomer.R;
public class Guide extends AppCompatActivity {
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlthutien_layout_guide);
        img = findViewById(R.id.img_arrowback_guide);
        img.setOnClickListener(v -> {finish();});}}