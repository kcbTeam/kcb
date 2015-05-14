package com.kcb.common.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

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
     * account type
     */
    public static final int TYPE_STU = 0;
    public static final int TYPE_TCH = 1;

    /**
     * sharedPreference keys
     */
    private static final String ACCOUNT = "account";

    private static final String TYPE = "type";
    private static final String ID = "id";
    private static final String NAME = "name";

    /**
     * account content, don't need save password local;
     */
    private int mType;
    private String mId;
    private String mName;

    public KAccount(int type, String id, String name) {
        mType = type;
        mId = id;
        mName = name;
    }

    /**
     * below are some static util functions: save/delete/has/get account
     */
    public static void saveAccount(KAccount account) {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = sPreferences.edit();
        editor.putInt(TYPE, account.mType);
        editor.putString(ID, account.mId);
        editor.putString(NAME, account.mName);
        editor.commit();
    }

    public static void deleteAccount() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = sPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean hasAccount() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        return !TextUtils.isEmpty(sPreferences.getString(ID, ""));
    }

    public static KAccount getAccount() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        int type = sPreferences.getInt(TYPE, -1);
        String id = sPreferences.getString(ID, "");
        String name = sPreferences.getString(NAME, "");
        return new KAccount(type, id, name);
    }

    public static int getAccountType() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getInt(TYPE, -1);
    }

    public static String getAccountId() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getString(ID, "");
    }

    public static String getAccountName() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getString(NAME, "");
    }
}
