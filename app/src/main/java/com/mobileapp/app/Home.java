package com.mobileapp.app;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {



    RecyclerView recyclerView_Home;
    LinearLayoutManager linearLayoutManager;
    List<BillCard> billCardList;
    Adapter_BillCard adapter_BillCard;

    private ImageButton imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //status bar color change
       // getWindow().setStatusBarColor(this.getResources().getColor(R.color.white));


        setContentView(R.layout.activity_home);

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