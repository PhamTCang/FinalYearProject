package my.traditionalfood.application.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import my.traditionalfood.application.R;

public class MainMenu extends AppCompatActivity {

    Button btnEmail, btnPhone, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnEmail=(Button)findViewById(R.id.menu_btn_email);
        btnPhone=(Button)findViewById(R.id.menu_btn_phone);
        btnSignUp=(Button)findViewById(R.id.menu_btn_signup);

        btnEmail.setOnClickListener(view -> {
            Intent btnEmail = new Intent(MainMenu.this,ChooseOne.class);
            btnEmail.putExtra("Home","Email");
            startActivity(btnEmail);
            finish();
        });

        btnPhone.setOnClickListener(view -> {
            Intent btnPhone = new Intent(MainMenu.this,ChooseOne.class);
            btnPhone.putExtra("Home", "Phone");
            startActivity(btnPhone);
            finish();
        });

        btnSignUp.setOnClickListener(view -> {
            Intent btnSignUp = new Intent(MainMenu.this,ChooseOne.class);
            btnSignUp.putExtra("Home", "SignUp");
            startActivity(btnSignUp);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
