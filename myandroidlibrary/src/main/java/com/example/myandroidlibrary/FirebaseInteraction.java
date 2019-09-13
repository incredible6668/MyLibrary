package com.example.myandroidlibrary;

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
    private ArrayList<String> mOptions;

    public FirebaseInteraction(){
            mDatabase = FirebaseDatabase.getInstance();
    }

    public ArrayList<String> getOptionsList(String dbName, String tableName, String keyName){
        mOptionsReference = mDatabase.getReference(dbName)
                .child(tableName)
                .child(keyName);
        mOptionsReference.addValueEventListener(optionsListener);
        return mOptions;
    }

    ValueEventListener optionsListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mOptions = new ArrayList<>();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                mOptions.add(postSnapshot.getValue().toString());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
