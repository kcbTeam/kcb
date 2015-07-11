package com.kcb.teacher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.kcb.common.application.KApplication;

/**
 * 
 * @className: SharedPreferenceUtil
 * @description: 老师模块的SharePreference管理类；所有的SharedPreference数据通过此类管理；
 * @author: wanghang
 * @date: 2015-7-11 下午4:55:27
 */
public class SharedPreferenceUtil {

    private static final String PREF_NAME = "kcb_tch";

    /**
     * 退出账号的时候，删除所有的数据；
     */
    public static void clear() {
        setFeedbackContent(""); // 删除意见反馈数据；
    }

    /**
     * 意见反馈；
     */
    private static final String PREF_KEY_FEEDBACK = "feedback";

    public static void setFeedbackContent(String feedback) {
        SharedPreferences preferences =
                KApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(PREF_KEY_FEEDBACK, feedback);
        editor.commit();
    }

    public static String getFeedbackContent() {
        SharedPreferences preferences =
                KApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_KEY_FEEDBACK, "");
    }
}
