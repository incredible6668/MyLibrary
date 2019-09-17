package com.example.myandroidlibrary;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class FirebaseInteraction {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mOptionsReference;
    private Boolean isOptionsListFilled;
    private ArrayList<String> mOptions;

    public FirebaseInteraction(){
            mDatabase = FirebaseDatabase.getInstance();
            mOptions = new ArrayList<>();
            isOptionsListFilled = false;
    }

    public void showOptions(){
        for(String option: mOptions){
        }
    }

    public void getOptionsList(final ArrayList<String> options, String dbName, String tableName, String keyName){
        mOptionsReference = mDatabase.getReference(dbName)
                .child(tableName)
                .child(keyName);
        mOptionsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    mOptions.add(postSnapshot.getValue().toString());
                }
                isOptionsListFilled = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
