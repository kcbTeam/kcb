package com.kcb.teacher.model;

import java.io.Serializable;

/**
 * 
 * @className: QuestionObj
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 上午10:08:21
 */
public class QuestionObj implements Serializable {

    private static final long serialVersionUID = 2L;

    private String mQuestion;
    private String mOptionA;
    private String mOptionB;
    private String mOptionC;
    private String mOptionD;

    //TODO is multi selected?
    private int mCorrectId;

    public QuestionObj(String question, String optionA, String optionB, String optionC,
            String optionD, int correctOption) {
        mQuestion = question;
        mOptionA = optionA;
        mOptionB = optionB;
        mOptionC = optionC;
        mOptionD = optionD;
        mCorrectId = correctOption;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }


    public String getOptionA() {
        return mOptionA;
    }

    public void setOptionA(String mOptionA) {
        this.mOptionA = mOptionA;
    }

    public String getOptionB() {
        return mOptionB;
    }

    public void setOptionB(String mOptionB) {
        this.mOptionB = mOptionB;
    }

    public String getOptionC() {
        return mOptionC;
    }

    public void setOptionC(String mOptionC) {
        this.mOptionC = mOptionC;
    }

    public String getmOptionD() {
        return mOptionD;
    }

    public void setOptionD(String mOptionD) {
        this.mOptionD = mOptionD;
    }

    public int getCorrectId() {
        return mCorrectId;
    }

    public void setCorrectId(int mCorrectOption) {
        this.mCorrectId = mCorrectOption;
    }

    public boolean equal(QuestionObj questionObj) {
        if (this == questionObj) {
            return true;
        }
        if (this.mCorrectId == questionObj.getCorrectId()
                && this.mOptionA.equals(questionObj.getOptionA())
                && this.mOptionB.equals(questionObj.getOptionB())
                && this.mOptionC.equals(questionObj.getOptionC())
                && this.mOptionD.equals(questionObj.getmOptionD())) {
            return true;
        }
        return false;
    }



}
