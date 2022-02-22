package com.pahakaladze.quizmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
    }

    public void startQuiz(View view){
        Intent intent = new Intent(this, TestQuizActivity.class);
        startActivity(intent);
    }

    public void startQuizMaker(View view){
        Intent intent = new Intent(this, QuizMakerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}