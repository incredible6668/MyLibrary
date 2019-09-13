package com.example.atulgoel.nirogaapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class FirebaseInteractionClass extends AppCompatActivity {

    ArrayList<String> mOptions;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mOptionsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseInteraction firebaseResults = new FirebaseInteraction();
        mOptions = new ArrayList<>();
        mOptions = firebaseResults.getOptionList("QuestionsData","second","options");
    }
}