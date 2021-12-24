package com.jether.monacoshop.logged;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jether.monacoshop.Prefrence.SharedPrefManager;
import com.jether.monacoshop.R;
import com.jether.monacoshop.Retrofit.ApiClient;
import com.jether.monacoshop.Retrofit.ApiInterface;
import com.jether.monacoshop.Retrofit.Users;

import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ImageView img;
    EditText em,pass,mo,lo,name;
    Button log;
    TextView next;


    //permission constants

    private static final int CAMERA_REQUEST_CODE = 300;
    private static final int STORAGE_REQUEST_CODE = 200;

    private static final int REQUEST_CODE_CROP_IMAGE = 103;
    private Bitmap cameraBitmap;
    private Bitmap bitmap;

    //image pick constants
    private static final int PICK_IMAGE_GALLERY_CODE = 500;
    private static final int PICK_IMAGE_CAMERA_CODE = 400;

    //permission arrays
    private String[] locationPermissions;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    //image picked url
    private Uri image_uri;

    String user_id;
    String user_moo;
    String user_namee;
    String user_emaill;
    String user_passs;
    String user_loo;

    SharedPrefManager sharedPrefManager;

    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        //limit permission array

        cameraPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        sharedPrefManager= new SharedPrefManager(this);

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        img=findViewById(R.id.imageView3);
        em=findViewById(R.id.editTextTextPersonName3);
        pass=findViewById(R.id.editTextTextPersonName5);
        mo=findViewById(R.id.editTextTextPersonName2);
        lo=findViewById(R.id.editTextTextPersonName4);
        name=findViewById(R.id.editTextTextPersonName);
        log=findViewById(R.id.button2);
        next=findViewById(R.id.textView5);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pick image
                showImagePickDialog();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String user_full=name.getText().toString().trim();
                String user_mail=em.getText().toString().trim();
                String user_mobile=mo.getText().toString().trim();
                String user_password=pass.getText().toString().trim();
                String user_location=lo.getText().toString().trim();

                BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                Bitmap profileBitmap = drawable.getBitmap();

                Bitmap bitmap = null;

                if (cameraBitmap == null) {
                    bitmap = profileBitmap;
                } else {
                    bitmap = cameraBitmap;
                }

                final String image = getStringImage(bitmap);

                if(user_full.equals("")){
                    new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Name is required..")
                            .show();
                }
                else if(user_mail.equals("")){
                    new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Email is required..")
                            .show();
                }
                else if(user_mobile.equals("")){
                    new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Mobile Number is required..")
                            .show();
                }
                else if(user_password.equals("")) {
                    new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Password is required..")
                            .show();
                }
                else if(user_location.equals("")) {
                    new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Location is required..")
                            .show();
                }else{

                    ProgressDialog dialog=new ProgressDialog(RegisterActivity.this);
                    dialog.setTitle("Registering...");
                    dialog.setMessage("Please wait while we check you credentials...");
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);

                    Call<Users> call= apiInterface.performRegistration(user_full,user_mail,user_mobile,user_location,user_password,image);
                    call.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {

                            if(response.body().getResponse().equals("Ok")){

                                user_id=response.body().getUserId();
                                user_namee=response.body().getUserName();
                                user_passs=response.body().getUserPass();
                                user_moo=response.body().getUserMobi();
                                user_emaill=response.body().getUserEmail();
                                user_loo=response.body().getUserLo();
                                sharedPrefManager.createSession(user_id,user_namee,user_emaill,user_moo,user_loo,user_passs);


                                    Intent intent= new Intent(RegisterActivity.this, BottomActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(RegisterActivity.this,String.valueOf(response.body().getUserName()), Toast.LENGTH_SHORT).show();
                                Toast.makeText(RegisterActivity.this, "Registered successfully....", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                            }
                            else if(response.body().getResponse().equals("failed")){
                                Toast.makeText(RegisterActivity.this, "Something went wrong,Please try again....", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                            else if(response.body().getResponse().equals("already")){
                                Toast.makeText(RegisterActivity.this, "This Email already exists,Please try another....", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            dialog.dismiss();

                        }
                    });
                }



            }
        });

    }

    private void showImagePickDialog() {
        //options to display in dialog

        String[] options={"Camera","Gallery"};
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Pick Image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handle clicks
                        if(which==0){
                            //camera clicked

                            if(checkCameraPermission()){
                                //camera permission allowed
                                pickFromCamera();
                            }else{
                                //not allowed,request
                                requestCameraPermission();
                            }


                        }else{
                            //gallery clicked

                            if(checkStoragePermission()){
                                //storage permission allowed
                                pickFromGallery();
                            }else{
                                //not allowed,request
                                requestStoragePermission();
                            }

                        }
                    }
                })
                .show();
    }

    private void pickFromGallery(){
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/+");
        startActivityForResult(intent,PICK_IMAGE_GALLERY_CODE);
    }

    private void pickFromCamera(){
        ContentValues contentValues= new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp_Image Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Temp_Image Description");

        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(intent,PICK_IMAGE_CAMERA_CODE);
    }

    private boolean checkStoragePermission() {

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result=ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);

        boolean result1=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);
    }


    private void performCrop(Uri picUri) {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, REQUEST_CODE_CROP_IMAGE);
        } catch (ActivityNotFoundException anfe) {
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast
                    .makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        //permission allowed
                        pickFromCamera();
                    } else {

                        //permission denied

                        Toast.makeText(this, "Camera permissions are necessary...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if ( storageAccepted) {
                        //permission allowed
                        pickFromGallery();
                    } else {

                        //permission denied

                        Toast.makeText(this, "Storage permission is necessary...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==PICK_IMAGE_GALLERY_CODE){
                //get picked image
                image_uri=data.getData();
                //set to Image view
                img.setImageURI(image_uri);

            }else if(requestCode==PICK_IMAGE_CAMERA_CODE){

                //set to Image view
                img.setImageURI(image_uri);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
