package com.kcb.common.model.answer;

import com.kcb.common.model.test.QuestionItem;

/**
 * 
 * @className: ChoiceCheckInfo
 * @description: each choice's select info in a question
 * @author: wanghang
 * @date: 2015-6-4 下午1:16:14
 */
public class ChoiceCheckInfo {

    private int mId; // choice id
    private boolean mIsSelected; // stu select it or not

    public ChoiceCheckInfo(QuestionItem item) {
        mId = item.getId();
    }
}
