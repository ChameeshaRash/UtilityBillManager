package com.mobileapp.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedBills extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_bills);



        //bottom navigation

        bottomNavigationView = findViewById(R.id.bottomNavigator);
        bottomNavigationView.setSelectedItemId(R.id.savedBills);

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
                        return true;

                    case R.id.userProfile:
                        startActivity(new Intent(getApplicationContext(),UserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });


    }
}