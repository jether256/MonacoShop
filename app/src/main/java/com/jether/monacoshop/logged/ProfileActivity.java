package com.jether.monacoshop.logged;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.jether.monacoshop.Activities.MainActivity;
import com.jether.monacoshop.Prefrence.SharedPrefManager;
import com.jether.monacoshop.R;

public class ProfileActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPrefManager = new SharedPrefManager(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!sharedPrefManager.isLogin()) {
            sharedPrefManager.editor.clear();
            sharedPrefManager.editor.commit();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        }else{

        }
    }
}