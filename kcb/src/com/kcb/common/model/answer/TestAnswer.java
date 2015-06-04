package com.kcb.common.model.answer;

import java.util.List;

import com.kcb.common.model.test.Test;

/**
 * 
 * @className: TestAnswer
 * @description: answer for a test
 * @author: wanghang
 * @date: 2015-6-4 下午1:42:22
 */
public class TestAnswer {

    private String mId; // test id
    private List<QuestionAnswer> mQuestionAnswers;

    public TestAnswer(Test test) {
        mId = test.getId();
        for (int i = 0; i < test.getQuestionNum(); i++) {
            mQuestionAnswers.add(new QuestionAnswer(test.getQuestion(i)));
        }
    }
}
