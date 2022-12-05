package com.mobileapp.app;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {



    RecyclerView recyclerView_Home;
    LinearLayoutManager linearLayoutManager;
    List<BillCard> billCardList;
    Adapter_BillCard adapter_BillCard;
    BottomNavigationView bottomNavigationView;
    PieChart homePieChart;

    ExtendedFloatingActionButton addBillFAB;

    private ImageButton imgProfile;



    //to get total amount
    TextView electricityCardAmountHome,waterCardAmountHome,internetCardAmountHome,fuelCardAmountHome;


    //to get electricity bill details from add page
    RadioButton billTypeElectricity,billTypeInternet,billTypeWater,billTypeFuel;
    EditText billAmount;
    EditText dateInput;

    float totalElectricity,totalInternet,totalFuel,totalWater;
    TextView userEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status bar color change
       // getWindow().setStatusBarColor(this.getResources().getColor(R.color.white));
        setContentView(R.layout.activity_home);


        //find attributes to data retrieve
        billTypeElectricity=findViewById(R.id.billTypeElectricity);
        billTypeInternet=findViewById(R.id.billTypeInternet);
        billTypeWater=findViewById(R.id.billTypeWater);
        billTypeFuel=findViewById(R.id.billTypeFuel);

        electricityCardAmountHome=findViewById(R.id.electricityCardAmountHome);
        waterCardAmountHome=findViewById(R.id.waterCardAmountHome);
        internetCardAmountHome=findViewById(R.id.internetCardAmountHome);
        fuelCardAmountHome=findViewById(R.id.fuelCardAmountHome);

        userEmail=findViewById(R.id.textView_email);


        //making reference to database
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        //reading data from database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //execute code when receiving data
                snapshot.getValue();
                internetCardAmountHome.setText("Rs.2800");
                electricityCardAmountHome.setText("Rs."+totalElectricity);
                //                    String amountFromDB=snapshot.child(billType).child("amount").getValue(String.class);





//                    String amountFromDB=snapshot.child(billType).child("amount").getValue(String.class);
//                    String dateFromDB=snapshot.child(billType).child("date").getValue(String.class);
//
//
//                    Intent intent=new Intent(getApplicationContext(),Home.class);
//                    intent.putExtra("amount",amountFromDB);
//                    intent.putExtra("date",dateFromDB);
//                    startActivity(intent);
//
//
//


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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


        //recyclerview content
        initData();
        initRecyclerView();
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
        DAOElectricityBill daoElectricityBill=new DAOElectricityBill();

        addBill.setOnClickListener(v->{

            final RadioButton utility = dialog.findViewById(utilityType.getCheckedRadioButtonId());

            UtilityBill utilityBill = new UtilityBill(
                    utility.getText().toString(),
                    Float.parseFloat(amount.getText().toString()),
                    date.getText().toString());


            //calculate each catogaries' total amount
            if(utility.getText().toString()=="Internet"){
                totalInternet=+Float.parseFloat(amount.getText().toString());
                String dateInternet =date.getText().toString();

            }else if(utility.getText().toString()=="Water"){
                totalWater=+Float.parseFloat(amount.getText().toString());
                String dateWater =date.getText().toString();

            }else if(utility.getText().toString()=="Fuel"){
                totalFuel=+Float.parseFloat(amount.getText().toString());
                String dateFuel =date.getText().toString();

            }else if(utility.getText().toString()=="Electricity"){
                //DatabaseReference referenceElec= FirebaseDatabase.getInstance().getReference();
                totalElectricity=+Float.parseFloat(amount.getText().toString());
                String dateElectricity =date.getText().toString();

                ElectricityBill electricityBill=new ElectricityBill(userEmail.getText().toString(),"Electricity",
                        Float.parseFloat(amount.getText().toString()),
                        dateElectricity);
                //referenceElec.child("ElectricityBills").child(userEmail.getText().toString()).setValue(electricityBill);
                daoElectricityBill.add(electricityBill);

            }






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








    //add data to the recyclerview home

    private void initData() {

        billCardList = new ArrayList<>();
        billCardList.add(new BillCard(R.drawable.ic_electricity, "Electricity - August","Rs. 3,200","31.08.2022"));
        billCardList.add(new BillCard(R.drawable.ic_fuel, "Fuel - August","Rs. 3,200","31.08.2022"));
        billCardList.add(new BillCard(R.drawable.ic_internet, "Internet - August","Rs. 3,200","31.08.2022"));
        billCardList.add(new BillCard(R.drawable.ic_water, "Water - August","Rs. 3,200","31.08.2022"));
        billCardList.add(new BillCard(R.drawable.ic_electricity, "Electricity - August","Rs. 3,200","31.08.2022"));
        billCardList.add(new BillCard(R.drawable.ic_fuel, "Fuel - August","Rs. 3,200","31.08.2022"));
        billCardList.add(new BillCard(R.drawable.ic_internet, "Internet - August","Rs. 3,200","31.08.2022"));
        billCardList.add(new BillCard(R.drawable.ic_electricity, "Electricity - August","Rs. 3,200","31.08.2022"));
        billCardList.add(new BillCard(R.drawable.ic_fuel, "Fuel - August","Rs. 3,200","31.08.2022"));
        billCardList.add(new BillCard(R.drawable.ic_internet, "Internet - August","Rs. 3,200","31.08.2022"));




    }

    private void initRecyclerView() {

        recyclerView_Home=findViewById(R.id.recyclerView_Home);
        linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_Home.setLayoutManager(linearLayoutManager);
        adapter_BillCard = new Adapter_BillCard(billCardList);
        recyclerView_Home.setAdapter(adapter_BillCard);
        adapter_BillCard.notifyDataSetChanged();


    }

//
//    //to get sum of  Internet bill
//    private int TotalInternet(){
//        String billType=billTypeInternet.getText().toString().trim();
//        String bill_amount=billAmount.getText().toString().trim();
//        String billDate=dateInput.getText().toString().trim();
//
//        int Total_InternetAmount=10;
//
//
//
//
//        Query checkBill=reference.orderByChild("amount").equalTo(billType);
//        checkBill.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//
//                    String amountFromDB=snapshot.child(billType).child("amount").getValue(String.class);
//                    String dateFromDB=snapshot.child(billType).child("date").getValue(String.class);
//
//
//                    Intent intent=new Intent(getApplicationContext(),Home.class);
//                    intent.putExtra("amount",amountFromDB);
//                    intent.putExtra("date",dateFromDB);
//                    startActivity(intent);
//
//
//                }
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return Total_InternetAmount;
//
//    }
//
//    private void showInternetTotal(){
//        Intent intent=getIntent();
//        int amountShowInternet=TotalInternet();
//        electricityCardAmount.setText("Rs."+amountShowInternet);
//    }

//    public void getDocument() {
//        // [START get_document]
//        DocumentReference docRef = db.collection("UtilityBill").document("reference ");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//        // [END get_document]
//    }





}