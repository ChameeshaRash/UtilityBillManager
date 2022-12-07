package com.mobileapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

//if user not logout this make intent to home
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(this,Home.class);
            startActivity(intent);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }



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