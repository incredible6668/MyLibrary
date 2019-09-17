package com.example.myandroidlibrary;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MobileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mMobileNumberField;
    Button mbtnContinue;
    private String mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mobile);

        Intent intent = getIntent();
        mAction = intent.getStringExtra("ACTION");

        mMobileNumberField = (EditText) findViewById(R.id.et_mobileNumber);
        mbtnContinue = (Button) findViewById(R.id.btn_continue);
        mbtnContinue.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mbtnContinue.setBackground(getResources().getDrawable(R.drawable.field_with_border));
            mbtnContinue.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
       /* if (i == R.id.btn_continue) {
            String mobileNumber = mMobileNumberField.getText().toString();
            if (validateMobileNumber(mobileNumber)) {
                *//*Intent intent = new Intent(getBaseContext(), OTPActivity.class);
                intent.putExtra("MobileNumber", mobileNumber);
                intent.putExtra("ACTION", mAction);
                startActivity(intent);*//*
            } else {
                mMobileNumberField.setError(getResources().getString(R.string.mobile_number_error_text));
            }
        }*/
    }

    private Boolean validateMobileNumber(String phone) {
        boolean check;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() != 10) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }
}

