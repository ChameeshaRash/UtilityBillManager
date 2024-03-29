package com.mobileapp.app;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
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


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;


public class Home extends AppCompatActivity {



    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    BottomNavigationView bottomNavigationView;
    PieChart homePieChart;
    ExtendedFloatingActionButton addBillFAB;
    private ImageButton imgProfile;
    DatabaseReference mDatabaseRef;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    RecyclerView recyclerViewHome;
    SavedBillsAdapter savedbillAdapterHome;

    //show total
    FirebaseAuth mAuth;
    TextView ElectricTotal,WaterTotal,FuelTotal,InternetTotal;
    String totalString="Fetching...";
    TextView viewAll;



    int electricityPie;
    int waterPie;
    int internetPie;
    int fuelPie;

    public int getElectricityPie() {
        return electricityPie;
    }

    public void setElectricityPie(int electricityPie) {
        this.electricityPie = electricityPie;
    }

    public int getWaterPie() {
        return waterPie;
    }

    public void setWaterPie(int waterPie) {
        this.waterPie = waterPie;
    }

    public int getInternetPie() {
        return internetPie;
    }

    public void setInternetPie(int internetPie) {
        this.internetPie = internetPie;
    }

    public int getFuelPie() {
        return fuelPie;
    }

    public void setFuelPie(int fuelPie) {
        this.fuelPie = fuelPie;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewAll=(TextView)findViewById(R.id.viewAllRecentBills);


        //making reference to database
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("UtilityBill");


        //show total
        ElectricTotal=(TextView)findViewById(R.id.electricityCardAmountHome);
        WaterTotal=(TextView)findViewById(R.id.waterCardAmountHome);
        FuelTotal=(TextView)findViewById(R.id.fuelCardAmountHome);
        InternetTotal=(TextView)findViewById(R.id.internetCardAmountHome);

        homePieChart = (PieChart) findViewById(R.id.homePieChart);
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        PieDataSet dataSet = new PieDataSet(yValues,"");
        PieData data =new PieData((dataSet));

        homePieChart.setNoDataText("Fetching Data...");
        homePieChart.setNoDataTextColor(R.color.grey_100);
        homePieChart.setNoDataTextTypeface(Typeface.DEFAULT_BOLD);
        homePieChart.setCenterText(totalString);//fetching....

        //homePieChart content
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


        //catch the profile logo to make intent to profile page
        imgProfile=(ImageButton) findViewById(R.id.imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Home.this, UserProfile.class));

            }
        });

        //show total
        mAuth=FirebaseAuth.getInstance();
        //filter by date-------------------------

        mDatabaseRef.child(""+uid).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArraySet<SavedBillsModel> savedBillList=new ArraySet<>();
                Double total = 0.00;
                Double totalElectricity=0.00;
                Double totalWater=0.00;
                Double totalFuel=0.00;
                Double totalInternet=0.00;
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH)+1;

                for( DataSnapshot ds :snapshot.getChildren()) {
                    SavedBillsModel saved_bills = ds.getValue(SavedBillsModel.class);

                    String sDate1=ds.child("date").toString();
                    String[] divide= sDate1.split("/");
                    String year1 = divide[2];
                    year1 = year1.replace(" }", "");
                    String month1 = divide[1];
                    String date = divide[0];//not use


                    if(Integer.toString(year).equals(year1)){

                        if(Integer.toString(month).equals(month1)){
                            assert saved_bills != null;
                            Double cost = (double) saved_bills.getAmount();
                            total = total + cost;
                            savedBillList.add(saved_bills);

                            //calculate total for each category
                            Log.d("chammmmeee", totalElectricity + "");
                            if(Objects.equals(saved_bills.getUtilityType(), "Electricity")){
                                totalElectricity+=cost;
                            }else if(Objects.equals(saved_bills.getUtilityType(), "Water")){
                                totalWater+=cost;
                            }else if(Objects.equals(saved_bills.getUtilityType(), "Internet")){
                                totalInternet+=cost;
                            }else if(Objects.equals(saved_bills.getUtilityType(), "Fuel")){
                                totalFuel+=cost;
                            }

                        }


                    }




                }

                //Log.d("TAG", total + "");


                //display total
                ElectricTotal.setText(""+(decfor.format(totalElectricity))+"\nLKR");
                WaterTotal.setText(""+(decfor.format(totalWater))+"\nLKR");
                InternetTotal.setText(""+(decfor.format(totalInternet))+"\nLKR");
                FuelTotal.setText(""+(decfor.format(totalFuel))+"\nLKR");


                homePieChart.notifyDataSetChanged();
                homePieChart.invalidate();
                homePieChart.setCenterText("Rs:"+decfor.format(total));


                setElectricityPie((int)Math.round(totalElectricity));
                Log.d("TAG", getElectricityPie() + "iiiiiiiiiiiiiiiiiiiiTotalElec");

                setWaterPie((int)Math.round(totalWater));
                setInternetPie((int)Math.round(totalInternet));
                setFuelPie((int)Math.round(totalFuel));



                yValues.clear();
                yValues.add(new PieEntry(getElectricityPie(),"Electricity"));
                yValues.add(new PieEntry(getWaterPie(),"Water"));
                yValues.add(new PieEntry(getInternetPie(),"Internet"));
                yValues.add(new PieEntry(getFuelPie(),"Fuel"));
                homePieChart.setData(data);


                homePieChart.animateY(1000, Easing.EaseInOutCirc);//animate pieChart


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        ArrayList<Integer> pieColors = new ArrayList<>();
        pieColors.add(this.getResources().getColor(R.color.orange));
        pieColors.add(this.getResources().getColor(R.color.blue));
        pieColors.add(this.getResources().getColor(R.color.purple));
        pieColors.add(this.getResources().getColor(R.color.green));



        dataSet.setSliceSpace(4f);
        dataSet.setSelectionShift(0f);
        dataSet.setColors(pieColors);
        dataSet.setDrawValues(false);

        data.setValueTextSize(12f);
        data.setValueTextColor(this.getResources().getColor(R.color.blue));


        //Add bill FAB
        addBillFAB = findViewById(R.id.fabAddBill);
        addBillFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

            }
        });



        //retrieve data
        recyclerViewHome=(RecyclerView)findViewById(R.id.recyclerView_Home);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<SavedBillsModel> options=
                new FirebaseRecyclerOptions.Builder<SavedBillsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UtilityBill").child(""+uid),SavedBillsModel.class)
                        .build();

        savedbillAdapterHome=new SavedBillsAdapter(options);
        recyclerViewHome.setAdapter(savedbillAdapterHome);

        //bottom navigation

        bottomNavigationView = findViewById(R.id.bottomNavigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
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

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToViewHistory=new Intent(getApplicationContext(),SavedBills.class);
                startActivity(intentToViewHistory);
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

                datePickerDialog = new DatePickerDialog(Home.this, new DatePickerDialog.OnDateSetListener() {
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


    @Override
    protected void onStart() {
        super.onStart();
        savedbillAdapterHome.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
        savedbillAdapterHome.stopListening();
    }







}