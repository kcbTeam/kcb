package com.kcb.teacher.model;

import java.io.Serializable;

/**
 * 
 * @className: ChoiceQuestion
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 上午10:08:21
 */
public class ChoiceQuestion implements Serializable {

    private static final long serialVersionUID = 2L;

    private String mQuestion;
    private String mOptionA;
    private String mOptionB;
    private String mOptionC;
    private String mOptionD;

    private boolean[] mCorrectId;

    public ChoiceQuestion() {
        mQuestion = "";
        mOptionA = "";
        mOptionB = "";
        mOptionC = "";
        mOptionD = "";
        mCorrectId = new boolean[] {false, false, false, false};
    }

    public ChoiceQuestion(String question, String optionA, String optionB, String optionC,
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

    public boolean equal(ChoiceQuestion questionObj) {
        if (this == questionObj) {
            return true;
        }
        if (mCorrectId[0] == questionObj.getCorrectId()[0]
                && mCorrectId[1] == questionObj.getCorrectId()[1]
                && mCorrectId[2] == questionObj.getCorrectId()[2]
                && mCorrectId[3] == questionObj.getCorrectId()[3]
                && mQuestion.equals(questionObj.getQuestion())
                && mOptionA.equals(questionObj.getOptionA())
                && mOptionB.equals(questionObj.getOptionB())
                && mOptionC.equals(questionObj.getOptionC())
                && mOptionD.equals(questionObj.getOptionD())) {
            return true;
        }
        return false;
    }

    public String toString() {

        return "." + mQuestion + '\n' + "A." + mOptionA + '\n' + "B." + mOptionB + '\n' + "C." + mOptionC
                + '\n' + "D." + mOptionD + '\n' + "答案：" + getCorrectOption();
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

    private String getCorrectOption() {
        String tempString = "";
        if (mCorrectId[0]) {
            tempString += "A、";
        }
        if (mCorrectId[1]) {
            tempString += "B、";
        }
        if (mCorrectId[2]) {
            tempString += "C、";
        }
        if (mCorrectId[3]) {
            tempString += "D、";
        }

        return tempString.substring(0, tempString.length() - 1);
    }

}
