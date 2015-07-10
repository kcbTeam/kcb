package com.kcb.teacher.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.kcb.common.application.KApplication;

/**
 * 
 * @className: KAccount
 * @description: stu/tch login success, save their account in local SharedPreference; when start
 *               app, detect if need login according account; when user exit current account in
 *               setting, delete saved account;
 * @author: wanghang
 * @date: 2015-4-29 下午8:41:44
 */
public class KAccount {

    /**
     * sharedPreference keys
     */
    private static final String TCH_ACCOUNT = "com.kcb.sharedpreference.tchaccount";

    private static final String KEY_ID = "key_id";
    private static final String KEY_NAME = "key_name";

    /**
     * common account content, don't need save password local;
     */
    private String mId;
    private String mName;

    public KAccount(String tchId, String tchName) {
        mId = tchId;
        mName = tchName;
    }

    /**
     * below are some static util functions: save/delete/has/get account
     */
    public static void saveAccount(KAccount account) {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(TCH_ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = sPreferences.edit();
        editor.putString(KEY_ID, account.mId);
        editor.putString(KEY_NAME, account.mName);
        editor.commit();
    }

    public static void deleteAccount() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(TCH_ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = sPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean hasAccount() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(TCH_ACCOUNT, Context.MODE_PRIVATE);
        return !TextUtils.isEmpty(sPreferences.getString(KEY_ID, ""));
    }

    public static KAccount getAccount() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(TCH_ACCOUNT, Context.MODE_PRIVATE);
        String id = sPreferences.getString(KEY_ID, "");
        String name = sPreferences.getString(KEY_NAME, "");
        return new KAccount(id, name);
    }

    public static String getAccountId() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(TCH_ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getString(KEY_ID, "");
    }

    public static String getAccountName() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(TCH_ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getString(KEY_NAME, "");
    }
}
