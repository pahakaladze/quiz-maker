package com.pahakaladze.quizmaker;

import java.io.Serializable;

public class QuestionPage implements Serializable {
    private String question;
    private Answers answers;


    public QuestionPage(String question, Answers answers) {
        this.question = question;
        this.answers = answers;
    }

    public static QuestionPage getEmptyPage() {
        return new QuestionPage("", new Answers.Builder()
                .addCorrectAnswer("")
                .addWrongAnswer("")
                .addWrongAnswer2("")
                .addWrongAnswer3("")
                .build());
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        try {
            if (question != null | !question.isEmpty() | !question.trim().isEmpty()) {
                this.question = question;
            } else return;
        } catch (NullPointerException e) {
            return;
        }
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        if(!answers.hasEmptyFields()){
            return;
        } else this.answers = answers;
    }

    public boolean hasEmptyFields() {
        return question.isEmpty() | answers.hasEmptyFields();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionPage that = (QuestionPage) o;

        return question != null ? question.equals(that.question) : that.question == null;
    }

    @Override
    public int hashCode() {
        return question != null ? question.hashCode() : 0;
    }
}
