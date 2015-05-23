package com.kcb.student.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;

public class JsonParserUtil {

    public static CourseTest JsonParser(String jsonData) {
        JsonReader reader = new JsonReader(new StringReader(jsonData));
        List<ChoiceQuestion> questionsList = new ArrayList<ChoiceQuestion>();
        String nameString = "";
        String dataString = "";
        int timeInt = 0;
        CourseTest courseTest;
        {
            try {
                reader.beginObject();/* 开始解析对象 */
                while (reader.hasNext()) {
                    String tagName = reader.nextName(); /* 得到键值对中的key */
                    if (tagName.equals("mTestName")) {
                        nameString = reader.nextString(); /* 得到key中的内容 */
                    } else if (tagName.equals("mTestDate"))/* key为data时 */
                    {
                        dataString = reader.nextString(); /* 得到key中的内容 */
                    } else if (tagName.equals("mTestTime"))/* key为time时 */
                    {
                        timeInt = reader.nextInt(); /* 得到key中的内容 */
                    } else if (tagName.equals("QuestionList"))/* key为list时 */
                    {
                        reader.beginArray(); /* 开始解析题目数组 */
                        while (reader.hasNext()) {
                            reader.beginObject(); /* 开始解析对象 */
                            ChoiceQuestion choiceQuestion = new ChoiceQuestion();
                            while (reader.hasNext()) {
                                String tagName1 = reader.nextName(); /* 得到键值对中的key */
                                /* key为number时 */
                                if (tagName1.equals("mQuestionNum")) {
                                    choiceQuestion.setQuestionNum(reader.nextInt()); /* 得到key中的内容 */
                                } else if (tagName1.equals("mQuestion"))/* key为question时 */
                                {
                                    choiceQuestion.setQuestion(reader.nextString()); /* 得到key中的内容 */
                                } else if (tagName1.equals("mOptionA"))/* key为anawerA时 */
                                {
                                    choiceQuestion.setOptionA(reader.nextString()); /* 得到key中的内容 */
                                } else if (tagName1.equals("mOptionB"))/* key为anawerB时 */
                                {
                                    choiceQuestion.setOptionB(reader.nextString()); /* 得到key中的内容 */
                                } else if (tagName1.equals("mOptionC"))/* key为anawerC时 */
                                {
                                    choiceQuestion.setOptionC(reader.nextString()); /* 得到key中的内容 */
                                } else if (tagName1.equals("mOptionD"))/* key为anawerD时 */
                                {
                                    choiceQuestion.setOptionD(reader.nextString()); /* 得到key中的内容 */
                                }
                            }
                            reader.endObject();
                            questionsList.add(choiceQuestion);
                        }
                        reader.endArray();
                    }
                }
                reader.endObject();/* 结束解析对象 */
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        courseTest = new CourseTest(nameString, questionsList, timeInt, dataString);
        return courseTest;
    }
}
