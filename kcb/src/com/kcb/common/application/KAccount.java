package com.kcb.common.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * 
 * @className: KAccount
 * @description: account save current user info;
 * @author: wanghang
 * @date: 2015-4-29 下午8:41:44
 */
public class KAccount {

    private static final String ACCOUNT = "account";

    private static final String TYPE = "type";
    private static final String ID = "id";
    private static final String NAME = "name";

    public static final int TYPE_STU = 0;
    public static final int TYPE_TCH = 1;

    private int mType;
    private String mId;
    private String mName;

    public KAccount(int type, String id, String name) {
        mType = type;
        mId = id;
        mName = name;
    }

    /**
     * below are some static util functions;
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

    public static int getAccountType() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getInt(TYPE, TYPE_STU);
    }
}
