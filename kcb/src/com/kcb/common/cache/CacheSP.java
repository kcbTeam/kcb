package com.kcb.common.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.kcb.common.application.KApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zqj on 2016/1/21 15:39.
 */
public class CacheSP {
    private static final String CACHE_SP_NAME = "kcb_stu_cache";

    private static final String KEY_STU_TEST_LIST = "key_stu_test_list";

    public static void saveStuTestListJson(String stuTestListString) {
        saveString(stuTestListString, KEY_STU_TEST_LIST);
    }

    public static JSONObject getStuTestListJson() {
        SharedPreferences preferences =
                KApplication.getInstance().getSharedPreferences(CACHE_SP_NAME, Context.MODE_PRIVATE);
        String content = preferences.getString(KEY_STU_TEST_LIST, "");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(content);
        } catch (JSONException e) {
        }
        return jsonObject;
    }


    public static void clear(){
        SharedPreferences preferences =
                KApplication.getInstance().getSharedPreferences(CACHE_SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    private static void saveString(String content, String key) {
        SharedPreferences preferences =
                KApplication.getInstance().getSharedPreferences(CACHE_SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, content);
        editor.commit();
    }


}
