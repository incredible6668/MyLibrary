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

    public OTPModule(Context context, ProgressBar progressBar, EditText firstField, EditText secondField, EditText thirdField,
                     EditText forthField, EditText fifthField, EditText sixthField, String verificationId,
                     PhoneAuthProvider.ForceResendingToken token, Button btnContinue, final Activity activity){
        this.mProgressBar = progressBar;
        this.mVerificationCodeFirstDigitField = firstField;
        this.mVerificationCodeSecondDigitField = secondField;
        this.mVerificationCodeThirdDigitField = thirdField;
        this.mVerificationCodeForthDigitField = forthField;
        this.mVerificationCodeFifthDigitField = fifthField;
        this.mVerificationCodeSixthDigitField = sixthField;
        this.mContext = context;
        this.mVerificationId = verificationId;
        this.mToken = token;
        this.mbtnContinue = btnContinue;
        this.mbtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullVerificationCode = mVerificationCodeFirstDigitField.getText().toString() + mVerificationCodeSecondDigitField.getText().toString()
                        + mVerificationCodeThirdDigitField.getText().toString() + mVerificationCodeForthDigitField.getText().toString()
                        + mVerificationCodeFifthDigitField.getText().toString() + mVerificationCodeSixthDigitField.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, fullVerificationCode);
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
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

    public String sendVerificationCode(String mobile, Activity activity) {
        FirebaseApp.initializeApp(mContext);
        auth = FirebaseAuth.getInstance();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                activity,
                mCallbacks);
        return mVerificationId;
    }

    public void resendVerificationCode(String phoneNumber, Activity activity) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks,mToken);             // ForceResendingToken from callbacks
    }
}
