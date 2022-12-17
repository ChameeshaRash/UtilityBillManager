package com.mobileapp.app;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TotalBillCalculateAdapter extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    private DatabaseReference UtilityBillReference;
    private FirebaseAuth mAuth;
    private TextView ElectricTotal,WaterTotal,FuelTotal,InternetTotal;
    private int totalElectricity;
    private int totalWater;
    private int totalFuel;
    private int totalInternet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        ElectricTotal=(TextView)findViewById(R.id.electricityCardAmountHome);
        WaterTotal=(TextView)findViewById(R.id.waterCardAmountHome);
        FuelTotal=(TextView)findViewById(R.id.fuelCardAmountHome);
        InternetTotal=(TextView)findViewById(R.id.internetCardAmountHome);



        mAuth=FirebaseAuth.getInstance();
        UtilityBillReference.child("UtilityBill").child(""+uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    totalElectricity= (int) snapshot.getChildrenCount();
                    ElectricTotal.setText(Integer.toString(totalElectricity));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ElectricTotal.setText("R.100.00");
            }
        });
    }
}
