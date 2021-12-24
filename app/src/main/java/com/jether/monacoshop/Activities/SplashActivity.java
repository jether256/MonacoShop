package com.jether.monacoshop.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jether.monacoshop.R;
import com.scwang.wave.MultiWaveHeader;

public class SplashActivity extends AppCompatActivity {

    private ImageView facebook;
    private ImageView watsapp;

    MultiWaveHeader waveHeader,waveFooter;
    private static int SPLASH_TIMER = 5000;
    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        facebook=findViewById(R.id.imageView6);
        watsapp=findViewById(R.id.imageView7);
        waveHeader=findViewById(R.id.waveHeader);
        waveFooter=findViewById(R.id.wavefooter);

        waveHeader.setVelocity(1);
        waveHeader.setProgress(1);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(40);
        waveHeader.setStartColor(Color.RED);
        waveHeader.setCloseColor(Color.CYAN);

        waveFooter.setVelocity(1);
        waveFooter.setProgress(1);
        waveFooter.isRunning();
        waveFooter.setGradientAngle(45);
        waveFooter.setWaveHeight(40);
        waveFooter.setStartColor(Color.MAGENTA);
        waveFooter.setCloseColor(Color.YELLOW);

        watsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri= Uri.parse("smsto:" + "+256703956820");
                Intent i=new Intent(Intent.ACTION_SENDTO,uri);
                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Wirenti-Limited-103385778501165/?view_public_for=1033855778501165")));
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen=getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime=onBoardingScreen.getBoolean("firstTime",true);

                if(isFirstTime){

                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent= new Intent(getApplicationContext(), OnBoardActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }

//                Intent intent= new Intent(getApplicationContext(),OnBoardActivity.class);
//                startActivity(intent);
//                finish();

            }
        },SPLASH_TIMER);

    }
}