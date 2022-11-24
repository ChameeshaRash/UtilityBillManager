package com.mobileapp.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener{

    //creating variables for link with firebase

    private ImageView AppLogoToMainActivity;
    private EditText editTextEmail,editTextPassword;
    Button btnRegister;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //catch the logo to making intent from register to Main activity by app logo
        AppLogoToMainActivity =(ImageView) findViewById(R.id.imgLogoRegister);
        AppLogoToMainActivity.setOnClickListener(this);

        //catch the user inputs
        editTextEmail=(EditText) findViewById(R.id.editTextEmailAddressRegister);
        editTextPassword=(EditText) findViewById(R.id.editTextPasswordRegister);


    }


//    @Override
//    public void onResume() {
//        super.onResume();
//    }


    @Override
    public void onClick(View v) {
        //making intent from register to Main activity by app
        switch (v.getId()){
            case R.id.imgLogoRegister:
                startActivity(new Intent(Register.this, MainActivity.class));
                break;
            case R.id.btnLoginRegister:
                    registerUser();

        }

    }

    private void registerUser() {
        String emailRegister=editTextEmail.getText().toString().trim();
        String passwordRegister=editTextPassword.getText().toString().trim();

        if(emailRegister.isEmpty()){
            editTextEmail.setError("Email is Required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailRegister).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(passwordRegister.isEmpty()){
            editTextPassword.setError("Password is Required!");
            editTextPassword.requestFocus();
            return;
        }

        if(passwordRegister.length()<8){
            editTextPassword.setError("Min password length should be 8 characters!");
            editTextPassword.requestFocus();
            return;
        }


    }
}