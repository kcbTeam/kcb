package com.kcb.common.model.answer;

import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: TestAnswer
 * @description: answer for a test
 * @author: wanghang
 * @date: 2015-6-4 下午1:42:22
 */
public class TestAnswer {

    private String mId; // test id
    private List<QuestionAnswer> mQuestionAnswers;

    public TestAnswer() {
    }

    public TestAnswer(Test test) {
        mId = test.getId();
        mQuestionAnswers = new ArrayList<QuestionAnswer>();
        for (int i = 0; i < test.getQuestionNum(); i++) {
            mQuestionAnswers.add(new QuestionAnswer(test.getQuestion(i)));
        }
        //
        mTestPointer = test;
    }

    public String getId() {
        return mId;
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        return mQuestionAnswers;
    }

    public void saveQuestionAnswer(Question question) {
        for (int i = 0; i < mQuestionAnswers.size(); i++) {
            QuestionAnswer questionAnswer = mQuestionAnswers.get(i);
            if (questionAnswer.getId() == question.getId()) {
                questionAnswer.setAnswer(question);
            }
        }
    }

    public List<Integer> getUnFinishedIndex() {
        List<Integer> indexs = new ArrayList<Integer>();
        for (int i = 0; i < mQuestionAnswers.size(); i++) {
            if (!mQuestionAnswers.get(i).hasFinished()) {
                indexs.add(Integer.valueOf(i));
            }
        }
        return indexs;
    }

    /**
     * test answer to json
     */
    public static final String KEY_ID = "id";
    public static final String KEY_QUESTION_ANSWER = "questionanswer";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < mQuestionAnswers.size(); i++) {
                jsonArray.put(mQuestionAnswers.get(i).toJsonObject());
            }

            jsonObject.put(KEY_QUESTION_ANSWER, jsonArray);
        } catch (JSONException e) {
        }
        return jsonObject;
    }

    public JSONArray toJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < mQuestionAnswers.size(); i++) {
            jsonArray.put(mQuestionAnswers.get(i).toJsonObject());
        }
        return jsonArray;
    }

    public static TestAnswer fromJsonObject(JSONObject jsonObject) {
        TestAnswer testAnswer = new TestAnswer();
        try {
            testAnswer.mId = jsonObject.optString(KEY_ID);

            JSONArray jsonArray = jsonObject.optJSONArray(KEY_QUESTION_ANSWER);
            for (int i = 0; i < jsonArray.length(); i++) {
                testAnswer.mQuestionAnswers.add(QuestionAnswer.fromJsonObject(jsonArray
                        .getJSONObject(i)));
            }
        } catch (JSONException e) {
        }
        return testAnswer;
    }

    /**
     * xia
     */
    private Test mTestPointer;
    private int sumNum;
    private int rightNum;


    public JSONArray answerInfo() {
        JSONArray jsonArray = new JSONArray();
        sumNum = mQuestionAnswers.size();
        for (int i = 0; i < mQuestionAnswers.size(); i++) {
            jsonArray.put(mQuestionAnswers.get(i).anserInfo());
            if(mQuestionAnswers.get(i).isCorrect())
                rightNum++;
        }
        return jsonArray;
    }

    public int getSumNum(){
        return sumNum;
    }

    public int getRightNum() {
        return rightNum;
    }
}
