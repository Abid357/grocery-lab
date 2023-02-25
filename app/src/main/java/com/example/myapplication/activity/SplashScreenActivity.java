package com.example.myapplication.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.core.BackgroundTask;
import com.example.myapplication.core.Database;

import java.util.concurrent.ExecutionException;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        RelativeLayout splashLayout = findViewById(R.id.splashLayout);
        splashLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                splashLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = splashLayout.getWidth();

                ImageView cartEmpty = findViewById(R.id.cartEmptyImageView);
                ImageView cartFull = findViewById(R.id.cartFullImageView);

                cartEmpty.setX(0f - cartEmpty.getWidth() / 2f);
                cartEmpty.setVisibility(View.VISIBLE);
                cartEmpty.animate()
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000)
                        .translationX(width / 2f - cartEmpty.getWidth() / 2f)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(@NonNull Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(@NonNull Animator animator) {
                                cartEmpty.setVisibility(View.INVISIBLE);
                                cartFull.setVisibility(View.VISIBLE);
                                cartFull.setX(width / 2f - cartEmpty.getWidth() / 2f);
                                cartFull.animate()
                                        .setInterpolator(new LinearInterpolator())
                                        .setDuration(2000)
                                        .translationX(width + cartEmpty.getWidth() / 2f)
                                        .setListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(@NonNull Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationEnd(@NonNull Animator animator) {
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onAnimationCancel(@NonNull Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(@NonNull Animator animator) {

                                            }
                                        });
                            }

                            @Override
                            public void onAnimationCancel(@NonNull Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(@NonNull Animator animator) {

                            }
                        });
            }
        });

        try {
            new BackgroundTask(this) {
                @Override
                public void doInBackground() {
                    Database.withContext(getActivity()).getProductList();
                    Database.withContext(getActivity()).getBrandList();
                    Database.withContext(getActivity()).getRecordList();
                }

                @Override
                public void onPostExecute() {
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}