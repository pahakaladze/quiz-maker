package com.pahakaladze.quizmaker;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizList implements Serializable {
    private volatile ArrayList<QuestionPage> list;
    private int currentPage, lastPage, firstPage;
    private static QuizList instance;

    private QuizList() {
        list = new ArrayList<QuestionPage>();
    }

    public static synchronized QuizList getInstance() {
        if (instance == null) {
            instance = new QuizList();
        }
        return instance;
    }

    public void add(QuestionPage questionPage) {
        if (lastPage != currentPage | this.size() >= 30) { //max quantity of questions in quiz
            Toast.makeText(MainActivity.getContext(), "Page not added", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentPage != 0 & this.getCurrentPage().hasEmptyFields()) {
            list.remove(currentPage);
        }
        list.add(questionPage.makeInstance());
        list.add(QuestionPage.getEmptyPage());
        currentPage = list.size() - 1;
        lastPage = list.size() - 1;
        ActivityController.activateElements();
    }

    public void refreshCurrent() {
        QuestionPage viewedPage = MainActivity.getViewedPage();

        if (viewedPage.hasEmptyFields() | this.getCurrentPage().equals(viewedPage)) {
            return;
        }
        list.add(currentPage, viewedPage.makeInstance());
        list.remove(currentPage + 1);
        ActivityController.activateElements();
    }

    public QuestionPage getFirstPage() {
        if (this.size() > 0) {
            return this.list.get(firstPage);
        } else
            return QuestionPage.getEmptyPage();
    }

    public ArrayList<QuestionPage> getList() {
        return this.list;
    }

    public void setList(ArrayList<QuestionPage> sourceList) {
        if (sourceList == null | sourceList.size() > 30) return;
        for (QuestionPage elementOfList : sourceList) {
            if (elementOfList == null | elementOfList.hasEmptyFields()) {
                return;
            }
        }
        this.list = new ArrayList<>(sourceList);
        firstPage = 0;
        currentPage = 0;
        lastPage = list.size() - 1;
        ActivityController.activateElements();
    }

    public QuestionPage getCurrentPage() {
        if (this.size() > 0) {
            return this.list.get(currentPage);
        } else
            return QuestionPage.getEmptyPage();
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage >= 0 || currentPage <= lastPage)
            this.currentPage = currentPage;
        ActivityController.activateElements();
    }

    public QuestionPage getLastPage() {
        return this.list.get(this.size() - 1);
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int size() {
        return this.list.size();
    }

    public QuestionPage getNextPage() {
        int currentIndex = list.indexOf(getCurrentPage());
        int lastIndex = this.list.indexOf(getLastPage());
        if (lastIndex > currentIndex) {
            setCurrentPage(++currentIndex);
            return this.list.get(currentIndex);
        } else return getCurrentPage();
    }

    public QuestionPage getPreviousPage() {
        int currentIndex = this.list.indexOf(getCurrentPage());
        int firstIndex = this.list.indexOf(getFirstPage());
        if (firstIndex < currentIndex) {
            setCurrentPage(--currentIndex);
            return list.get(currentIndex);
        } else return getCurrentPage();
    }
}
