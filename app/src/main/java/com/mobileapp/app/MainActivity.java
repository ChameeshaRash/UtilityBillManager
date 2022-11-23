package com.mobileapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
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
        Intent i1 = new Intent(MainActivity.this, Login.class);
        startActivity(i1);


    }


    public void register(View view) {
        Intent i2 = new Intent(MainActivity.this, Register.class);
        startActivity(i2);

    }


}