package com.kcb.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.kcb.common.application.KApplication;
import com.kcb.student.model.KAccount;

/**
 * 
 * @className: AccountUtil
 * @description: 公共的账户管理类，判断用户是否登录了，登录的用户类型；
 * @author: wanghang
 * @date: 2015-5-11 下午6:27:38
 */
public class AccountUtil {

    /**
     * account type
     */
    public static final int TYPE_STU = 0;
    public static final int TYPE_TCH = 1;

    public static final String PREF_NAME = "com.kcb.sharedpreference.account";
    public static final String PREF_KEY_TYPE = "key_type";

    public static void setAccountType(int type) {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = sPreferences.edit();
        editor.putInt(PREF_KEY_TYPE, type);
        editor.commit();
    }

    public static int getAccountType() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sPreferences.getInt(PREF_KEY_TYPE, -1);
    }

    public static boolean hasAccount() {
        return KAccount.hasAccount() || com.kcb.teacher.model.KAccount.hasAccount();
    }
}
