package com.kcb.common.model.answer;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void setIsSelected(QuestionItem item) {
        mIsSelected = item.isSelected();
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    /**
     * choice check info to jsonobject
     */
    public static final String KEY_ID = "id";
    public static final String KEY_ISSELECTED = "isselected";

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ID, mId);
            jsonObject.put(KEY_ISSELECTED, mIsSelected);
        } catch (JSONException e) {}
        return jsonObject;
    }
}
