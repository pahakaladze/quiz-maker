package com.pahakaladze.quizmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText question;
    private EditText correctAnswer;
    private EditText wrongAnswer;
    private EditText wrongAnswer2;
    private EditText wrongAnswer3;
    private static AppCompatActivity appCompatActivity;
    private QuizList quizList = QuizList.getInstance();
    private static QuestionPage viewedPage = QuestionPage.getEmptyPage();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        appCompatActivity = this;
        loadQuiz(this.getCurrentFocus());
    }

    public static AppCompatActivity getContext() {
        return appCompatActivity;
    }

    public static QuestionPage getViewedPage() {
        return viewedPage;
    }

    private void initializationOfFields() {
        question = findViewById(R.id.editQuestion);
        correctAnswer = findViewById(R.id.correctAnswer);
        wrongAnswer = findViewById(R.id.wrongAnswer);
        wrongAnswer2 = findViewById(R.id.wrongAnswer2);
        wrongAnswer3 = findViewById(R.id.wrongAnswer3);
        viewedPage.setQuestion(question.getText().toString());
        viewedPage.getAnswers().setCorrectAnswer(correctAnswer.getText().toString());
        viewedPage.getAnswers().setWrongAnswer(wrongAnswer.getText().toString());
        viewedPage.getAnswers().setWrongAnswer2(wrongAnswer2.getText().toString());
        viewedPage.getAnswers().setWrongAnswer3(wrongAnswer3.getText().toString());
    }

    public void newQuestion(View view) {
        initializationOfFields();
        if (viewedPage.hasEmptyFields() | quizList.size() >= 30) return;
        quizList.add(viewedPage);
        showPage(quizList.getCurrentPage());
    }

    public void loadQuiz(View view) {
        initializationOfFields();
        QuestionPage loadedPage = QuizLoader.loadFromFiles(this);
        showPage(loadedPage);
    }

    public void deletePage(View view){
        initializationOfFields();
        quizList.deleteCurrent();
        showPage(quizList.getCurrentPage());
    }

    public void next(View view) {
        initializationOfFields();
        quizList.refreshCurrent();
        showPage(quizList.getNextPage());
    }

    public void previous(View view) {
        initializationOfFields();
        quizList.refreshCurrent();
        showPage(quizList.getPreviousPage());
    }

    private void showPage(QuestionPage page) {
        question.setText(page.getQuestion());
        correctAnswer.setText(page.getAnswers().getCorrectAnswer());
        wrongAnswer.setText(page.getAnswers().getWrongAnswers().get(0));
        wrongAnswer2.setText(page.getAnswers().getWrongAnswers().get(1));
        wrongAnswer3.setText(page.getAnswers().getWrongAnswers().get(2));
        ActivityController.activateElements();
    }


}

//TODO scrolling in activitymain