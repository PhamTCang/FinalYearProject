package my.traditionalfood.application.ChefFunctions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import my.traditionalfood.application.Food.FoodInf;
import my.traditionalfood.application.R;

import java.util.UUID;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class ChefAddFood extends AppCompatActivity {

    ImageButton ImageBtn;
    TextInputLayout FoodName, Description, Quantity, Price;
    String foodname, description, quantity, price;
    Button AddFood;
    Uri ImgUri;
    private Uri PrivateImg;
    FirebaseStorage FbStore;
    StorageReference StoreRef;
    StorageReference Ref;
    FirebaseDatabase FbDb;
    DatabaseReference DbRefFood, DbRefChef;
    FirebaseAuth FbAuth;
    String ChefId, RandomUID, City, District;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_add_food);

        FoodName = (TextInputLayout)findViewById(R.id.foodname);
        Description = (TextInputLayout)findViewById(R.id.description);
        Quantity = (TextInputLayout)findViewById(R.id.quantity);
        Price = (TextInputLayout)findViewById(R.id.price);
        AddFood = (Button)findViewById(R.id.AddFoodBtn);

        FbStore = FirebaseStorage.getInstance();
        StoreRef = FbStore.getReference();
        FbAuth = FirebaseAuth.getInstance();
        DbRefFood = FbDb.getInstance().getReference("FoodInf");

        //
        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DbRefChef = FbDb.getInstance().getReference("Chef").child(userid);
            DbRefChef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ChefInf cheff = snapshot.getValue(ChefInf.class);
                    City = cheff.getCity();
                    District = cheff.getDistrict();
                    ImageBtn = (ImageButton) findViewById(R.id.picture_upload);

                    ImageBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSelectImageclick(v);
                        }
                    });
                    AddFood.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            foodname = FoodName.getEditText().getText().toString().trim();
                            description = Description.getEditText().getText().toString().trim();
                            quantity = Quantity.getEditText().getText().toString().trim();
                            price = Price.getEditText().getText().toString().trim();

                            if(isValid()){
                                UploadImage();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }

    }
    private void UploadImage() {


        if(ImgUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(ChefAddFood.this);
            progressDialog.setTitle("Uploading.....");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            Ref = StoreRef.child(RandomUID);
            ChefId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Ref.putFile(ImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            FoodInf info = new FoodInf(foodname,price,description,quantity,String.valueOf(uri),RandomUID,ChefId);
                            DbRefFood.child(ChefId).child(RandomUID)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    progressDialog.dismiss();
                                    Toast.makeText(ChefAddFood.this,"Dish Posted Successfully!",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ChefAddFood.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int) progress+"%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }

    }


    private boolean isValid() {

        Description.setErrorEnabled(false);
        Description.setError("");
        FoodName.setErrorEnabled(false);
        FoodName.setError("");
        Quantity.setErrorEnabled(false);
        Quantity.setError("");
        Price.setErrorEnabled(false);
        Price.setError("");

        boolean isValid = false, isValidDescription = false, isValidFoodName = false, isValidQuantity = false, isValidPrice = false;
        if(TextUtils.isEmpty(foodname)) {
            FoodName.setErrorEnabled(true);
            FoodName.setError("Food's name can not be empty");
        }else{
            isValidFoodName = true;
        }

        if(TextUtils.isEmpty(description)) {
            Description.setErrorEnabled(true);
            Description.setError("Please have a few words about your food");
        }else{
            Description.setError(null);
            isValidDescription = true;
        }

        if(TextUtils.isEmpty(quantity)) {
            Quantity.setErrorEnabled(true);
            Quantity.setError("Can you tell us many dishes are available at the moment?");
        }else{
            isValidQuantity = true;
        }

        if(TextUtils.isEmpty(price)) {
            Price.setErrorEnabled(true);
            Price.setError("How much for a dish?");
        }else{
            isValidPrice = true;
        }

        isValid  = (isValidDescription && isValidFoodName && isValidQuantity && isValidPrice) ? true: false;
        return isValid;
    }

    private void startCropImageActivity(Uri ImgUri){
        CropImage.activity(ImgUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void onSelectImageclick(View v){
        CropImage.startPickImageActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PrivateImg != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(PrivateImg);
        } else {
            Toast.makeText(this, "Cancelling! Permission Not Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            ImgUri = CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,ImgUri)){
                PrivateImg = ImgUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else{
                startCropImageActivity(ImgUri);
            }
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                ((ImageButton) findViewById(R.id.picture_upload)).setImageURI(result.getUri());
                Toast.makeText(this,"Cropped Successfully!"+result.getSampleSize(),Toast.LENGTH_SHORT).show();
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(this,"Failed To Crop"+result.getError(),Toast.LENGTH_SHORT).show();

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}