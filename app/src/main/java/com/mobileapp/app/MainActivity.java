package com.mobileapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;


public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    ScreenshotsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dots indicator
        DotsIndicator dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);

        viewPager= findViewById(R.id.sceensot_slider);

        adapter =new ScreenshotsAdapter(this);
        viewPager.setAdapter(adapter);

        dotsIndicator.setViewPager(viewPager);

    }
    public void login(View view) {
        Intent i = new Intent(MainActivity.this, BottomSheet_Login.class);
        startActivity(i);

//        BottomSheet_Login bottomSheet = new BottomSheet_Login();
//        bottomSheet.show(getSupportFragmentManager(),
//                "ModalBottomSheet");

    }


    public void register(View view) {

    }


}