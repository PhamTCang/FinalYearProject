package my.traditionalfood.application.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import my.traditionalfood.application.R;

public class ChooseOne extends AppCompatActivity {

    Button btnChef, btnCustomer, btnShipper;
    Intent intent;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);

        intent = getIntent();
        type = intent.getStringExtra("Home").toString().trim();

        btnChef = (Button) findViewById(R.id.btn_chef);
        btnCustomer = (Button) findViewById(R.id.btn_customer);
        btnShipper = (Button) findViewById(R.id.btn_shipper);

        btnChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Email")){
                    Intent SignInEmail = new Intent(ChooseOne.this,ChefSignInEmail.class);
                    startActivity(SignInEmail);
                    finish();

                }
                if (type.equals("Phone")){
                    Intent SignInPhone = new Intent(ChooseOne.this,ChefSignInPhone.class);
                    startActivity(SignInPhone);
                    finish();
                }
                if (type.equals("SignUp")){
                    Intent SignUp = new Intent(ChooseOne.this,ChefSignUp.class);
                    startActivity(SignUp);
                    finish();
                }
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Email")){
                    Intent SignInEmailCus = new Intent(ChooseOne.this,CustomerSignInEmail.class);
                    startActivity(SignInEmailCus);
                    finish();
                }
                if (type.equals("Phone")){
                    Intent SignInPhoneCus = new Intent(ChooseOne.this,CustomerSignInPhone.class);
                    startActivity(SignInPhoneCus);
                    finish();
                }
                if (type.equals("SignUp")){
                    Intent SignUpCus = new Intent(ChooseOne.this,CustomerSignUp.class);
                    startActivity(SignUpCus);
                    finish();
                }
            }
        });

        btnShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Email")){
                    Intent SignInEmailDe = new Intent(ChooseOne.this,ShipperSignInEmail.class);
                    startActivity(SignInEmailDe);
                    finish();
                }
                if (type.equals("Phone")){
                    Intent SignInPhoneDe = new Intent(ChooseOne.this,ShipperSignInPhone.class);
                    startActivity(SignInPhoneDe);
                    finish();
                }
                if (type.equals("SignUp")){
                    Intent SignUpDe = new Intent(ChooseOne.this,ShipperSignUp.class);
                    startActivity(SignUpDe);
                    finish();
                }
            }
        });

    }
}