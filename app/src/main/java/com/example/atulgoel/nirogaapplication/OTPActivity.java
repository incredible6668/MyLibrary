package com.example.atulgoel.nirogaapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myandroidlibrary.MyMessage;
import com.example.myandroidlibrary.OTPModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    private EditText mVerificationCodeFirstDigitField;
    private EditText mVerificationCodeSecondDigitField;
    private EditText mVerificationCodeThirdDigitField;
    private EditText mVerificationCodeForthDigitField;
    private EditText mVerificationCodeFifthDigitField;
    private EditText mVerificationCodeSixthDigitField;
    private Button mbtnContinue;
    private String mVerificationId;
    private String mMobileNumber;
    private TextView mResendCodeText;
    private ProgressBar mProgressBar;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mToken;
    private ProgressDialog progressDialog;
    private String mAction;
    boolean stateChanged = false;
    OTPModule module ;
   /* private TextView mTimerText;
    private int minutes = 0;
    private int seconds = 60;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mVerificationCodeFirstDigitField = (EditText) findViewById(R.id.et_verificationCodeFirstDigit);
        mVerificationCodeSecondDigitField = (EditText) findViewById(R.id.et_verificationCodeSecondDigit);
        mVerificationCodeThirdDigitField = (EditText) findViewById(R.id.et_verificationCodeThirdDigit);
        mVerificationCodeForthDigitField = (EditText) findViewById(R.id.et_verificationCodeForthDigit);
        mVerificationCodeFifthDigitField = (EditText) findViewById(R.id.et_verificationCodeFifthDigit);
        mVerificationCodeSixthDigitField = (EditText) findViewById(R.id.et_verificationCodeSixthDigit);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mbtnContinue = (Button) findViewById(R.id.btn_continue);
        mResendCodeText = (TextView) findViewById(R.id.tv_resendCode);

        mResendCodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                module.resendVerificationCode();
            }
        });

        // mTimerText = (TextView) findViewById(R.id.tv_timer);

        mVerificationCodeFirstDigitField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    mVerificationCodeSecondDigitField.requestFocus();
                } else if (editable.length() == 0) {
                    mVerificationCodeFirstDigitField.clearFocus();
                }
            }
        });
        mVerificationCodeSecondDigitField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    mVerificationCodeThirdDigitField.requestFocus();
                } else if (editable.length() == 0) {
                    mVerificationCodeFirstDigitField.requestFocus();
                }
            }
        });

        mVerificationCodeThirdDigitField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    mVerificationCodeForthDigitField.requestFocus();
                } else if (editable.length() == 0) {
                    mVerificationCodeSecondDigitField.requestFocus();
                }
            }
        });

        mVerificationCodeForthDigitField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    mVerificationCodeFifthDigitField.requestFocus();
                } else if (editable.length() == 0) {
                    mVerificationCodeThirdDigitField.requestFocus();
                }
            }
        });

        mVerificationCodeFifthDigitField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    mVerificationCodeSixthDigitField.requestFocus();
                } else if (editable.length() == 0) {
                    mVerificationCodeForthDigitField.requestFocus();
                }
            }
        });

        mVerificationCodeSixthDigitField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    mVerificationCodeSixthDigitField.clearFocus();
                } else if (editable.length() == 0) {
                    mVerificationCodeFifthDigitField.requestFocus();
                }
            }
        });

        module = new OTPModule(this, "9541862699",mProgressBar,mVerificationCodeFirstDigitField,
                mVerificationCodeSecondDigitField,mVerificationCodeThirdDigitField,mVerificationCodeForthDigitField,
                mVerificationCodeFifthDigitField,mVerificationCodeSixthDigitField,
                mbtnContinue,OTPActivity.this);

        module.sendVerificationCode();

    }
}
