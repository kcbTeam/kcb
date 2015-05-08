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

    // TODO is multi selected?
    private boolean[] mCorrectId;

    public QuestionObj(String question, String optionA, String optionB, String optionC,
            String optionD, boolean[] correctOption) {
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

    public String getOptionD() {
        return mOptionD;
    }

    public void setOptionD(String mOptionD) {
        this.mOptionD = mOptionD;
    }

    public boolean[] getCorrectId() {
        return mCorrectId;
    }

    public void setCorrectId(boolean[] mCorrectOption) {
        this.mCorrectId = mCorrectOption;
    }

    public boolean equal(QuestionObj questionObj) {
        if (this == questionObj) {
            return true;
        }
        if (mCorrectId[0] == questionObj.getCorrectId()[0]
                && mCorrectId[1] == questionObj.getCorrectId()[1]
                && mCorrectId[2] == questionObj.getCorrectId()[2]
                && mCorrectId[3] == questionObj.getCorrectId()[3]
                && mOptionA.equals(questionObj.getOptionA())
                && mOptionB.equals(questionObj.getOptionB())
                && mOptionC.equals(questionObj.getOptionC())
                && mOptionD.equals(questionObj.getOptionD())) {
            return true;
        }
        return false;
    }

    public String toString() {

        return mQuestion + '\n' + mOptionA + '\n' + mOptionB + '\n' + mOptionC + '\n' + mOptionD
                + '\n' + mCorrectId[0] + '\n' + mCorrectId[1] + '\n' + mCorrectId[2] + '\n'
                + mCorrectId[3] + '\n';
    }

    public boolean isLegal() {
        if (mCorrectId[0] == false && mCorrectId[1] == false && mCorrectId[2] == false
                && mCorrectId[3] == false) {
            return false;
        }
        if (!mQuestion.equals("") && !mOptionA.equals("") && !mOptionB.equals("")
                && !mOptionC.equals("") && !mOptionD.equals("")) {
            return true;
        }
        return false;
    }



}
