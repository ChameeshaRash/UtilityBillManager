package com.mobileapp.app;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.fonts.FontFamily;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {



    RecyclerView recyclerView_Home;
    LinearLayoutManager linearLayoutManager;
    List<BillCard> billCardList;
    Adapter_BillCard adapter_BillCard;

    PieChart homePieChart;

    private ImageButton imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status bar color change
       // getWindow().setStatusBarColor(this.getResources().getColor(R.color.white));
        setContentView(R.layout.activity_home);


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


        PieDataSet dataSet = new PieDataSet(yValues," ");
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
                startActivity(new Intent(Home.this,Profile.class));

            }
        });


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

}