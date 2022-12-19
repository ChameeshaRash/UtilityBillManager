package com.mobileapp.app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.GoogleAuthProvider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity implements View.OnClickListener{

    //creating variables for link with firebase

     ImageView imgLogoRegister;
     EditText editTextEmail,editTextPassword;
     Button btnLoginRegister;
     ImageButton btnSignUpwithGoogle;
     private GoogleSignInClient client;




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


        //catch the sign up with google button
        btnSignUpwithGoogle=(ImageButton) findViewById(R.id.btn_LoginByGmail);
        GoogleSignInOptions options=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client= GoogleSignIn.getClient(this,options);
        btnSignUpwithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSignUpwithGoogle=client.getSignInIntent();

                startActivityForResult(intentToSignUpwithGoogle,1234);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1234){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                AuthCredential credintial= GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credintial)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                  if(task.isSuccessful()){
                                    User user = new User(account.getEmail());

                                      //to store email in database
                                      FirebaseDatabase.getInstance().getReference("Users")
                                              .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                              .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                  @Override
                                                  public void onComplete(@NonNull Task<Void> task) {
                                                      if (task.isSuccessful()) {
                                                          Intent intenttoSignIn=new Intent(getApplicationContext(),Home.class);
                                                          startActivity(intenttoSignIn);
                                                          Toast.makeText(Register.this, "User has been registered Successfully", Toast.LENGTH_LONG).show();
                                                      } else {
                                                          Toast.makeText(Register.this, "Failed to Register.Try Again!", Toast.LENGTH_LONG).show();

                                                      }

                                                  }
                                              });
//-------------------------------------------------------------------------------------------
//
                                }else{
                                    Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });






            } catch (ApiException e) {
                e.printStackTrace();
            }

        }









    }


    @Override
    public void onClick(View v) {
        //making intent from register to Main activity by app
        switch (v.getId()){
            case R.id.imgLogoRegister://define what happened if logo is clicked
                startActivity(new Intent(this, MainActivity.class));
                break;

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
                                                        startActivity(new Intent(Register.this,Verify_Email.class));//change the intent to authentication view except login
                                                        //Toast.makeText(Register.this, "User has been registered Successfully.Please check the email to verify the account!", Toast.LENGTH_LONG).show();

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


            public void checkMail(View v){


            }




    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            Toast.makeText(Register.this,"Please Log out !",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Register.this,Profile.class));
            finish();
        }else {
            registerUser();
            Toast.makeText(Register.this,"You can Register now !",Toast.LENGTH_SHORT).show();
        }
    }


}