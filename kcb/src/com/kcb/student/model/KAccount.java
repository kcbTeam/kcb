package com.kcb.student.model;

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
    private static final String STU_ACCOUNT = "com.kcb.sharedpreference.stuaccount";

    private static final String KEY_STU_ID = "key_stuid";
    private static final String KEY_STU_NAME = "key_stuname";
    private static final String KEY_TCH_ID = "key_tchid";
    private static final String KEY_TCH_NAME = "key_tchname";

    /**
     * common account content, don't need save password local;
     */
    private String mId;
    private String mName;
    private String mTchId;
    private String mTchName;

    public KAccount(String stuId, String stuName, String tchId, String tchName) {
        mId = stuId;
        mName = stuName;
        mTchId = tchId;
        mTchName = tchName;
    }

    /**
     * below are some static util functions: save/delete/has/get account
     */
    public static void saveAccount(KAccount account) {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(STU_ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = sPreferences.edit();
        editor.putString(KEY_STU_ID, account.mId);
        editor.putString(KEY_STU_NAME, account.mName);
        editor.putString(KEY_TCH_ID, account.mTchId);
        editor.putString(KEY_TCH_NAME, account.mTchName);
        editor.commit();
    }

    /**
     * 退出账号的时候，删除账号信息；
     */
    public static void clear() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(STU_ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = sPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean hasAccount() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(STU_ACCOUNT, Context.MODE_PRIVATE);
        return !TextUtils.isEmpty(sPreferences.getString(KEY_STU_ID, ""));
    }

    public static KAccount getAccount() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(STU_ACCOUNT, Context.MODE_PRIVATE);
        String id = sPreferences.getString(KEY_STU_ID, "");
        String name = sPreferences.getString(KEY_STU_NAME, "");
        String tchid = sPreferences.getString(KEY_TCH_ID, "");
        String tchname = sPreferences.getString(KEY_TCH_NAME, "");
        return new KAccount(id, name, tchid, tchname);
    }

    public static String getAccountId() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(STU_ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getString(KEY_STU_ID, "");
    }

    public static String getAccountName() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(STU_ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getString(KEY_STU_NAME, "");
    }

    public static String getTchId() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(STU_ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getString(KEY_TCH_ID, "");
    }

    public static String getTchName() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(STU_ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getString(KEY_TCH_NAME, "");
    }
}
