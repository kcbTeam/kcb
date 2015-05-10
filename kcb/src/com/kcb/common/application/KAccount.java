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

    public static final int TYPE_STU = 0;
    public static final int TYPE_TCH = 1;

    private int mType;
    private String mId;

    public KAccount(int type, String id) {
        mType = type;
        mId = id;
    }

    public static int getAccountType() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getInt(TYPE, TYPE_STU);
    }

    public void saveAccount() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = sPreferences.edit();
        editor.putInt(TYPE, mType);
        editor.putString(ID, mId);
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
}
