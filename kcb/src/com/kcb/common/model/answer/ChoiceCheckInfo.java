package com.kcb.common.model.answer;

import com.kcb.common.model.test.QuestionItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @className: ChoiceCheckInfo
 * @description: each choice's select info in a question
 * @author: wanghang
 * @date: 2015-6-4 下午1:16:14
 */
public class ChoiceCheckInfo {

    private int mId; // choice id
    private String mkeyValue;
    private boolean mIsSelected; // stu select it or not

    public ChoiceCheckInfo() {}

    public ChoiceCheckInfo(QuestionItem item) {
        mId = item.getId();
        mkeyValue = item.getKeyValue();
    }

    public void setIsSelected(QuestionItem item) {
        mIsSelected = item.isSelected();
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public String getkeyValue() {
        return mkeyValue;
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

    public static ChoiceCheckInfo fromJsonObject(JSONObject jsonObject) {
        ChoiceCheckInfo checkInfo = new ChoiceCheckInfo();
        checkInfo.mId = jsonObject.optInt(KEY_ID);
        checkInfo.mIsSelected = jsonObject.optBoolean(KEY_ISSELECTED);
        return checkInfo;
    }
}
