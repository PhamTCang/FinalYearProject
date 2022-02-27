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

public class CustomerSignInPhone extends AppCompatActivity {

    EditText Phone;
    Button SendOTP, ChangeSIEmail;
    TextView ChangetoSU;
    CountryCodePicker Ccp;
    FirebaseAuth FbAuth;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_in_phone);

        Phone = (EditText) findViewById(R.id.PhoneNumberCus);
        SendOTP = (Button) findViewById(R.id.OTPCus);
        ChangeSIEmail = (Button) findViewById(R.id.ChangeSignInCus);
        ChangetoSU = (TextView) findViewById(R.id.SignUpLinkCus);
        Ccp = (CountryCodePicker) findViewById(R.id.CountryCodeCus);
        FbAuth = FirebaseAuth.getInstance();

        SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = Phone.getText().toString().trim();
                String cusphone = Ccp.getSelectedCountryCodeWithPlus() + phone;

                Intent sendOTP = new Intent(CustomerSignInPhone.this, CustomerGetOTP.class);
                sendOTP.putExtra("cusphone", cusphone);
                startActivity(sendOTP);
                finish();
            }
        });

        ChangetoSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerSignInPhone.this,CustomerSignUp.class));
                finish();
            }
        });

        ChangeSIEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerSignInPhone.this, CustomerSignInEmail.class));
                finish();
            }
        });

    }
}