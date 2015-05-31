package com.kcb.teacher.database.test.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.QuestionItem;

public class TestJsonUtils {


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
    public static String changeQuestionsToString(List<Question> list) throws JSONException,
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
            jsonObject.put("mTitleItem", changeQuestionItemToJsonObject(temp.getTitle()));
            jsonObject.put("mChoiceAItem", changeQuestionItemToJsonObject(temp.getChoiceA()));
            jsonObject.put("mChoiceBItem", changeQuestionItemToJsonObject(temp.getChoiceB()));
            jsonObject.put("mChoiceCItem", changeQuestionItemToJsonObject(temp.getChoiceC()));
            jsonObject.put("mChoiceDItem", changeQuestionItemToJsonObject(temp.getChoiceD()));

            questionsArray.put(jsonObject);
        }
        return questionsArray.toString();

    }

    /**
     * 
     * @title: jsonStringToQuesitonList
     * @description: jsonString to List<Question>
     * @author: ZQJ
     * @date: 2015年5月30日 下午10:18:12
     * @param jsonString
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static List<Question> jsonStringToQuesitonList(String jsonString) throws JSONException,
            IOException {
        JSONArray jsonArray = new JSONArray(jsonString);
        int size = jsonArray.length();
        if (size < 1) {
            return null;
        }
        List<Question> list = new ArrayList<Question>();
        for (int i = 0; i < size; i++) {
            Question tempQuestion = new Question();

            JSONObject questionJsonObject = jsonArray.getJSONObject(i);

            /*
             * mId
             */
            // String mId = (String) questionJsonObject.get("mId");
            // tempQuestion.setId(mId);

            /*
             * mTitleItem
             */
            JSONObject questionItemJsonObject = questionJsonObject.getJSONObject("mTitleItem");
            jsonObjectToQuestionItem(tempQuestion.getTitle(), questionItemJsonObject);


            /*
             * mChoiceAItem
             */
            questionItemJsonObject = questionJsonObject.getJSONObject("mChoiceAItem");
            jsonObjectToQuestionItem(tempQuestion.getChoiceA(), questionItemJsonObject);

            /*
             * mChoiceBItem
             */
            questionItemJsonObject = questionJsonObject.getJSONObject("mChoiceBItem");
            jsonObjectToQuestionItem(tempQuestion.getChoiceB(), questionItemJsonObject);

            /*
             * mChoiceCItem
             */
            questionItemJsonObject = questionJsonObject.getJSONObject("mChoiceCItem");
            jsonObjectToQuestionItem(tempQuestion.getChoiceC(), questionItemJsonObject);

            /*
             * mCoiceDItem
             */
            questionItemJsonObject = questionJsonObject.getJSONObject("mChoiceDItem");
            jsonObjectToQuestionItem(tempQuestion.getChoiceD(), questionItemJsonObject);

            list.add(tempQuestion);
        }

        return list;

    }

    /**
     * 
     * @title: JSONObject To QuestionItem
     * @description:
     * @author: ZQJ
     * @date: 2015年5月30日 下午8:08:02
     * @param jsonObject
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static void jsonObjectToQuestionItem(QuestionItem questionItem, JSONObject jsonObject)
            throws JSONException, IOException {
        // String mId = (String) jsonObject.get("mId");
        boolean isText = (Boolean) jsonObject.get("isText");
        boolean isRight = (Boolean) jsonObject.get("isRight");
        double mRate = (Double) jsonObject.getDouble("mRate");
        questionItem.setRate(mRate);
        questionItem.setIsRight(isRight);

        if (isText) {
            String mText = jsonObject.getString("mText");
            questionItem.setText(mText);
        } else {
            Bitmap mBitmap = ImageToJsonString.decodeImagefromJson(jsonObject.getString("mBitmap"));
            questionItem.setBitmap(mBitmap);
        }
        // questionItem.setId(mId);
    }


    /**
     * 
     * @title: QuestionItem to JSONObject
     * @description:
     * @author: ZQJ
     * @date: 2015年5月30日 下午7:54:36
     * @param questionItem
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static JSONObject changeQuestionItemToJsonObject(QuestionItem questionItem)
            throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject();

        // jsonObject.put("mId", questionItem.getId());
        jsonObject.put("mRate", questionItem.getRate());
        jsonObject.put("isRight", questionItem.isRight());
        jsonObject.put("isText", questionItem.isText());

        if (questionItem.isText()) {
            jsonObject.put("mText", questionItem.getText());
        } else {
            if (null != questionItem.getBitmap()) {
                jsonObject.put("mBitmap",
                        ImageToJsonString.encodeImageToJson(questionItem.getBitmap()));
            } else {
                jsonObject.put("mBitmap", " ");
            }
        }
        return jsonObject;
    }
}
