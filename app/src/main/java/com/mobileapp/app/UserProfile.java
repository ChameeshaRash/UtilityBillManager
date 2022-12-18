package com.mobileapp.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class UserProfile extends AppCompatActivity {

    private Button logout;
    BottomNavigationView bottomNavigationView;
    ExtendedFloatingActionButton addBillFAB;
    DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


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
}