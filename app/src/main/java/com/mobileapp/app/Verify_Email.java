package com.mobileapp.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Verify_Email extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmit;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //catch the login button
        btnSubmit = (Button) findViewById(R.id.btnVerifiedSubmit);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVerifiedSubmit:
                loginAfterVerify();
                startActivity(new Intent(Verify_Email.this, MainActivity.class));
                break;

        }
    }


    //method to check email is verified
    protected void loginAfterVerify() {

        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag){
            startActivity(new Intent(Verify_Email.this, Home.class)); //intent to verify view to home
        }else{
            firebaseUser.sendEmailVerification();//send the email verification
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            mAuth.signOut();

        }
    }

}