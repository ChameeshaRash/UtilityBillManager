package com.mobileapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;


public class MainActivity extends AppCompatActivity  {
    ViewPager viewPager;
    ScreenshotsAdapter adapter;
    MaterialButton register;
    Button login;

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



//        login = (Button) findViewById(R.id.btnLogin);
//        login.setOnClickListener(this);
//
//        //create a variable to hold the register button
//        register = (MaterialButton) findViewById(R.id.btnregister);
//        register.setOnClickListener(this);



    }


    @Override
    public void onResume() {
        super.onResume();
    }

//    public void login(View view) {
//        Intent i1 = new Intent(MainActivity.this, Login.class);
//        startActivity(i1);
//
//
//    }

//
//    public void register(View view) {
//        Intent i2 = new Intent(MainActivity.this, Register.class);
//        startActivity(i2);
//
//    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btnregister:
//                //intent from main activity to register page
//                Intent intentMain_Register = new Intent(MainActivity.this, Register.class);
//                startActivity(intentMain_Register);
//                break;
//            case R.id.btnLogin:
//                //intent from main activity to login page
//                Intent intentMain_Login=new Intent(MainActivity.this,Login.class);
//                startActivity(intentMain_Login);
//                break;
//
//        }
//    }

    public void register(View view) {
        //intent from main activity to register page
                Intent intentMain_Register = new Intent(MainActivity.this, Register.class);
                startActivity(intentMain_Register);


    }


    public void login(View view) {
        //intent from main activity to login page
                Intent intentMain_Login=new Intent(MainActivity.this,Login.class);
                startActivity(intentMain_Login);

    }

}