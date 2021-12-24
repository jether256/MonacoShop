package com.jether.monacoshop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jether.monacoshop.Adapters.SliderAdapter;
import com.jether.monacoshop.R;

public class OnBoardActivity extends AppCompatActivity {


    ViewPager viewPager;
    LinearLayout dotsLay;

    Button btn;
    SliderAdapter sliderAdapter;

    TextView[] dots;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_board);

        viewPager=findViewById(R.id.slider);
        dotsLay=findViewById(R.id.dots);
        btn=findViewById(R.id.get_started_btn);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

        //call adapter 
        sliderAdapter= new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(OnBoardActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void addDots(int position) {
        dots=new TextView[3];
        dotsLay.removeAllViews();

        for(int i = 0; i<dots.length; i++){
            dots[i]= new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dotsLay.addView(dots[i]);

        }

        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.purple_200));
        }
    }

    ViewPager.OnPageChangeListener changeListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            if(position==0){
                btn.setVisibility(View.INVISIBLE);
            }
            else if(position==1){
                btn.setVisibility(View.INVISIBLE);
            }
            else {
                animation= AnimationUtils.loadAnimation(OnBoardActivity.this,R.anim.slide_animation);
                btn.setAnimation(animation);
                btn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}