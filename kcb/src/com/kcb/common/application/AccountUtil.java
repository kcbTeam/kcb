package com.kcb.common.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.kcb.student.model.account.KAccount;

public class AccountUtil {

    /**
     * account type
     */
    public static final int TYPE_STU = 0;
    public static final int TYPE_TCH = 1;

    public static final String ACCOUNT = "com.kcb.sharedpreference.account";
    public static final String KEY_TYPE = "key_type";

    public static void setAccountType(int type) {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = sPreferences.edit();
        editor.putInt(KEY_TYPE, type);
        editor.commit();
    }

    public static int getAccountType() {
        SharedPreferences sPreferences =
                KApplication.getInstance().getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        return sPreferences.getInt(KEY_TYPE, -1);
    }

    public static boolean hasAccount() {
        return KAccount.hasAccount() || com.kcb.teacher.model.account.KAccount.hasAccount();
    }
}
