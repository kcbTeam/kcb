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

    private String mId; // from client

    private QuestionItem mTitleItem;
    private QuestionItem mChoiceAItem;
    private QuestionItem mChoiceBItem;
    private QuestionItem mChoiceCItem;
    private QuestionItem mChoiceDItem;

    public Question() {
        mTitleItem = new QuestionItem();
        mChoiceAItem = new QuestionItem();
        mChoiceAItem.setId("1");
        mChoiceBItem = new QuestionItem();
        mChoiceBItem.setId("2");
        mChoiceCItem = new QuestionItem();
        mChoiceCItem.setId("3");
        mChoiceDItem = new QuestionItem();
        mChoiceDItem.setId("4");
    }

    public static Question clone(Question object) {
        Question question = new Question();
        QuestionItem.copy(object.mTitleItem, question.mTitleItem);
        QuestionItem.copy(object.mChoiceAItem, question.mChoiceAItem);
        QuestionItem.copy(object.mChoiceBItem, question.mChoiceBItem);
        QuestionItem.copy(object.mChoiceCItem, question.mChoiceCItem);
        QuestionItem.copy(object.mChoiceDItem, question.mChoiceDItem);
        return question;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
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

    public boolean equal(Question questionObj) {
        return mTitleItem.equals(questionObj.getTitle())
                && mChoiceAItem.equals(questionObj.getChoiceA())
                && mChoiceBItem.equals(questionObj.getChoiceB())
                && mChoiceCItem.equals(questionObj.getChoiceC())
                && mChoiceDItem.equals(questionObj.getChoiceD());
    }

    public String contentString() {
        return mTitleItem.getText() + '\n' + '\n' + "A." + mChoiceAItem.getText() + '\n' + "B."
                + mChoiceBItem.getText() + '\n' + "C." + mChoiceCItem.getText() + '\n' + "D."
                + mChoiceDItem.getText();
    }

    public boolean isAllString() {
        if (mTitleItem.isText() && mChoiceAItem.isText() && mChoiceBItem.isText()
                && mChoiceCItem.isText() && mChoiceDItem.isText()) return true;
        return false;
    }

    public boolean isCompleted() {
        return !mTitleItem.isEmpty() && !mChoiceAItem.isEmpty() && !mChoiceBItem.isEmpty()
                && !mChoiceCItem.isEmpty() && !mChoiceDItem.isEmpty();
    }

    public void changeQuestionToSerializable() {
        mTitleItem.changeBitmapToBytes();
        mChoiceAItem.changeBitmapToBytes();
        mChoiceBItem.changeBitmapToBytes();
        mChoiceCItem.changeBitmapToBytes();
        mChoiceDItem.changeBitmapToBytes();
    }
}
