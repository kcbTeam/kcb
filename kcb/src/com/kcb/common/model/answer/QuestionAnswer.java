package com.kcb.common.model.answer;

import com.kcb.common.model.test.Question;

/**
 * 
 * @className: QuestionAnswer
 * @description: answer for one question
 * @author: wanghang
 * @date: 2015-6-4 下午1:11:42
 */
public class QuestionAnswer {

    private int mId; // question id;
    private ChoiceCheckInfo aCheckInfo;
    private ChoiceCheckInfo bCheckInfo;
    private ChoiceCheckInfo cCheckInfo;
    private ChoiceCheckInfo dCheckInfo;

    public QuestionAnswer(Question question) {
        mId = question.getId();
        aCheckInfo = new ChoiceCheckInfo(question.getChoiceA());
        bCheckInfo = new ChoiceCheckInfo(question.getChoiceB());
        cCheckInfo = new ChoiceCheckInfo(question.getChoiceC());
        dCheckInfo = new ChoiceCheckInfo(question.getChoiceD());
    }
}
