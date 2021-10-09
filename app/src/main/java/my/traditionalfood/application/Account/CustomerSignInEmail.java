package my.traditionalfood.application.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import my.traditionalfood.application.CusFoodPanel.BottomNav_CusFoodPanel;
import my.traditionalfood.application.R;

public class CustomerSignInEmail extends AppCompatActivity {

    TextInputLayout Email, Password;
    Button SignInBtn, ChangeSIPhone;
    TextView ChangetoSU, ResetPwd;
    FirebaseAuth FbAuth;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_in_email);
        try {

            Email = (TextInputLayout) findViewById(R.id.SignInEmailCus);
            Password = (TextInputLayout) findViewById(R.id.SignInPwCus);
            SignInBtn = (Button) findViewById(R.id.SignInBtnCus);
            ChangeSIPhone = (Button) findViewById(R.id.ChangeSignInCus);
            ResetPwd = (TextView) findViewById(R.id.ForgotPwCus);
            ChangetoSU = (TextView) findViewById(R.id.SignUpLinkCus);
            FbAuth = FirebaseAuth.getInstance();

            SignInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    email = Email.getEditText().getText().toString().trim();
                    password = Password.getEditText().getText().toString().trim();

                    if(isValid()){

                        final ProgressDialog mDialog = new ProgressDialog(CustomerSignInEmail.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Signing In Please Wait...");
                        mDialog.show();

                        FbAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    mDialog.dismiss();

                                    if(FbAuth.getCurrentUser().isEmailVerified()){
                                        mDialog.dismiss();
                                        Toast.makeText(CustomerSignInEmail.this, "Congratulation! You Have Successfully Login", Toast.LENGTH_SHORT).show();
                                        Intent Z = new Intent(CustomerSignInEmail.this, BottomNav_CusFoodPanel.class);
                                        startActivity(Z);
                                        finish();

                                    }else{
                                        ReusableCodeForAll.ShowAlert(CustomerSignInEmail.this,"Verification Failed","You Have Not Verified Your Email");

                                    }
                                }else{
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(CustomerSignInEmail.this,"Error",task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });

            ChangetoSU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CustomerSignInEmail.this,CustomerSignUp.class));
                    finish();
                }
            });

            ResetPwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CustomerSignInEmail.this, CustomerResetPwd.class));
                    finish();
                }
            });

            ChangeSIPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CustomerSignInEmail.this, CustomerSignInPhone.class));
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    String EmailValidate = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");

        boolean isValid = false, isValidEmail = false, isValidPassword = false;
        if (TextUtils.isEmpty(email)){
            Email.setErrorEnabled(true);
            Email.setError("Please enter your email!");
        }else{
            if (email.matches(EmailValidate)){
                isValidEmail = true;
            }else{
                Email.setErrorEnabled(true);
                Email.setError("Invalid Email, please try again!");
            }
        }

        if (TextUtils.isEmpty(password)){
            Password.setErrorEnabled(true);
            Password.setError("Please enter your password");
        }else{
            isValidPassword=true;
        }

        isValid = (isValidEmail && isValidPassword) ? true : false;
        return isValid;
    }
}