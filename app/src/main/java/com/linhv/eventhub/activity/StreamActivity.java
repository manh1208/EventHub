package com.linhv.eventhub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.linhv.eventhub.R;

public class StreamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Đặt câu hỏi");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ImageView imageView = (ImageView) findViewById(R.id.iv_animation_one);
        final ImageView imageView1 = (ImageView) findViewById(R.id.iv_animation_two);
        final ImageView imageView2 = (ImageView) findViewById(R.id.iv_animation_three);

        Button button = (Button) findViewById(R.id.btn_stream);

        imageView.startAnimation(getAnimation());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView1.startAnimation(getAnimation());
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView2.startAnimation(getAnimation());
            }
        }, 1000);


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    imageView.
//                }
//            }
//        }).start();
    }


    private AnimationSet getAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setRepeatMode(Animation.RESTART);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        return set;
    }

}
