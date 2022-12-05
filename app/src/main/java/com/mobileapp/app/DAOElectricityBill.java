package com.mobileapp.app;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOElectricityBill {


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();


    private final DatabaseReference databaseReferenceElec;

    public DAOElectricityBill() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReferenceElec = db.getReference(ElectricityBill.class.getSimpleName());
    }


    public Task<Void> add(ElectricityBill electricityBill){
        return databaseReferenceElec.push().setValue(electricityBill);
    }
}
