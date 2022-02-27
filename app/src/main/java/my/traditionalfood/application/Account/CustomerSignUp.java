package my.traditionalfood.application.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;

import my.traditionalfood.application.R;

public class CustomerSignUp extends AppCompatActivity {

    String[] HoChiMinh = {
            "District 1","District 2", "District 3", "District 4", "District 5", "District 6", "District 7", "District 8",
            "District 9", "District 10", "District 11", "District 12", "Tan Phu", "Tan Binh", "Binh Tan",
            "Binh Thanh", "Binh Chanh", "Thu Duc", "Phu Nhuan", "Cu Chi", "Hoc Mon", "Go Vap",
            "Nha Be", "Can Gio"
    };
    String[] HaNoi = {
            "Ba Dinh", "Bac Tu Liem", "cau Giay", "Dong Da", "Ha Dong", "Hai Ba Trung", "Hoan Kiem", "Hoang Mai", "Long Bien",
            "Nam Tu Lien", "Tay Ho", "Thanh Xuan", "Son Tay", "Ba Vi", "Chuong My", "Dan Phuong", "Dong Anh", "Gia Lam", "Hoai Duc",
            "Me Linh", "My Duc", "Phu Xuyen", "Phuc Tho", "Quoc Oai", "Soc Son", "Thach That", "Thoanh Oai", "Thanh Tri",
            "Thuong Tinh", "Ung Hoa"
    };
    String[] DaNang = {
            "Hai Chau", "Cam le", "Thanh Khe", "Lien Chieu", "Ngu Hanh Son", "Son Tra", "Hoa Vang", "Hoang Sa"
    };

    TextInputLayout Lname, Fname, Email, Password, ConfirmP, Mobile, Address;
    Spinner DistrictSpin, CitySpin;
    Button Signup, SigninE, SigninP;
    CountryCodePicker Ccp;
    FirebaseAuth FbAuth;
    DatabaseReference DBRef;
    FirebaseDatabase FbDb;
    String lname, fname, email, password, confirmp, mobile, address, district, city, role = "Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);

        Fname = (TextInputLayout)findViewById(R.id.FirstnameCus);
        Lname = (TextInputLayout)findViewById(R.id.LastnameCus);
        Email = (TextInputLayout)findViewById(R.id.EmailCus);
        Password = (TextInputLayout)findViewById(R.id.PasswordCus);
        ConfirmP = (TextInputLayout)findViewById(R.id.ConfirmPwCus);
        Mobile = (TextInputLayout)findViewById(R.id.PhoneNumberCus);
        Address = (TextInputLayout)findViewById(R.id.AddressCus);

        CitySpin = (Spinner) findViewById(R.id.CitySpinnerCus);
        DistrictSpin = (Spinner) findViewById(R.id.DistrictSpinnerCus);


        Signup = (Button)findViewById(R.id.SignpbtnCus);
        SigninE = (Button)findViewById(R.id.EmailSignInC);
        SigninP = (Button)findViewById(R.id.PhoneSignInC);

        Ccp = (CountryCodePicker)findViewById(R.id.CountryCodeCus);

        CitySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object value = adapterView.getItemAtPosition(position);
                city = value.toString().trim();
                if (city.equals("Ho Chi Minh")){
                    ArrayList<String> list = new ArrayList<>();
                    for (String districts : HoChiMinh){
                        list.add(districts);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CustomerSignUp.this, android.R.layout.simple_spinner_item,list);
                    DistrictSpin.setAdapter(arrayAdapter);
                }
                if (city.equals("Ha Noi")){
                    ArrayList<String> list = new ArrayList<>();
                    for (String districts : HaNoi){
                        list.add(districts);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CustomerSignUp.this, android.R.layout.simple_spinner_item,list);
                    DistrictSpin.setAdapter(arrayAdapter);
                }
                if (city.equals("Da Nang")){
                    ArrayList<String> list = new ArrayList<>();
                    for (String districts : DaNang){
                        list.add(districts);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CustomerSignUp.this, android.R.layout.simple_spinner_item,list);
                    DistrictSpin.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DistrictSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                district = value.toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DBRef = FbDb.getInstance().getReference("Customer");
        FbAuth = FirebaseAuth.getInstance();

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = Fname.getEditText().getText().toString().trim();
                lname = Lname.getEditText().getText().toString().trim();
                email = Email.getEditText().getText().toString().trim();
                mobile = Mobile.getEditText().getText().toString().trim();
                password = Password.getEditText().getText().toString().trim();
                confirmp = ConfirmP.getEditText().getText().toString().trim();
                address = Address.getEditText().getText().toString().trim();

                if (isValid()){
                    final ProgressDialog mDialog = new ProgressDialog(CustomerSignUp.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in progress...");
                    mDialog.show();

                    FbAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DBRef = FirebaseDatabase.getInstance().getReference("User").child(userid);
                                final HashMap<String , String> hashMap = new HashMap<>();
                                hashMap.put("Role",role);
                                DBRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        HashMap<String , String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Phone ",mobile);
                                        hashMap1.put("First Name",fname);
                                        hashMap1.put("Last Name",lname);
                                        hashMap1.put("Email",email);
                                        hashMap1.put("City",city);
                                        hashMap1.put("Password",password);
                                        hashMap1.put("District",district);
                                        hashMap1.put("Confirm Password", confirmp);
                                        hashMap1.put("Address", address);

                                        FbDb.getInstance().getReference("Customer")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();

                                                FbAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful()){
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerSignUp.this);
                                                            builder.setMessage("SignUp Successfully. Please verify your email!");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    dialog.dismiss();

                                                                    //Verify phone number
                                                                    String PhoneNumberCus = Ccp.getSelectedCountryCodeWithPlus() + mobile;
                                                                    Intent verify = new Intent(CustomerSignUp.this,VerifyPhoneCustomer.class);
                                                                    verify.putExtra("Phone Number Cus", PhoneNumberCus);
                                                                    startActivity(verify);

                                                                }
                                                            });
                                                            AlertDialog Alert = builder.create();
                                                            Alert.show();
                                                        }else{
                                                            mDialog.dismiss();
                                                            ReusableCodeForAll.ShowAlert(CustomerSignUp.this,"Error",task.getException().getMessage());
                                                        }
                                                    }
                                                });

                                            }
                                        });

                                    }
                                });
                            }else{
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(CustomerSignUp.this,"Error",task.getException().getMessage());
                            }
                        }
                    });
                }
//
            }
        });

        SigninE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerSignUp.this, CustomerSignInEmail.class));
                finish();
            }
        });

        SigninP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerSignUp.this, CustomerSignInPhone.class));
                finish();
            }
        });

    }

    String EmailValidate = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");
        Mobile.setErrorEnabled(false);
        Mobile.setError("");
        ConfirmP.setErrorEnabled(false);
        ConfirmP.setError("");
        Address.setErrorEnabled(false);
        Address.setError("");

        boolean isValid=false, isValidAddress=false,isValidLname=false,
                isValidFname=false,isValidEmail=false,isValidPassword=false,
                isValidConfirmP=false,isValidMobile=false;
        if(TextUtils.isEmpty(fname)){
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        }else{
            isValidFname = true;
        }
        if(TextUtils.isEmpty(lname)){
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        }else{
            isValidLname = true;
        }
        if(TextUtils.isEmpty(email)){
            Email.setErrorEnabled(true);
            Email.setError("Email Is Required");
        }else{
            if(email.matches(EmailValidate)){
                isValidEmail = true;
            }else{
                Email.setErrorEnabled(true);
                Email.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(password)){
            Password.setErrorEnabled(true);
            Password.setError("Enter Password");
        }else{
            if(password.length()<8){
                Password.setErrorEnabled(true);
                Password.setError("Password is Weak");
            }else{
                isValidPassword = true;
            }
        }
        if(TextUtils.isEmpty(confirmp)){
            ConfirmP.setErrorEnabled(true);
            ConfirmP.setError("Enter Password Again");
        }else{
            if(!password.equals(confirmp)){
                ConfirmP.setErrorEnabled(true);
                ConfirmP.setError("Password Doesn't Match");
            }else{
                isValidConfirmP = true;
            }
        }
        if(TextUtils.isEmpty(mobile)){
            Mobile.setErrorEnabled(true);
            Mobile.setError("Mobile Number Is Required");
        }else{
            if(mobile.length()<10){
                Mobile.setErrorEnabled(true);
                Mobile.setError("Invalid Mobile Number");
            }else{
                isValidMobile = true;
            }
        }
        if(TextUtils.isEmpty(address)){
            Address.setErrorEnabled(true);
            Address.setError("Fields Can't Be Empty");
        }else{
            isValidAddress = true;
        }

        isValid = (isValidConfirmP && isValidPassword && isValidEmail && isValidMobile && isValidFname && isValidAddress && isValidLname) ? true : false;
        return isValid;


    }
}