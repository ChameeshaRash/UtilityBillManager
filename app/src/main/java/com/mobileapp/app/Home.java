package com.mobileapp.app;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    PieChart homePieChart;

    ExtendedFloatingActionButton addBillFAB;

    private ImageButton imgProfile;



    DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status bar color change
       // getWindow().setStatusBarColor(this.getResources().getColor(R.color.white));
        setContentView(R.layout.activity_home);


        //making reference to database
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("UtilityBill");


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
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
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
                        startActivity(new Intent(getApplicationContext(),UserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });



        //homePieChart content
        homePieChart = (PieChart) findViewById(R.id.homePieChart);
        homePieChart.getDescription().setEnabled(false);
        homePieChart.setExtraOffsets(5,10,5,5);
        homePieChart.setMaxAngle(180);
        homePieChart.setRotationAngle(180);
        homePieChart.setCenterTextOffset(0f,-20f);
        homePieChart.setCenterTextSize(24);
        homePieChart.setCenterTextColor(this.getResources().getColor(R.color.grey_100));
        homePieChart.setExtraBottomOffset(-50f);
        homePieChart.setExtraTopOffset(16f);


        homePieChart.setTouchEnabled(false); //disable interaction with chart
        homePieChart.setDragDecelerationFrictionCoef(0.5f);
        homePieChart.setDrawHoleEnabled(true);
        homePieChart.setHoleRadius(80f);
        homePieChart.setDrawEntryLabels(false);
        homePieChart.setDrawRoundedSlices(false);
        homePieChart.setHoleColor(this.getResources().getColor(R.color.white));
        homePieChart.setTransparentCircleRadius(0f);

        homePieChart.getLegend().setYOffset(26f);
        homePieChart.getLegend().setXEntrySpace(16f);
        homePieChart.getLegend().setTextSize(16f);
        homePieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        //text in center of pie chart
        homePieChart.setCenterText("Rs. 12,800");


        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(3200,"Electricity"));
        yValues.add(new PieEntry(3200,"Water"));
        yValues.add(new PieEntry(3200,"Internet"));
        yValues.add(new PieEntry(3200,"Fuel"));

        ArrayList<Integer> pieColors = new ArrayList<>();
        pieColors.add(this.getResources().getColor(R.color.orange));
        pieColors.add(this.getResources().getColor(R.color.purple));
        pieColors.add(this.getResources().getColor(R.color.blue));
        pieColors.add(this.getResources().getColor(R.color.green));

        //animate pieChart
        homePieChart.animateY(1000, Easing.EaseInOutCirc);


        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(4f);
        dataSet.setSelectionShift(0f);
        dataSet.setColors(pieColors);
        dataSet.setDrawValues(false);

        PieData data =new PieData((dataSet));
        data.setValueTextSize(12f);
        data.setValueTextColor(this.getResources().getColor(R.color.blue));

        homePieChart.setData(data);

        //catch the profile logo to make intent to profile page
        imgProfile=(ImageButton) findViewById(R.id.imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this, UserProfile.class));

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


        DAOUtilityBill daoUtilityBill = new DAOUtilityBill();


        addBill.setOnClickListener(v->{

            final RadioButton utility = dialog.findViewById(utilityType.getCheckedRadioButtonId());

            UtilityBill utilityBill = new UtilityBill(
                    utility.getText().toString(),
                    Float.parseFloat(amount.getText().toString()),
                    date.getText().toString());

            daoUtilityBill.add(utilityBill).addOnSuccessListener(suc->{

                Toast.makeText(this,"Bill Added!",Toast.LENGTH_SHORT).show();



            }).addOnFailureListener(er->{

                Toast.makeText(this,""+er.getMessage(),Toast.LENGTH_SHORT).show();

            });


        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }



}