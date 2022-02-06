package com.pahakaladze.quizmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class TestQuizActivity extends AppCompatActivity {
    private AdView mAdView;
    private TextView pageNumberView;
    private TextView questionView;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_quiz);
        getSupportActionBar().hide();
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        loadQuiz();
    }

    private void initializationOfFields() {
        pageNumberView = findViewById(R.id.pageNumber);
        questionView = findViewById(R.id.question);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);
    }

    private void loadQuiz() {
        QuestionPage page = QuizLoader.loadFromFiles(this);
        if (!TestManager.prepareQuizForTest(QuizList.getInstance())) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            Toast.makeText(this, "Your Quiz has empty data", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        initializationOfFields();
        showPage(QuizList.getInstance().getCurrentPage());
    }

    private void showPage(QuestionPage page) {
        if (page.hasEmptyFields()) return;
        QuizList list = QuizList.getInstance();
        String text = "Page " + (list.getCurrentPageIndex() + 1) + " of " + list.size();
        ArrayList<String> answers = page.getAnswers().getMixedAnswers();

        questionView.setText(page.getQuestion());
        pageNumberView.setText(text);
        answerBtn1.setText(answers.get(0));
        answerBtn2.setText(answers.get(1));
        answerBtn3.setText(answers.get(2));
        answerBtn4.setText(answers.get(3));
    }

    public void answerButtonClicked(View view) {
        Button btn = findViewById(view.getId());
        String correct = QuizList.getInstance().getCurrentPage().getAnswers().getCorrectAnswer();
        if (btn.getText().equals(correct)) {
            Toast.makeText(this, "Yo Yo Yo", Toast.LENGTH_SHORT).show();
        }
    }

}