package com.pahakaladze.quizmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

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
    private AdView adView;
    private TextView pageNumberView;
    private TextView questionView;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_test_quiz);
        getSupportActionBar().hide();
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        loadQuiz();
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (adView != null) {
            adView.pause();
        }
        super.onStop();
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
            Toast.makeText(this, R.string.empty_data, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        initializationOfFields();
        showPage(QuizList.getInstance().getCurrentPage());
    }

    private void showPage(QuestionPage page) {
        if (page.hasEmptyFields()) return;
        QuizList list = QuizList.getInstance();
        String text = getResources().getString(R.string.page_number) +
                (list.getCurrentPageIndex() + 1) +
                getResources().getString(R.string.from) +
                list.size();
        ArrayList<String> answers = page.getAnswers().getMixed();

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
            PopupMessage.showCorrectMark(btn, this);
            showPage(QuizList.getInstance().getNextPage());
        } else {
            PopupMessage.showIncorrectMark(btn, this);
            showPage(QuizList.getInstance().getNextPage());
        }
    }

    public void homePage(View view){
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void skipQuestion(View view){
        showPage(QuizList.getInstance().getNextPage());
    }

    public void closeApplication(View view){
        ActivityCompat.finishAffinity(this);
    }
}