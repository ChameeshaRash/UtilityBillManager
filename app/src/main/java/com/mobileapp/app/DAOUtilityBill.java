package com.mobileapp.app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DAOUtilityBill {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    private final DatabaseReference databaseReference;
    public DAOUtilityBill()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        databaseReference = db.getReference(UtilityBill.class.getSimpleName());

    }

    public Task<Void> add(UtilityBill utilityBill){
        return databaseReference.child(""+uid).push().setValue(utilityBill);
    }



}
