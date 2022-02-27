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

public class ChefSignInPhone extends AppCompatActivity {

    EditText Phone;
    Button SendOTP, ChangeSIEmail;
    TextView ChangetoSU;
    CountryCodePicker Ccp;
    FirebaseAuth FbAuth;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_sign_in_phone);

        Phone = (EditText) findViewById(R.id.PhoneNumberCh);
        SendOTP = (Button) findViewById(R.id.OTPCh);
        ChangeSIEmail = (Button) findViewById(R.id.ChangeSignInCh);
        ChangetoSU = (TextView) findViewById(R.id.SignUpLinkCh);
        Ccp = (CountryCodePicker) findViewById(R.id.CountryCodeCh);
        FbAuth = FirebaseAuth.getInstance();

        SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = Phone.getText().toString().trim();
                String chefphone = Ccp.getSelectedCountryCodeWithPlus() + phone;

                Intent sendOTP = new Intent(ChefSignInPhone.this, ChefGetOTP.class);
                sendOTP.putExtra("chefphone", chefphone);
                startActivity(sendOTP);
                finish();
            }
        });

        ChangetoSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChefSignInPhone.this,ChefSignUp.class));
                finish();
            }
        });

        ChangeSIEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChefSignInPhone.this, ChefSignInEmail.class));
                finish();
            }
        });

    }
}