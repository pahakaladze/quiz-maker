package com.pahakaladze.quizmaker;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityController {
    private static AppCompatActivity appCompatActivity = MainActivity.getContext();
    private static StringBuilder pageNumberText = new StringBuilder();
    private static QuizList quizList;
    private static int currentPageIndex;
    private static int firstPageIndex;
    private static int lastPageIndex;

    private ActivityController() {

    }

    public static void activateElements() {
        initialization();
        if (quizList.size() == 0) return;
        activateNextAndPreviousButtons();
        activatePageNumberElement();
    }

    private static void initialization() {
        quizList = QuizList.getInstance();
        currentPageIndex = quizList.getCurrentPageIndex();
        lastPageIndex = quizList.getLastPageIndex();
    }

    private static void activateNextAndPreviousButtons() {
        Button nextBtn = (Button) appCompatActivity.findViewById(R.id.next);
        Button previousBtn = (Button) appCompatActivity.findViewById(R.id.previous);
        if (currentPageIndex > firstPageIndex) {
            previousBtn.setVisibility(View.VISIBLE);
        } else {
            previousBtn.setVisibility(View.INVISIBLE);
        }

        if (currentPageIndex < lastPageIndex) {
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
        pageNumberText.append((currentPageIndex + 1));
        TextView pageNumberElement = (TextView) appCompatActivity.findViewById(R.id.pageNumber);
        pageNumberElement.setText(pageNumberText);
    }
}
