package my.traditionalfood.application.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

import my.traditionalfood.application.R;

public class ShipperSignInPhone extends AppCompatActivity {

    EditText Phone;
    Button SendOTP, ChangeSIEmail;
    TextView ChangetoSU;
    CountryCodePicker Ccp;
    FirebaseAuth FbAuth;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_sign_in_phone);

        Phone = (EditText) findViewById(R.id.PhoneNumberShip);
        SendOTP = (Button) findViewById(R.id.OTPSh);
        ChangeSIEmail = (Button) findViewById(R.id.ChangeSignInSh);
        ChangetoSU = (TextView) findViewById(R.id.SignUpLinkSh);
        Ccp = (CountryCodePicker) findViewById(R.id.CountryCodeShip);
        FbAuth = FirebaseAuth.getInstance();

        SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = Phone.getText().toString().trim();
                String shipphone = Ccp.getSelectedCountryCodeWithPlus() + phone;

                Intent sendOTP = new Intent(ShipperSignInPhone.this, ShipperGetOTP.class);
                sendOTP.putExtra("shipphone", shipphone);
                startActivity(sendOTP);
                finish();
            }
        });

        ChangetoSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShipperSignInPhone.this,ShipperSignUp.class));
                finish();
            }
        });

        ChangeSIEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShipperSignInPhone.this, ShipperSignInEmail.class));
                finish();
            }
        });

    }
}