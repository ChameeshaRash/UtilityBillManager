package com.mobileapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //making intent to forgot password
    public void forgot_Password(View view) {
        Intent i3 = new Intent(Login.this, Forget_Password.class);
        startActivity(i3);

    }
}