package com.mobileapp.app;

import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class BottomSheet_Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheet_login);

//        mBottomSheetLayout  = findViewById(R.id.bottom_sheet_layout);
//        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
//        algo_button = (Button) findViewById(R.id.btnlogin);
//
//
//        algo_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                } else {
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//
//            }
//        });
//
//        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//            }
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//                algo_button.setRotation(slideOffset * 180);
//            }
//        });

    }






    @Override
    public void onResume() {
        super.onResume();
    }
}