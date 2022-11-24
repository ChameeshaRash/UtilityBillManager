package com.mobileapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity implements View.OnClickListener{

    //creating variables for link with firebase

     ImageView imgLogoRegister;
     EditText editTextEmail,editTextPassword;
     Button btnLoginRegister;
     RadioButton radioBtnAccept;


//    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //catch the logo to making intent from register to Main activity by app logo
        imgLogoRegister =(ImageView) findViewById(R.id.imgLogoRegister);
        imgLogoRegister.setOnClickListener(this);

        btnLoginRegister =(Button) findViewById(R.id.btnLoginRegister);
        btnLoginRegister.setOnClickListener(this);

        //catch the user inputs
        editTextEmail=(EditText) findViewById(R.id.editTextEmailAddressRegister);
        editTextPassword=(EditText) findViewById(R.id.editTextPasswordRegister);
        radioBtnAccept=(RadioButton)findViewById(R.id.RadioBtnTextView_accept);


    }



    @Override
    public void onClick(View v) {
        //making intent from register to Main activity by app
        switch (v.getId()){
            case R.id.imgLogoRegister:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnLoginRegister:
                registerUser();//validation part is include in this method below
                break;

        }


    }

    private void registerUser() {
            String emailRegister=editTextEmail.getText().toString().trim();
            String passwordRegister=editTextPassword.getText().toString().trim();
            boolean selectedBooleanValue = radioBtnAccept.isSelected();




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

            }
            if(!selectedBooleanValue){
                Toast.makeText(Register.this,"Please Agree to Terms and Condition", Toast.LENGTH_SHORT).show();

            }

    }
}