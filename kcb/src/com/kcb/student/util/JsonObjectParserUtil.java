package com.kcb.student.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.Test;

/**
 * 
 * @className: JsonObjectParserUtil
 * @description:
 * @author: TaoLi
 * @date: 2015-5-23 下午3:39:23
 */

public class JsonObjectParserUtil {
    private String strJson;
    private Test test;;

    public JsonObjectParserUtil(String strJson) {
        this.strJson = strJson;
    }

    public Test ParserJsonObject() {
        Question question;
        try {
            JSONObject jsonObject = new JSONObject(strJson);
            List<Question> questionsList = new ArrayList<Question>();
            JSONArray jsonArray = (JSONArray) jsonObject.get("QuestionList");
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                question = new Question();
                if (jsonObject2.getBoolean("mQuestionType")) {
                    question.getTitle().setText(jsonObject2.getString("mQuestion"));
                } else {
                    Bitmap bitmap =
                            ImageToJsonString.getBitmapFromByte(ImageToJsonString
                                    .decode(jsonObject2.getString("mQuestion")));
                    question.getTitle().setBitmap(bitmap);
                }
                if (jsonObject2.getBoolean("mOptionAType")) {
                    question.getChoiceA().setText(jsonObject2.getString("mOptionA"));
                } else {
                    Bitmap bitmap =
                            ImageToJsonString.getBitmapFromByte(ImageToJsonString
                                    .decode(jsonObject2.getString("mOptionA")));
                    question.getChoiceA().setBitmap(bitmap);
                }
                if (jsonObject2.getBoolean("mOptionBType")) {
                    question.getChoiceB().setText(jsonObject2.getString("mOptionB"));
                } else {
                    Bitmap bitmap =
                            ImageToJsonString.getBitmapFromByte(ImageToJsonString
                                    .decode(jsonObject2.getString("mOptionB")));
                    question.getChoiceB().setBitmap(bitmap);
                }
                if (jsonObject2.getBoolean("mOptionCType")) {
                    question.getChoiceC().setText(jsonObject2.getString("mOptionC"));
                } else {
                    Bitmap bitmap =
                            ImageToJsonString.getBitmapFromByte(ImageToJsonString
                                    .decode(jsonObject2.getString("mOptionC")));
                    question.getChoiceC().setBitmap(bitmap);
                }
                if (jsonObject2.getBoolean("mOptionDType"))
                    question.getChoiceD().setText(jsonObject2.getString("mOptionD"));
                else {
                    Bitmap bitmap =
                            ImageToJsonString.getBitmapFromByte(ImageToJsonString
                                    .decode(jsonObject2.getString("mOptionD")));
                    question.getChoiceD().setBitmap(bitmap);
                }
                // question.setCorrectId(IntToBooleans.IntCvToBooleans(jsonObject2.getInt("mAnswer")));
                questionsList.add(question);
            }
            test =
                    new Test(jsonObject.getString("mTestName"), questionsList,
                            jsonObject.getInt("mTestTime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return test;
    }
}
