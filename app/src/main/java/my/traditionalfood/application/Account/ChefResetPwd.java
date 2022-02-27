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
import com.google.firebase.database.FirebaseDatabase;

import my.traditionalfood.application.R;

public class ChefResetPwd extends AppCompatActivity {

    TextInputLayout ChefEmail;
    Button ChefReset;
    FirebaseAuth FbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_reset_pwd);

        ChefEmail = (TextInputLayout) findViewById(R.id.ChefResetEmailId);
        ChefReset = (Button) findViewById(R.id.ChefResetBtn);
        FbAuth = FirebaseAuth.getInstance();

        ChefReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(ChefResetPwd.this);
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setMessage("Progressing...");
                mDialog.show();

                FbAuth.sendPasswordResetEmail(ChefEmail.getEditText().getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            ReusableCodeForAll.ShowAlert(ChefResetPwd.this, "", "Password has been sent to your Email");
                        } else {
                            mDialog.dismiss();
                            ReusableCodeForAll.ShowAlert(ChefResetPwd.this, "Error", task.getException().getMessage());
                        }
                    }
                });
            }
        });
    }
}