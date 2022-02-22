package com.pahakaladze.quizmaker;

import android.os.Build;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class TestManager {
    private TestManager() {
    }

    public static boolean prepareQuizForTest(QuizList quizList) {
        if (quizList.size() == 0) return false;
        ArrayList<QuestionPage> filteredList = getFilteredList(quizList);
        if (filteredList.size() > 0) {
            QuizList.getInstance().setList(filteredList);
            return true;
        } else return false;
    }

    private static ArrayList<QuestionPage> getFilteredList(QuizList quizList) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return (ArrayList<QuestionPage>) quizList.getList().stream()
                    .distinct()
                    .filter(p -> !p.hasEmptyFields())
                    .collect(Collectors.toList());
        } else {
            Set<QuestionPage> uniqSet = new HashSet<QuestionPage>(quizList.getList());
            Iterator<QuestionPage> iterator = uniqSet.iterator();
            while (iterator.hasNext()) {
                QuestionPage page = iterator.next();
                if (page.hasEmptyFields()) iterator.remove();
            }
            return new ArrayList<QuestionPage>(uniqSet);
        }
    }
}
