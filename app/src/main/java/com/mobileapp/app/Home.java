package com.mobileapp.app;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {

    RecyclerView recyclerView_Home;
    LinearLayoutManager linearLayoutManager;
    List<BillCard> billCardList;
    Adapter_BillCard adapter_BillCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initData();
        initRecyclerView();


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