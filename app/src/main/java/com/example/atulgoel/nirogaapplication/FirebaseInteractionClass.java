package com.example.atulgoel.nirogaapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.myandroidlibrary.FirebaseInteraction;

import java.util.ArrayList;

public class FirebaseInteractionClass extends AppCompatActivity {

    ArrayList<String> mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseInteraction firebaseResults = new FirebaseInteraction();
        mOptions = new ArrayList<>();
        firebaseResults.addOptionsToList("QuestionsData", "second", "options");
        // Toast.makeText(this,mOptions.size(),Toast.LENGTH_SHORT).show();

        firebaseResults.showOptions();
    }
}