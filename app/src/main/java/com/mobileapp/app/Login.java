package com.mobileapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_emailLogin,editText_PasswordLogin;
    private Button btnLogIn;

    private TextView forgotPassword;


    ImageButton btnSignInwithGoogle;
    private GoogleSignInClient client;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //catch the login button
        btnLogIn=(Button)findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);

        //catch the email and password that user inputs
        editText_emailLogin=(EditText)findViewById(R.id.editTextEmailAddressLogin);
        editText_PasswordLogin=(EditText)findViewById(R.id.editTextPasswordLogin);


        //catch the forgot password text to make intent from login to reset password
        forgotPassword=(TextView) findViewById(R.id.textView_forgotPassword);
        forgotPassword.setOnClickListener(this);


        //catch the sign up with google button
        btnSignInwithGoogle=(ImageButton) findViewById(R.id.btn_LoginByGmail);
        GoogleSignInOptions options=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client= GoogleSignIn.getClient(this,options);
        btnSignInwithGoogle.setOnClickListener(new View.OnClickListener() {
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

                                    //creating firebase object
                                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                                    if(user.isEmailVerified()){
                                        //redirect to user profile
                                        Intent intenttoSignIn=new Intent(getApplicationContext(),Home.class);
                                        startActivity(intenttoSignIn);
                                    }else {
                                        user.sendEmailVerification();
                                        Toast.makeText(Login.this,"Check your email to verify your account!",Toast.LENGTH_LONG).show();
                                    }

                                }else{
                                    Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogIn:
                userLogin();
                break;
            case R.id.textView_forgotPassword:
                startActivity(new Intent(this,Forget_Password.class));
                break;
        }

    }

    private void userLogin() {
        String email=editText_emailLogin.getText().toString().trim();
        String password=editText_PasswordLogin.getText().toString().trim();

        //validations for email and password
        if(email.isEmpty()){
            editText_emailLogin.setError("Email is Required!");
            editText_emailLogin.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText_emailLogin.setError("Please enter a valid email!");
            editText_emailLogin.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editText_PasswordLogin.setError("Password is Required!");
            editText_PasswordLogin.requestFocus();
            return;
        }

        if(password.length()<8){
            editText_PasswordLogin.setError("Min password length should be 8 characters!");
            editText_PasswordLogin.requestFocus();

        }


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //creating firebase object
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        //redirect to user profile
                        startActivity(new Intent(Login.this,Home.class));
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(Login.this,"Check your email to verify your account!",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(Login.this,"Failed to login !Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}