package com.kcb.common.model.test;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
import com.kcb.teacher.database.test.QuestionResult;
import com.kcb.teacher.model.KAccount;

public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mId; // 后台分配的测试Id
    private String mName; // 测试的名称
    private int mTime = 300; // 测试的时间，多少秒，默认300s
    private long mDate; // 测试创建的时间 ——> 修改的时间 ——> 开始的时间
    private boolean mHasTested; // 是否测试过了
    private List<Question> mQuestions; // 测试包括的问题

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

    public void setName(String name) {
        mName = name;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(getDate()));
        return date;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public boolean hasTested() {
        return mHasTested;
    }

    public void setHasTested(boolean hasTested) {
        mHasTested = hasTested;
    }

    public void setQuestions(String questions) {
        JSONArray questionArray;
        try {
            questionArray = new JSONArray(questions);
            for (int i = 0; i < questionArray.length(); i++) {
                mQuestions.add(Question.fromJsonObject(questionArray.optJSONObject(i)));
            }
        } catch (JSONException e) {}
    }

    // 如果测试的结束时间 < 现在的时间，表示已经结束了
    public boolean hasEnded() {
        return getDate() + getTime() * 1000 < System.currentTimeMillis();
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

    /**
     * 编辑内容：此测试是否编辑完成了
     */
    public boolean isEditFinish() {
        for (int i = 0; i < mQuestions.size(); i++) {
            if (!mQuestions.get(i).isEditFinish()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取第一个未完成的的题目编号
     */
    public int getFirstUnFinishQuestionIndex() {
        for (int i = 0; i < mQuestions.size(); i++) {
            if (!mQuestions.get(i).isEditFinish()) {
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

    /**
     * 请求单个测试的结果，设置
     */
    public void setQuestionResult(QuestionResult result) {
        for (Question question : mQuestions) {
            if (question.getId() == result.getId()) {
                question.setRate(result.getRate());
            }
        }
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }

    // 学生模块，保存测试信息到数据库时，获取题目内容
    public String getQuestionsString() {
        JSONArray questionArray = new JSONArray();
        for (int i = 0; i < mQuestions.size(); i++) {
            questionArray.put(mQuestions.get(i).toJsonObject());
        }
        return questionArray.toString();
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
     * 当删除一个测试中的一个题目的时候，需要重新命名保存的图片名称； 因为图片名称和题目id有关，而删除题目后，题目的id会变化； 题目id从0开始递增；
     */
    public void renameBitmap() {
        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).renameBitmap(mName, i);
        }
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
    private static final String KEY_ID = "testid";
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
            requestObject.put(KEY_TEST, toJsonObject());
        } catch (JSONException e) {}

        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            entity.addPart("data", new StringBody(requestObject.toString()));
        } catch (UnsupportedEncodingException e) {}

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
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId); // 测试id
            jsonObject.put(KEY_NAME, mName); // 测试名称
            jsonObject.put(KEY_TIME, mTime); // 测试的时间，单位为秒
            jsonObject.put(KEY_DATE, mDate); // 测试的开始时间戳
            jsonObject.put(KEY_HASTESTED, mHasTested); // 是否已经测试过了

            JSONArray questionArray = new JSONArray();
            for (int i = 0; i < mQuestions.size(); i++) {
                mQuestions.get(i).setId(i);
                questionArray.put(mQuestions.get(i).toJsonObject());
            }
            jsonObject.put(KEY_QUESTION, questionArray); // 测试包括的题目
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
                Question question = Question.fromJsonObject(questionArray.getJSONObject(i));
                test.mQuestions.add(question);
            } catch (JSONException e) {}
        }
        return test;
    }
}
