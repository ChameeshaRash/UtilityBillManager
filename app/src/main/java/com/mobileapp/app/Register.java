package com.mobileapp.app;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity implements View.OnClickListener{

    //creating variables for link with firebase

     ImageView imgLogoRegister;
     EditText editTextEmail,editTextPassword;
     Button btnLoginRegister;
     RadioButton radioBtnAccept;

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
            case R.id.imgLogoRegister://define what happened if logo is clicked
                startActivity(new Intent(this, MainActivity.class));
                break;
//            case R.id.btnLoginRegister:
//                //validation part is include in this method below
//                if( onClickButtonMethod()==false) {
//                    btnLoginRegister.requestFocus();

//                }else {
//                    registerUser();
//                    Intent intentMain_Login=new Intent(Register.this,Home.class);
//                    startActivity(intentMain_Login);
//                    break;
//                }

            case R.id.btnLoginRegister://define what happened if register button is clicked
                registerUser();
                break;


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

            }


                 mAuth.createUserWithEmailAndPassword(emailRegister, passwordRegister)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(emailRegister);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Register.this, "User has been registered Successfully", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(Register.this, "Failed to Register.Try Again!", Toast.LENGTH_LONG).show();

                                                    }

                                                }
                                            });
                                } else {

                                    Toast.makeText(Register.this, "Failed to Register.Try Again!", Toast.LENGTH_LONG).show();


                                }
                            }
                        });
            }



            //define the method to check radio button
    private boolean onClickButtonMethod() {
        boolean selectedBooleanValue = radioBtnAccept.isSelected();

        if(selectedBooleanValue==false) {
            Toast.makeText(Register.this, "Please Agree to Terms and Condition", Toast.LENGTH_SHORT).show();

        }
        return selectedBooleanValue;



    }

}