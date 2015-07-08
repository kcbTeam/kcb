package com.kcb.common.model.test;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kcb.common.model.answer.QuestionAnswer;
import com.kcb.common.model.answer.TestAnswer;
import com.kcb.student.util.FileUtil;
import com.kcb.teacher.model.account.KAccount;

public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mId; // from server when start test
    private String mName; // test name
    private int mTime = 300; // default test time is 5 minutes，即300s
    private long mDate; // test date, create date or start test date
    private boolean mHasTested; // true if teacher start test
    private List<Question> mQuestions;

    public Test() {
        mQuestions = new ArrayList<Question>();
    }

    public Test(String name, int num) {
        mName = name;
        mQuestions = new ArrayList<Question>();
        for (int i = 0; i < num; i++) {
            mQuestions.add(new Question());
        }
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getTime() {
        return mTime;
    }

    public int getMinTime() {
        return mTime / 60;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public long getDate() {
        return mDate;
    }

    public String getDateString() {
        return new Date(mDate).toString();
    }

    public void setDate(long date) {
        mDate = date;
    }

    public boolean hasTested() {
        return mHasTested;
    }

    public void setHasTested(boolean isTested) {
        mHasTested = isTested;
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public Question getQuestion(int index) {
        return mQuestions.get(index);
    }

    public void addQuestion() {
        mQuestions.add(new Question());
    }

    public void deleteQuestion(int index) {
        mQuestions.get(index).deleteBitmap();
        mQuestions.remove(index);
    }

    public void updateQuestion(int index, Question question) {
        mQuestions.set(index, question);
    }

    /**
     * set before start test, because use may add/delete question before start
     */
    public void setQuestionId() {
        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).setId(i);
        }
    }

    public int getQuestionNum() {
        return mQuestions.size();
    }

    public boolean isCompleted() {
        for (int i = 0; i < mQuestions.size(); i++) {
            if (!mQuestions.get(i).isCompleted()) {
                return false;
            }
        }
        return true;
    }

    public int getUnCompleteIndex() {
        for (int i = 0; i < mQuestions.size(); i++) {
            if (!mQuestions.get(i).isCompleted()) {
                return i;
            }
        }
        return -1;
    }

    public void setAnswer(TestAnswer testAnswer) {
        for (Question question : mQuestions) {
            for (QuestionAnswer questionAnswer : testAnswer.getQuestionAnswers()) {
                if (question.getId() == questionAnswer.getId()) {
                    question.setRate(questionAnswer.getRate());
                }
            }
        }
    }

    // 转成的string不会放到数据库中，只保存路径
    @Override
    public String toString() {
        return toJsonObject(false).toString();
    }

    public void release() {
        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).release();
        }
        mQuestions = null;
    }

    /**
     * test to json, json to test
     */
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";
    private static final String KEY_HASTESTED = "hastested";
    private static final String KEY_QUESTION = "question";

    public HttpEntity toHttpEntity() {
        final String KEY_ID = "tchid";
        final String KEY_TEST = "test";

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put(KEY_ID, KAccount.getAccountId());
            setQuestionId();
            requestObject.put(KEY_TEST, toJsonObject(true));
        } catch (JSONException e) {}

        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            entity.addPart("data", new StringBody(requestObject.toString()));
        } catch (UnsupportedEncodingException e) {
        }

        for (int i = 0; i < mQuestions.size(); i++) {
            Question question = mQuestions.get(i);
            if (!question.getTitle().isText()) {
                entity.addPart("question_" + question.getId() + "_item_"
                        + question.getTitle().getId(), new FileBody(new File(question.getTitle()
                        .getBitmapPath())));
            }
            if (!question.getChoiceA().isText()) {
                entity.addPart("question_" + question.getId() + "_item_"
                        + question.getChoiceA().getId(), new FileBody(new File(question
                        .getChoiceA().getBitmapPath())));
            }
            if (!question.getChoiceB().isText()) {
                entity.addPart("question_" + question.getId() + "_item_"
                        + question.getChoiceB().getId(), new FileBody(new File(question
                        .getChoiceB().getBitmapPath())));
            }
            if (!question.getChoiceC().isText()) {
                entity.addPart("question_" + question.getId() + "_item_"
                        + question.getChoiceC().getId(), new FileBody(new File(question
                        .getChoiceC().getBitmapPath())));
            }
            if (!question.getChoiceD().isText()) {
                entity.addPart("question_" + question.getId() + "_item_"
                        + question.getChoiceD().getId(), new FileBody(new File(question
                        .getChoiceD().getBitmapPath())));
            }
        }
        return entity;
    }

    /**
     * 发送到服务器的JsonObject包括的是图片String，保存到数据库的JsonObject包括的是图片的路径。
     */
    public JSONObject toJsonObject(boolean toServer) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);

            jsonObject.put(KEY_NAME, mName);
            // jsonObject.put(KEY_NAME, getUTF8XMLString(mName));

            jsonObject.put(KEY_TIME, mTime);
            jsonObject.put(KEY_DATE, mDate);
            jsonObject.put(KEY_HASTESTED, mHasTested);

            JSONArray questionArray = new JSONArray();
            for (int i = 0; i < mQuestions.size(); i++) {
                mQuestions.get(i).setId(i);
                questionArray.put(mQuestions.get(i).toJsonObject(toServer));
            }

            jsonObject.put(KEY_QUESTION, questionArray);
        } catch (JSONException e) {}
        return jsonObject;
    }

    /**
     * Get XML String of utf-8
     * 
     * @return XML-Formed string
     */
    // public static String getUTF8XMLString(String xml) {
    // // A StringBuffer Object
    // StringBuffer sb = new StringBuffer();
    // sb.append(xml);
    // String xmString = "";
    // String xmlUTF8 = "";
    // try {
    // xmString = new String(sb.toString().getBytes("UTF-8"));
    // xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
    // System.out.println("utf-8 编码：" + xmlUTF8);
    // } catch (UnsupportedEncodingException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // // return to String Formed
    // return xmlUTF8;
    // }

    public static Test fromJsonObject(JSONObject jsonObject) {
        Test test = new Test();
        test.mId = jsonObject.optString(KEY_ID);
        test.mName = jsonObject.optString(KEY_NAME);
        test.mTime = jsonObject.optInt(KEY_TIME);
        test.mDate = jsonObject.optLong(KEY_DATE);
        test.mHasTested = jsonObject.optBoolean(KEY_HASTESTED);

        JSONArray questionArray = jsonObject.optJSONArray(KEY_QUESTION);
        for (int i = 0; i < questionArray.length(); i++) {
            try {
                Question question = Question.fromJson(questionArray.getJSONObject(i));
                test.mQuestions.add(question);
            } catch (JSONException e) {}
        }
        return test;
    }

    /**
     * 学生答题，从网络上获取到题目后，需要将题目中的图片String转成Bitmap保存到本地
     */
    public void saveBitmap() {
        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).saveBitmap(mName, i);
        }
    }

    /**
     * 删除一个测试的时候，要把此与此测试相关的图片删除
     */
    public void deleteBitmap() {
        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).deleteBitmap();
        }
        new File(FileUtil.getTestPath(mName)).delete();
    }

    /**
     * 当删除一个测试中的一个题目的时候，需要重新命名图片的名称
     */
    public void renameBitmap() {
        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).renameBitmap(mName, i);
        }
    }
}
