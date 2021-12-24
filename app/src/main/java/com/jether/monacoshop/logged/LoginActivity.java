package com.jether.monacoshop.logged;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jether.monacoshop.Activities.HairActivity;
import com.jether.monacoshop.Activities.MainActivity;
import com.jether.monacoshop.Prefrence.SharedPrefManager;
import com.jether.monacoshop.R;
import com.jether.monacoshop.Retrofit.ApiClient;
import com.jether.monacoshop.Retrofit.ApiInterface;
import com.jether.monacoshop.Retrofit.Users;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText em,pass;
    Button log;
    TextView next;


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
        setContentView(R.layout.activity_login);


        sharedPrefManager= new SharedPrefManager(this);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        em=findViewById(R.id.editTextTextPersonName);
        pass=findViewById(R.id.editTextTextPersonName3);
        log=findViewById(R.id.button2);
        next=findViewById(R.id.textView6);


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_mail=em.getText().toString().trim();
                String user_password=pass.getText().toString().trim();



                if(user_mail.equals("")){
                    new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Email is required..")
                            .show();
                }
                else if(user_password.equals("")){
                    new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Password is required..")
                            .show();
                }
                else{

                    ProgressDialog dialog=new ProgressDialog(LoginActivity.this);
                    dialog.setTitle("Loggin in...");
                    dialog.setMessage("Please wait while we check you credentials...");
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);

                    Call<Users> call= apiInterface.performLogin(user_mail,user_password);
                    call.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            Users users=response.body();

                            if(response.body().getResponse().equals("owner")){


                                user_id=response.body().getUserId();
                                user_namee=response.body().getUserName();
                                user_passs=response.body().getUserPass();
                                user_moo=response.body().getUserMobi();
                                user_emaill=response.body().getUserEmail();
                                user_loo=response.body().getUserLo();
                                sharedPrefManager.createSession(user_id,user_namee,user_emaill,user_moo,user_loo,user_passs);

                                Intent intent= new Intent(LoginActivity.this, BottomActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this,String.valueOf(response.body().getUserName()), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }

                            else if(response.body().getResponse().equals("no_account")){

                                Toast.makeText(LoginActivity.this, "No Account found..", Toast.LENGTH_SHORT).show();
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



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}