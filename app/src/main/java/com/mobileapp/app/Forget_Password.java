package com.mobileapp.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_Password extends AppCompatActivity {

    private EditText emailToReset;
    private Button resetPassword;


    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //catch the email and button for do reset
        emailToReset=(EditText) findViewById(R.id.editTextEmailAddressToReset);
        resetPassword=(Button) findViewById(R.id.btnResetPassword);

        auth=FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }


        });



    }

    private void resetPassword() {
        //validation for user input email
        String email=emailToReset.getText().toString().trim();

        if(email.isEmpty()){
            emailToReset.setError("Email is required!");
            emailToReset.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailToReset.setError("Please provide valid email!");
            emailToReset.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forget_Password.this,"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Forget_Password.this,Reset_Password.class));

                }else {
                    Toast.makeText(Forget_Password.this,"Try again!Something wrong happened!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}