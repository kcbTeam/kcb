package com.kcb.teacher.model.test;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public String getAnswerString() {
        String anString = "答案：";
        if (mChoiceAItem.isRight()) {
            anString += "A、";
        }
        if (mChoiceBItem.isRight()) {
            anString += "B、";
        }
        if (mChoiceCItem.isRight()) {
            anString += "C、";
        }
        if (mChoiceDItem.isRight()) {
            anString += "D、";
        }

        return anString.substring(0, anString.length() - 1);

    }

    public boolean isAllString() {
        if (mTitleItem.isText() && mChoiceAItem.isText() && mChoiceBItem.isText()
                && mChoiceCItem.isText() && mChoiceDItem.isText()) return true;
        return false;
    }

    public boolean isCompleted() {
        return mTitleItem.isCompleted()
                && mChoiceAItem.isCompleted()
                && mChoiceBItem.isCompleted()
                && mChoiceCItem.isCompleted()
                && mChoiceDItem.isCompleted()
                && (mChoiceAItem.isRight() || mChoiceBItem.isRight() || mChoiceCItem.isRight() || mChoiceDItem
                        .isRight());
    }

    public void changeQuestionToSerializable() {
        mTitleItem.changeBitmapToBytes();
        mChoiceAItem.changeBitmapToBytes();
        mChoiceBItem.changeBitmapToBytes();
        mChoiceCItem.changeBitmapToBytes();
        mChoiceDItem.changeBitmapToBytes();
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", mId);
            jsonObject.put("title", mTitleItem.toJsonObject());
            
            JSONArray choiceArray = new JSONArray();
            choiceArray.put(mChoiceAItem.toJsonObject());
            choiceArray.put(mChoiceBItem.toJsonObject());
            choiceArray.put(mChoiceCItem.toJsonObject());
            choiceArray.put(mChoiceDItem.toJsonObject());
            jsonObject.put("choice", choiceArray);
        } catch (JSONException e) {}
        return jsonObject;
    }
    
    /**
     * 
     * @title: List<Question> To String
     * @description:
     * @author: ZQJ
     * @date: 2015年5月30日 下午7:55:04
     * @param list
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static String questionListToString(List<Question> list) throws JSONException,
            IOException {
        int length = list.size();
        if (length < 1) {
            return null;
        }
        JSONArray questionsArray = new JSONArray();
        Question temp = new Question();
        for (int i = 0; i < length; i++) {
            temp = list.get(i);
            JSONObject jsonObject = new JSONObject();

            // jsonObject.put("mId", temp.getId());
            jsonObject.put("mTitleItem", temp.getTitle().toJsonObject());
            jsonObject.put("mChoiceAItem", temp.getChoiceA().toJsonObject());
            jsonObject.put("mChoiceBItem", temp.getChoiceB().toJsonObject());
            jsonObject.put("mChoiceCItem", temp.getChoiceC().toJsonObject());
            jsonObject.put("mChoiceDItem", temp.getChoiceD().toJsonObject());

            questionsArray.put(jsonObject);
        }
        return questionsArray.toString();

    }
    
}
