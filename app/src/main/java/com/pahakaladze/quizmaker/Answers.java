package com.pahakaladze.quizmaker;

import java.io.Serializable;
import java.util.ArrayList;

public class Answers implements Serializable {
    private String correctAnswer;
    private String wrongAnswer;
    private String wrongAnswer2;
    private String wrongAnswer3;

    //pattern builder
    public static class Builder {
        private Answers newAnswers;

        public Builder() {
            newAnswers = new Answers();
        }

        public Builder addCorrectAnswer(String correctAnswer) {
            newAnswers.correctAnswer = correctAnswer;
            return this;
        }

        public Builder addWrongAnswer(String wrongAnswer) {
            newAnswers.wrongAnswer = wrongAnswer;
            return this;
        }

        public Builder addWrongAnswer2(String wrongAnswer2) {
            newAnswers.wrongAnswer2 = wrongAnswer2;
            return this;
        }

        public Builder addWrongAnswer3(String wrongAnswer3) {
            newAnswers.wrongAnswer3 = wrongAnswer3;
            return this;
        }

        public Answers build() {
            return newAnswers;
        }
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getWrongAnswers() {
        ArrayList<String> list = new ArrayList<>(3);
        list.add(wrongAnswer);
        list.add(wrongAnswer2);
        list.add(wrongAnswer3);
        return list;
    }

    public boolean hasEmptyFields() {
        return isNullOrEmpty(correctAnswer)
                | isNullOrEmpty(wrongAnswer)
                | isNullOrEmpty(wrongAnswer2)
                | isNullOrEmpty(wrongAnswer3);
    }

    private static boolean isNullOrEmpty(String text) {

        try {
            return text == null | text.isEmpty() | text.trim().isEmpty();
        } catch (NullPointerException e) {
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answers answers = (Answers) o;

        if (correctAnswer != null ? !correctAnswer.equals(answers.correctAnswer) : answers.correctAnswer != null)
            return false;
        if (wrongAnswer != null ? !wrongAnswer.equals(answers.wrongAnswer) : answers.wrongAnswer != null)
            return false;
        if (wrongAnswer2 != null ? !wrongAnswer2.equals(answers.wrongAnswer2) : answers.wrongAnswer2 != null)
            return false;
        return wrongAnswer3 != null ? wrongAnswer3.equals(answers.wrongAnswer3) : answers.wrongAnswer3 == null;
    }

    @Override
    public int hashCode() {
        int result = correctAnswer != null ? correctAnswer.hashCode() : 0;
        result = 31 * result + (wrongAnswer != null ? wrongAnswer.hashCode() : 0);
        result = 31 * result + (wrongAnswer2 != null ? wrongAnswer2.hashCode() : 0);
        result = 31 * result + (wrongAnswer3 != null ? wrongAnswer3.hashCode() : 0);
        return result;
    }

}
