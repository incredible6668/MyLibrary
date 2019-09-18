package com.example.myandroidlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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


public class OTPModule {

    private ProgressBar mProgressBar;
    private EditText mVerificationCodeFirstDigitField;
    private EditText mVerificationCodeSecondDigitField;
    private EditText mVerificationCodeThirdDigitField;
    private EditText mVerificationCodeForthDigitField;
    private EditText mVerificationCodeFifthDigitField;
    private EditText mVerificationCodeSixthDigitField;
    private Context mContext;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mToken;
    private Button mbtnContinue;
    private FirebaseAuth auth;
    private String mPhoneNumber;
    private Activity mActivity;

    public OTPModule(Context context, String phoneNumber, ProgressBar progressBar, EditText firstField, EditText secondField, EditText thirdField,
                     EditText forthField, EditText fifthField, EditText sixthField, Button btnContinue, Activity activity){
        this.mProgressBar = progressBar;
        this.mVerificationCodeFirstDigitField = firstField;
        this.mVerificationCodeSecondDigitField = secondField;
        this.mVerificationCodeThirdDigitField = thirdField;
        this.mVerificationCodeForthDigitField = forthField;
        this.mVerificationCodeFifthDigitField = fifthField;
        this.mVerificationCodeSixthDigitField = sixthField;
        this.mContext = context;
        this.mPhoneNumber = phoneNumber;
        this.mbtnContinue = btnContinue;
        this.mActivity = activity;

        this.mbtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullVerificationCode = mVerificationCodeFirstDigitField.getText().toString() + mVerificationCodeSecondDigitField.getText().toString()
                        + mVerificationCodeThirdDigitField.getText().toString() + mVerificationCodeForthDigitField.getText().toString()
                        + mVerificationCodeFifthDigitField.getText().toString() + mVerificationCodeSixthDigitField.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, fullVerificationCode);
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(mContext,"Verification succcessful", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(mContext, "Verification Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }



    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            mProgressBar.setVisibility(View.INVISIBLE);
            mVerificationCodeFirstDigitField.setText(String.valueOf(code.charAt(0)));
            mVerificationCodeSecondDigitField.setText(String.valueOf(code.charAt(1)));
            mVerificationCodeThirdDigitField.setText(String.valueOf(code.charAt(2)));
            mVerificationCodeForthDigitField.setText(String.valueOf(code.charAt(3)));
            mVerificationCodeFifthDigitField.setText(String.valueOf(code.charAt(4)));
            mVerificationCodeSixthDigitField.setText(String.valueOf(code.charAt(5)));
            //startTimer();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            String errorString = null;
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(mContext, R.string.invaild_mobile_no, Toast.LENGTH_SHORT).show();
                errorString = "Invalid mobile number.";
            } else if (e instanceof FirebaseTooManyRequestsException) {
                errorString = "SMS Quota exceeded.";
                Toast.makeText(mContext, R.string.sms_quota_exceeded, Toast.LENGTH_SHORT).show();
            }
            else {
                errorString = "Please check your internet connection.";
                Toast.makeText(mContext, R.string.please_check_your_internet_connection_text, Toast.LENGTH_SHORT).show();
            }

            final Dialog builder = new Dialog(mContext);

            builder.setContentView(R.layout.dialogbox_loginerror);
            builder.setCancelable(false);
            Window window = builder.getWindow();
            window.setLayout(1000, 400);
            builder.show();

            Button btnOk = (Button) builder.findViewById(R.id.btn_ok);
            TextView tv_error = (TextView) builder.findViewById(R.id.tv_error);

            tv_error.setText(errorString);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.dismiss();
                }
            });
        }

        @Override
        public void onCodeSent(String verificationId,
                PhoneAuthProvider.ForceResendingToken token) {
            Toast.makeText(mContext, R.string.code_sent, Toast.LENGTH_SHORT).show();
            mVerificationId = verificationId;
            mToken = token;
        }
    };

    public void sendVerificationCode() {
        FirebaseApp.initializeApp(mContext);
        auth = FirebaseAuth.getInstance();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber,
                60,
                TimeUnit.SECONDS,
                mActivity,
                mCallbacks);
    }

    public void resendVerificationCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                mActivity,               // Activity (for callback binding)
                mCallbacks,mToken);             // ForceResendingToken from callbacks
    }
}
