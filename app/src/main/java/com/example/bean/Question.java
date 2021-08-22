package com.example.bean;

import java.io.Serializable;

public class Question   implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String title;

    private int answerNum = 0;
    private String choiceA = "";
    private String choiceB = "";
    private String choiceC = "";
    private String choiceD = "";
    private String choiceE = "";
    private String choiceF = "";
    private String answer = "";
    private String preQuestion = "";
    private String NextQuestion_A = "";
    private String NextQuestion_B = "";
    private String NextQuestion_C = "";
    private String NextQuestion_D = "";
    private String NextQuestion_E = "";
    private String NextQuestion_F = "";
    private boolean isAnswerCorrect = false;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public String getChoiceE() {
        return choiceE;
    }

    public void setChoiceE(String choiceE) {
        this.choiceE = choiceE;
    }

    public String getChoiceF() {
        return choiceF;
    }

    public void setChoiceF(String choiceF) {
        this.choiceF = choiceF;
    }


    public boolean isAnswerCorrect() {
        return isAnswerCorrect;
    }

    public void setAnswerCorrect(boolean answerCorrect) {
        isAnswerCorrect = answerCorrect;
    }

    public String getPreQuestion() {
        return preQuestion;
    }

    public void setPreQuestion(String preQuestion) {
        this.preQuestion = preQuestion;
    }

    public String getNextQuestion_A() {
        return NextQuestion_A;
    }

    public void setNextQuestion_A(String nextQuestion_A) {
        NextQuestion_A = nextQuestion_A;
    }

    public String getNextQuestion_B() {
        return NextQuestion_B;
    }

    public void setNextQuestion_B(String nextQuestion_B) {
        NextQuestion_B = nextQuestion_B;
    }

    public String getNextQuestion_C() {
        return NextQuestion_C;
    }

    public void setNextQuestion_C(String nextQuestion_C) {
        NextQuestion_C = nextQuestion_C;
    }

    public String getNextQuestion_D() {
        return NextQuestion_D;
    }

    public void setNextQuestion_D(String nextQuestion_D) {
        NextQuestion_D = nextQuestion_D;
    }

    public String getNextQuestion_E() {
        return NextQuestion_E;
    }

    public void setNextQuestion_E(String nextQuestion_E) {
        NextQuestion_E = nextQuestion_E;
    }

    public String getNextQuestion_F() {
        return NextQuestion_F;
    }

    public void setNextQuestion_F(String nextQuestion_F) {
        NextQuestion_F = nextQuestion_F;
    }
}
