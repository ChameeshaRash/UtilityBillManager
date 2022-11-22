package com.mobileapp.app;

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

//
//        country.setText(obj.getCountry());
//        temperature.setText(obj.getTemperature());
//        humidity.setText(obj.getHumidity());
//        pressure.setText(obj.getPressure());


    }
    public void register(View view) {

    }

}