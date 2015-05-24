package com.kcb.teacher.model.test;

import java.io.Serializable;

/**
 * 
 * @className: ChoiceQuestion
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 上午10:08:21
 */
public class Question implements Serializable {

    private static final long serialVersionUID = 2L;

    private QuestionItem mTitleItem;
    private QuestionItem mChoiceAItem;
    private QuestionItem mChoiceBItem;
    private QuestionItem mChoiceCItem;
    private QuestionItem mChoiceDItem;

    private boolean[] mAnswers;

    public Question() {
        mTitleItem = new QuestionItem();
        mChoiceAItem = new QuestionItem();
        mChoiceBItem = new QuestionItem();
        mChoiceCItem = new QuestionItem();
        mChoiceDItem = new QuestionItem();
        mAnswers = new boolean[] {false, false, false, false};
    }

    public Question(QuestionItem question, QuestionItem optionA, QuestionItem optionB,
            QuestionItem optionC, QuestionItem optionD, boolean[] correctOption) {
        mTitleItem = question;
        mChoiceAItem = optionA;
        mChoiceBItem = optionB;
        mChoiceCItem = optionC;
        mChoiceDItem = optionD;
        mAnswers = correctOption;
    }

    public QuestionItem getTitle() {
        return mTitleItem;
    }

    public QuestionItem getChoiceA() {
        return mChoiceAItem;
    }

    public QuestionItem getChoiceB() {
        return mChoiceBItem;
    }

    public QuestionItem getChoiceC() {
        return mChoiceCItem;
    }

    public QuestionItem getChoiceD() {
        return mChoiceDItem;
    }

    public boolean[] getCorrectId() {
        return mAnswers;
    }

    public void setCorrectId(boolean[] mCorrectOption) {
        mAnswers = mCorrectOption;
    }

    public boolean equal(Question questionObj) {
        if (this == questionObj) {
            return true;
        }
        if (mAnswers[0] == questionObj.getCorrectId()[0]
                && mAnswers[1] == questionObj.getCorrectId()[1]
                && mAnswers[2] == questionObj.getCorrectId()[2]
                && mAnswers[3] == questionObj.getCorrectId()[3]
                && mTitleItem.equals(questionObj.getTitle())
                && mChoiceAItem.equals(questionObj.getChoiceA())
                && mChoiceBItem.equals(questionObj.getChoiceB())
                && mChoiceCItem.equals(questionObj.getChoiceC())
                && mChoiceDItem.equals(questionObj.getChoiceD())) {
            return true;
        }
        return false;
    }

    public String contentString() {
        return mTitleItem.getText() + '\n' + "A." + mChoiceAItem.getText() + '\n' + "B."
                + mChoiceBItem.getText() + '\n' + "C." + mChoiceCItem.getText() + '\n' + "D."
                + mChoiceDItem.getText() + '\n';
    }

    public boolean isAllString() {
        if (mTitleItem.isText() && mChoiceAItem.isText() && mChoiceBItem.isText()
                && mChoiceCItem.isText() && mChoiceDItem.isText()) return true;
        return false;
    }

    public boolean isLegal() {
        if (mAnswers[0] == false && mAnswers[1] == false && mAnswers[2] == false
                && mAnswers[3] == false) {
            return false;
        }
        if (!mTitleItem.isEmpty() && !mChoiceAItem.isEmpty() && !mChoiceBItem.isEmpty()
                && !mChoiceCItem.isEmpty() && !mChoiceDItem.isEmpty()) {
            return true;
        }
        return false;
    }

    public String getCorrectOptionString() {
        String tempString = "答案：";
        if (mAnswers[0]) {
            tempString += "A、";
        }
        if (mAnswers[1]) {
            tempString += "B、";
        }
        if (mAnswers[2]) {
            tempString += "C、";
        }
        if (mAnswers[3]) {
            tempString += "D、";
        }
        return tempString.substring(0, tempString.length() - 1);
    }
}
