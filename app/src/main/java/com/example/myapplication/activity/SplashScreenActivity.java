package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.myapplication.R;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView view = findViewById(R.id.titleImageView);

        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide);
        slideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SpringAnimation jerkAnimation = new SpringAnimation(view, SpringAnimation.X);
                SpringForce spring = new SpringForce();
                spring.setFinalPosition(430);
                spring.setStiffness(SpringForce.STIFFNESS_LOW);
                spring.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
                jerkAnimation.setSpring(spring);
                jerkAnimation.addEndListener((animation1, canceled, value, velocity) -> {
                    Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                    startActivity(intent);
                    finish();
                });
                jerkAnimation.animateToFinalPosition(400);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(slideAnimation);

//        try {
//            new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... voids) {
////                    new Global(getApplicationContext());
////                    Global.loadDatabase();
//                    return null;
//                }
//            }.execute().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}