package com.example.myandroidlibrary;

import android.content.Context;
import android.widget.Toast;

public class MyMessage {

    public void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
