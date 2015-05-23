package com.kcb.student.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kcb.student.util.ChoiceQuestion;
import com.kcb.student.util.CourseTest;

/**
 * 
 * @className: JsonObjectParserUtil
 * @description:
 * @author: TaoLi
 * @date: 2015-5-23 下午3:39:23
 */

public class JsonObjectParserUtil {
    private String strJson;
    private CourseTest courseTest;

    public JsonObjectParserUtil(String strJson) {
        this.strJson = strJson;
    }

    public CourseTest ParserJsonObject() {
        {
            try {
                JSONObject jsonObject = new JSONObject(strJson);
                List<ChoiceQuestion> questionsList = new ArrayList<ChoiceQuestion>();
                JSONArray jsonArray = (JSONArray) jsonObject.get("QuestionList");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                    ChoiceQuestion choiceQuestion = new ChoiceQuestion();
                    choiceQuestion.setQuestionNum(jsonObject2.getInt("mQuestionNum"));
                    choiceQuestion.setQuestion(jsonObject2.getString("mQuestion"));
                    choiceQuestion.setOptionA(jsonObject2.getString("mOptionA"));
                    choiceQuestion.setOptionB(jsonObject2.getString("mOptionB"));
                    choiceQuestion.setOptionC(jsonObject2.getString("mOptionC"));
                    choiceQuestion.setOptionD(jsonObject2.getString("mOptionD"));
                    questionsList.add(choiceQuestion);
                }
                courseTest =
                        new CourseTest(jsonObject.getString("mTestName"), questionsList,
                                jsonObject.getInt("mTestTime"), jsonObject.getString("mTestDate"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return courseTest;
    }
}
