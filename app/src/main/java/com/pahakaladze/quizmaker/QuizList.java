package com.pahakaladze.quizmaker;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizList implements Serializable {
    private volatile ArrayList<QuestionPage> list;
    private int currentPageIndex, lastPageIndex, firstPageIndex;
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
        if (lastPageIndex != currentPageIndex |
                this.getCurrentPage().equals(questionPage)) {
            Toast.makeText(MainActivity.getContext(), "Page not added", Toast.LENGTH_SHORT).show();
            return;
        }

        if (list.size() > 0 & this.getCurrentPage().hasEmptyFields()) {
            list.remove(currentPageIndex);
        }
        list.add(questionPage.makeInstance());
        list.add(QuestionPage.getEmptyPage());
        currentPageIndex = list.size() - 1;
        lastPageIndex = list.size() - 1;
        QuizLoader.saveToFiles(MainActivity.getContext());
    }

    public void refreshCurrent() {
        QuestionPage viewedPage = MainActivity.getViewedPage();
        QuestionPage currentPage = getCurrentPage();
        if (viewedPage.hasEmptyFields() | currentPage.equals(viewedPage)) {
            return;
        }
        currentPage.setQuestion(viewedPage.getQuestion());
        currentPage.setAnswers(viewedPage.getAnswers());

        if (currentPageIndex == lastPageIndex) {
            list.add(QuestionPage.getEmptyPage());
        }
        lastPageIndex = list.size() - 1;
        QuizLoader.saveToFiles(MainActivity.getContext());
    }

    public void deleteCurrent() {
        if (getCurrentPage().hasEmptyFields()) {
            return;
        }
        list.remove(currentPageIndex);
        lastPageIndex = list.size() - 1;
        QuizLoader.saveToFiles(MainActivity.getContext());
    }

    public QuestionPage getFirstPage() {
        if (this.size() > 0) {
            return this.list.get(firstPageIndex);
        } else
            return QuestionPage.getEmptyPage();
    }

    public int getFirstPageIndex() {
        return firstPageIndex;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getLastPageIndex() {
        return list.size() > 0 ? list.size() - 1 : 0;
    }

    public ArrayList<QuestionPage> getList() {
        return this.list;
    }

    public void setList(ArrayList<QuestionPage> sourceList) {
        if (sourceList == null) return;
        for (QuestionPage elementOfList : sourceList) {
            if (elementOfList == null) {
                return;
            }
        }
        this.list = new ArrayList<>(sourceList);
        firstPageIndex = 0;
        currentPageIndex = 0;
        lastPageIndex = list.size() - 1;
        if (MainActivity.getContext() != null) {
            ActivityController.activateElements();//executing only from MainActivity
        }
    }

    public QuestionPage getCurrentPage() {
        if (this.size() > 0) {
            return this.list.get(currentPageIndex);
        } else
            return QuestionPage.getEmptyPage();
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        if (currentPageIndex >= 0 || currentPageIndex <= lastPageIndex)
            this.currentPageIndex = currentPageIndex;
    }

    public QuestionPage getLastPage() {
        return this.list.get(this.size() - 1);
    }

    public void setLastPageIndex(int lastPageIndex) {
        if (this.size() > 0) {
            this.lastPageIndex = lastPageIndex;
        } else this.lastPageIndex = 0;
    }

    public int size() {
        return this.list.size();
    }

    public QuestionPage getNextPage() {
        int currentIndex = getCurrentPageIndex();
        int lastIndex = getLastPageIndex();
        if (lastIndex > currentIndex) {
            setCurrentPageIndex(++currentIndex);
            return this.list.get(currentIndex);
        } else return getCurrentPage();
    }

    public QuestionPage getPreviousPage() {
        int currentIndex = getCurrentPageIndex();
        int firstIndex = getFirstPageIndex();
        if (firstIndex < currentIndex) {
            setCurrentPageIndex(--currentIndex);
            return list.get(currentIndex);
        } else return getCurrentPage();
    }
}
