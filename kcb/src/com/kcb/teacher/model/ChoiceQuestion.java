package com.kcb.teacher.model;

import java.io.Serializable;

/**
 * 
 * @className: ChoiceQuestion
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 上午10:08:21
 */
public class ChoiceQuestion implements Serializable, Cloneable {

    private static final long serialVersionUID = 2L;

    private int mQuestionNum;
    private TextContent mQuestion;
    private TextContent mOptionA;
    private TextContent mOptionB;
    private TextContent mOptionC;
    private TextContent mOptionD;

    private boolean[] mCorrectId;

    public ChoiceQuestion() {
        mQuestionNum = 0;
        mQuestion = new TextContent("");
        mOptionA = new TextContent("");
        mOptionB = new TextContent("");
        mOptionC = new TextContent("");
        mOptionD = new TextContent("");
        mCorrectId = new boolean[] {false, false, false, false};
    }

    public ChoiceQuestion(TextContent question, TextContent optionA, TextContent optionB,
            TextContent optionC, TextContent optionD, boolean[] correctOption) {
        mQuestion = question;
        mOptionA = optionA;
        mOptionB = optionB;
        mOptionC = optionC;
        mOptionD = optionD;
        mCorrectId = correctOption;
    }

    /**
     * 
     * Constructor: ChoiceQuestion
     * copy data from a ChoiceQuestion object, be careful that must copy the real data of those reference objects
     */
    public ChoiceQuestion(ChoiceQuestion choiceQuestion) {
//        mCorrectId = choiceQuestion.mCorrectId;
//        mOptionA = choiceQuestion.getOptionA();
//        mOptionB = choiceQuestion.getOptionB();
//        mOptionC = choiceQuestion.getOptionC();
//        mOptionD = choiceQuestion.getOptionD();
        mCorrectId =
                new boolean[] {choiceQuestion.mCorrectId[0], choiceQuestion.mCorrectId[1],
                        choiceQuestion.mCorrectId[2], choiceQuestion.mCorrectId[3]};
        if (choiceQuestion.getOptionA().isString()) {
            mOptionA = new TextContent(choiceQuestion.getOptionA().getContentString());
        } else {
            mOptionA = new TextContent(choiceQuestion.getOptionA().getContentBitmap());
        }

        if (choiceQuestion.getOptionB().isString()) {
            mOptionB = new TextContent(choiceQuestion.getOptionB().getContentString());
        } else {
            mOptionB = new TextContent(choiceQuestion.getOptionB().getContentBitmap());
        }
        
        if (choiceQuestion.getOptionC().isString()) {
            mOptionC = new TextContent(choiceQuestion.getOptionC().getContentString());
        } else {
            mOptionC = new TextContent(choiceQuestion.getOptionC().getContentBitmap());
        }
        
        if (choiceQuestion.getOptionD().isString()) {
            mOptionD = new TextContent(choiceQuestion.getOptionD().getContentString());
        } else {
            mOptionD = new TextContent(choiceQuestion.getOptionD().getContentBitmap());
        }
        if (choiceQuestion.getQuestion().isString()) {
            mQuestion = new TextContent(choiceQuestion.getQuestion().getContentString());
        } else {
            mQuestion = new TextContent(choiceQuestion.getQuestion().getContentBitmap());
        }
    }

    public int getQuestionNum() {
        return this.mQuestionNum;
    }

    public void setQuestionNum(int mQuestionNum) {
        this.mQuestionNum = mQuestionNum;
    }

    public TextContent getQuestion() {
        return mQuestion;
    }

    public void setQuestion(TextContent mQuestion) {
        this.mQuestion = mQuestion;
    }

    public TextContent getOptionA() {
        return mOptionA;
    }

    public void setOptionA(TextContent mOptionA) {
        this.mOptionA = mOptionA;
    }

    public TextContent getOptionB() {
        return mOptionB;
    }

    public void setOptionB(TextContent mOptionB) {
        this.mOptionB = mOptionB;
    }

    public TextContent getOptionC() {
        return mOptionC;
    }

    public void setOptionC(TextContent mOptionC) {
        this.mOptionC = mOptionC;
    }

    public TextContent getOptionD() {
        return mOptionD;
    }

    public void setOptionD(TextContent mOptionD) {
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

        return "." + mQuestion + '\n' + "A." + mOptionA + '\n' + "B." + mOptionB + '\n' + "C."
                + mOptionC + '\n' + "D." + mOptionD + '\n' + "答案：" + getCorrectOption();
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

    public void copyFrom(ChoiceQuestion choiceQuestion) {
        this.mCorrectId = choiceQuestion.getCorrectId().clone();
        this.mOptionA.copy(choiceQuestion.getOptionA());
        this.mOptionB.copy(choiceQuestion.getOptionB());
        this.mOptionC.copy(choiceQuestion.getOptionC());
        this.mOptionD.copy(choiceQuestion.getOptionD());
        this.mQuestion.copy(choiceQuestion.getQuestion());

    }

}
