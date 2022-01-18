package com.pahakaladze.quizmaker;

import java.io.Serializable;

public class QuestionPage implements Serializable {
    private String question;
    private Answers answers;


    public QuestionPage(String question, Answers answers) {
        this.question = question;
        this.answers = answers;
    }

    public static QuestionPage getEmptyPage(){
        return new QuestionPage("",new Answers());
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }

    public boolean hasEmptyFields(){
        return question.isEmpty() | answers.hasEmptyFields();

    }
}
