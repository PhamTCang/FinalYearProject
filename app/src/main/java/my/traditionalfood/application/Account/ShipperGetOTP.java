package my.traditionalfood.application.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import my.traditionalfood.application.ShipFoodPanel.BottomNav_ShipFoodPanel;
import my.traditionalfood.application.R;

public class ShipperGetOTP extends AppCompatActivity {

    String VerifyId;
    FirebaseAuth FbAuth;
    Button Verify, Resend;
    TextView Txt;
    EditText OTPCode;
    String PhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_get_otp);

        PhoneNumber = getIntent().getStringExtra("shipphone").trim();

        OTPCode = (EditText) findViewById(R.id.ShipOTPSI);
        Txt = (TextView) findViewById(R.id.EmptyTextShipSI);
        Verify = (Button) findViewById(R.id.VerifyShipBtnSI);
        Resend = (Button) findViewById(R.id.ResendOtpShipSI);

        FbAuth = FirebaseAuth.getInstance();

        Resend.setVisibility(View.INVISIBLE);
        Txt.setVisibility(View.INVISIBLE);

        SendOTPShip(PhoneNumber);

        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long l) {
                Txt.setVisibility(View.VISIBLE);
                Txt.setText("Resend Code within" + " " + l/1000 + " " + "Seconds");
            }

            @Override
            public void onFinish() {
                Resend.setVisibility(View.VISIBLE);
                Txt.setVisibility(View.INVISIBLE);
            }
        }.start();

        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resend.setVisibility(View.INVISIBLE);
                ResendOTP(PhoneNumber);
            }
        });

    }

    private void ResendOTP(String phoneNumber) {
        SendOTPShip(PhoneNumber);
    }

    private void SendOTPShip(String number) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FbAuth)
                        .setPhoneNumber(PhoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String Code = phoneAuthCredential.getSmsCode();
            if (Code != null){
                OTPCode.setText(Code);
                VerifyOTP(Code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ShipperGetOTP.this , e.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            super.onCodeSent(s, forceResendingToken);
            VerifyId = s;
        }
    };

    private void VerifyOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerifyId , code);
        ShipSIPhone(credential);
    }

    private void ShipSIPhone(PhoneAuthCredential credential) {

        FbAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(ShipperGetOTP.this, BottomNav_ShipFoodPanel.class));
                            finish();
                        }else{
                            ReusableCodeForAll.ShowAlert(ShipperGetOTP.this, "Error", task.getException().getMessage());
                        }

                    }
                });
    }
}