package my.traditionalfood.application.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import my.traditionalfood.application.R;

public class CustomerResetPwd extends AppCompatActivity {

    TextInputLayout CusEmail;
    Button CustomerReset;
    FirebaseAuth FbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reset_pwd);

        CusEmail = (TextInputLayout) findViewById(R.id.ChefResetEmailId);
        CustomerReset = (Button) findViewById(R.id.ChefResetBtn);
        FbAuth = FirebaseAuth.getInstance();

        CustomerReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(CustomerResetPwd.this);
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setMessage("Progressing...");
                mDialog.show();

                FbAuth.sendPasswordResetEmail(CusEmail.getEditText().getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(CustomerResetPwd.this, "", "Password has been sent to your Email");
                                } else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(CustomerResetPwd.this, "Error", task.getException().getMessage());
                                }
                            }
                        });
            }
        });
    }
}