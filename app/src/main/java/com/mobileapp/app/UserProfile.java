package com.mobileapp.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class UserProfile extends AppCompatActivity {

    private Button logout;
    BottomNavigationView bottomNavigationView;
    ExtendedFloatingActionButton addBillFAB;


    //user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        //Add bill FAB
        addBillFAB = findViewById(R.id.fabAddBill);
        addBillFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

            }
        });


        //bottom navigation

        bottomNavigationView = findViewById(R.id.bottomNavigator);
        bottomNavigationView.setSelectedItemId(R.id.userProfile);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.analytics:
                        startActivity(new Intent(getApplicationContext(),Analytics.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.savedBills:
                        startActivity(new Intent(getApplicationContext(),SavedBills.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.userProfile:
                        return true;
                }

                return false;
            }
        });





        //catch the sign out button
        logout=(Button)findViewById(R.id.btnSignOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
    }

    //add Bill bottom sheet
    private void showDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_bill_layout);

        final RadioGroup utilityType = dialog.findViewById(R.id.utilitySelection);
        final EditText amount = dialog.findViewById(R.id.billAmount);
        final EditText date = dialog.findViewById(R.id.dateInput);

        Button addBill = dialog.findViewById(R.id.btnAddBill);

        // date picker
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatePickerDialog datePickerDialog;

                datePickerDialog = new DatePickerDialog(UserProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);

                datePickerDialog.show();
            }

        });
        //date picker


        UtilityBillModel utilityBillModel = new UtilityBillModel();


        addBill.setOnClickListener(v->{

            final RadioButton utility = dialog.findViewById(utilityType.getCheckedRadioButtonId());

            UtilityBill utilityBill = new UtilityBill(
                    utility.getText().toString(),
                    Float.parseFloat(amount.getText().toString()),
                    date.getText().toString());

            utilityBillModel.add(utilityBill).addOnSuccessListener(suc->{

//                Toast.makeText(this,"Bill Added!",Toast.LENGTH_SHORT).show();
                dialog.setContentView(R.layout.add_bil_success_layout);

                Button addAnotherBill = dialog.findViewById(R.id.btnAddBillSuccessAnother);
                addAnotherBill.setOnClickListener(y->{
                    dialog.cancel();
                    showDialog();
                });

                Button addBillSuccessCancel = dialog.findViewById(R.id.btnAddBillSuccessCancel);
                addBillSuccessCancel.setOnClickListener(z->{
                    dialog.cancel();
                });



            }).addOnFailureListener(er->{

                Toast.makeText(this,""+er.getMessage(),Toast.LENGTH_SHORT).show();

                dialog.setContentView(R.layout.add_bill_failure);
                TextView subtitle = dialog.findViewById(R.id.txtAddBillFailSubTitle);
                subtitle.setText(""+er.getMessage());
                Button addBillTryAgain = dialog.findViewById(R.id.btnAddBillTryAgain);
                addBillTryAgain.setOnClickListener(w->{
                    dialog.cancel();
                    showDialog();
                });

                Button addBillFailCancel = dialog.findViewById(R.id.btnAddBillFailCancel);
                addBillFailCancel.setOnClickListener(u->{
                    dialog.cancel();
                });


            });


        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }


}