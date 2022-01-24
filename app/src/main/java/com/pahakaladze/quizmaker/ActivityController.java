package com.pahakaladze.quizmaker;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityController {
    private static AppCompatActivity appCompatActivity = MainActivity.getContext();
    private static StringBuilder pageNumberText = new StringBuilder();
    private static QuizList quizList;
    private static int listSize;
    private static int currentPage;
    private static int firstPage;
    private static int lastPage;

    private ActivityController() {

    }

    public static void activateElements() {
        initialization();
        if (listSize == 0) return;
        activateNextAndPreviousButtons();
        activatePageNumberElement();
        activateNewQuestionButton();
    }

    private static void initialization() {
        quizList = QuizList.getInstance();
        listSize = quizList.getList().size();
        currentPage = quizList.getList().indexOf(quizList.getCurrentPage());
        firstPage = quizList.getList().indexOf(quizList.getFirstPage());
        lastPage = quizList.getList().indexOf(quizList.getLastPage());
    }

    private static void activateNextAndPreviousButtons(){
        Button nextBtn = (Button) appCompatActivity.findViewById(R.id.next);
        Button previousBtn = (Button) appCompatActivity.findViewById(R.id.previous);
        if (currentPage > firstPage) {
            previousBtn.setVisibility(View.VISIBLE);
        } else {
            previousBtn.setVisibility(View.INVISIBLE);
        }

        if (currentPage < lastPage) {
            nextBtn.setVisibility(View.VISIBLE);
        } else {
            nextBtn.setVisibility(View.INVISIBLE);
        }
    }

    private static void activatePageNumberElement() {
        if (pageNumberText.length() > 0) {
            pageNumberText.delete(0, pageNumberText.length());
        }
        pageNumberText.append(appCompatActivity.getResources().getString(R.string.page_number));
        pageNumberText.append((currentPage + 1));
        TextView pageNumberElement = (TextView) appCompatActivity.findViewById(R.id.pageNumber);
        pageNumberElement.setText(pageNumberText);
    }

    private static void activateNewQuestionButton(){
            Button newQuestionBtn = (Button) appCompatActivity.findViewById(R.id.newQuestion);
        if(currentPage == lastPage && !quizList.getCurrentPage().hasEmptyFields()){
            newQuestionBtn.setVisibility(View.VISIBLE);
        }
        else newQuestionBtn.setVisibility(View.INVISIBLE);
    }
}
