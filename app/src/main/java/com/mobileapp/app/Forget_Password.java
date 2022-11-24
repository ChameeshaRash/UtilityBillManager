package com.mobileapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Forget_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    //making intent to reset password
    public void reset_Password(View view) {
        Intent i3 = new Intent(Forget_Password.this, Reset_Password.class);
        startActivity(i3);
    }
}