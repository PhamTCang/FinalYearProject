package my.traditionalfood.application.Food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

import my.traditionalfood.application.ChefFoodPanel.BottomNav_ChefFoodPanel;
import my.traditionalfood.application.ChefFunctions.ChefInf;
import my.traditionalfood.application.R;

public class EditFood extends AppCompatActivity {

    TextInputLayout EditFName, EditDes, EditQua, EditPrice;
    ImageButton imgButton;
    Uri imgUri;
    String DbUri;
    private Uri mCropImgUri;
    Button EditBtn, DeleteBtn;
    String editFName, editDes, editQuantity, editPrice, chefId;
    String RandomUID;
    StorageReference StoreRef, StoreRef2;
    FirebaseStorage FbStore;
    FirebaseDatabase FbDb;
    DatabaseReference DbRefFood, DbRefChef;
    FirebaseAuth FbAuth;
    String ID;
    private ProgressDialog progressDialog;
    String City, District;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        EditFName = (TextInputLayout) findViewById(R.id.edit_foodname);
        EditDes = (TextInputLayout) findViewById(R.id.edit_description);
        EditQua = (TextInputLayout) findViewById(R.id.edit_quantity);
        EditPrice = (TextInputLayout) findViewById(R.id.edit_price);
        imgButton = (ImageButton) findViewById(R.id.picture_edit);
        EditBtn = (Button) findViewById(R.id.UpdateFoodBtn);
        DeleteBtn = (Button) findViewById(R.id.DeleteFoodBtn);
        ID = getIntent().getStringExtra("EditFood");

        String UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DbRefChef = FirebaseDatabase.getInstance().getReference("Chef").child(UserId);
        DbRefChef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ChefInf chefInf = snapshot.getValue(ChefInf.class);
                City = chefInf.getCity();
                District = chefInf.getDistrict();

                EditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editFName = EditFName.getEditText().getText().toString().trim();
                        editDes = EditDes.getEditText().getText().toString().trim();
                        editQuantity = EditQua.getEditText().getText().toString().trim();
                        editPrice = EditPrice.getEditText().getText().toString().trim();

                        if(isValid()) {
                            if(imgUri != null) {
                                uploadImg();
                            }else{
                                updateDes(DbUri);
                            }
                        }
                    }
                });

                DeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditFood.this);
                        builder.setMessage("Are you sure want to remove this food?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FbDb.getInstance().getReference("FoodInf").child(City).child(District)
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID).removeValue();
                                AlertDialog.Builder food = new AlertDialog.Builder(EditFood.this);
                                food.setMessage("This Food Has Been Removed From Your List!");
                                food.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(EditFood.this, BottomNav_ChefFoodPanel.class));
                                    }
                                });
                                AlertDialog alert = food.create();
                                alert.show();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                progressDialog = new ProgressDialog(EditFood.this);
                DbRefFood = FirebaseDatabase.getInstance().getReference("FoodInf").child(userId).child(ID);
                DbRefFood.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FoodMenu foodMenu = snapshot.getValue(FoodMenu.class);
                        EditDes.getEditText().setText(foodMenu.getDescription());
                        EditQua.getEditText().setText(foodMenu.getQuantity());
                        EditFName.getEditText().setText(foodMenu.getFood());
                        EditPrice.getEditText().setText(foodMenu.getPrice());
                        Glide.with(EditFood.this).load(foodMenu.getImageURL()).into(imgButton);
                        DbUri = foodMenu.getImageURL();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FbAuth = FirebaseAuth.getInstance();
                DbRefFood = FbDb.getInstance().getReference("FoodInf");
                FbStore = FirebaseStorage.getInstance();
                StoreRef = FbStore.getReference();
                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSelectImageclick(v);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void onSelectImageclick(View v) {

        CropImage.startPickImageActivity(this);
    }

    private void updateDes(String dbUri) {

        chefId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FoodInf info = new FoodInf(editFName,editQuantity,editPrice,editDes,dbUri,ID,chefId);
        FbDb.getInstance().getReference("FoodInf").child(City).child(District)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID)
                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressDialog.dismiss();
                Toast.makeText(EditFood.this,"Update Food Successfully!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImg() {

        if(imgUri != null) {
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            StoreRef2 = StoreRef.child(RandomUID);
            StoreRef2.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StoreRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            updateDes(String.valueOf(uri));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(EditFood.this, "Failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Upload "+(int) progress+"%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }
    }

    private boolean isValid() {
        EditFName.setErrorEnabled(false);
        EditFName.setError("");
        EditDes.setErrorEnabled(false);
        EditDes.setError("");
        EditQua.setErrorEnabled(false);
        EditQua.setError("");
        EditPrice.setErrorEnabled(false);
        EditPrice.setError("");

        boolean isValid = false, isValidDescription = false, isValidFoodName = false,
                isValidQuantity = false, isValidPrice = false;
        if(TextUtils.isEmpty(editFName)) {
            EditFName.setErrorEnabled(true);
            EditFName.setError("Food's name can not be empty");
        }else{
            isValidFoodName = true;
        }

        if(TextUtils.isEmpty(editDes)) {
            EditDes.setErrorEnabled(true);
            EditDes.setError("Please have a few words about your food");
        }else{
            EditDes.setError(null);
            isValidDescription = true;
        }

        if(TextUtils.isEmpty(editQuantity)) {
            EditQua.setErrorEnabled(true);
            EditQua.setError("Can you tell us many dishes are available at the moment?");
        }else{
            isValidQuantity = true;
        }

        if(TextUtils.isEmpty(editPrice)) {
            EditPrice.setErrorEnabled(true);
            EditPrice.setError("How much for a dish?");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mCropImgUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(mCropImgUri);
        } else {
            Toast.makeText(this, "Cancelling! Permission Not Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            imgUri = CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imgUri)){
                mCropImgUri = imgUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else{
                startCropImageActivity(imgUri);
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