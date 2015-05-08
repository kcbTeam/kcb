package com.kcb.common.server;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kcb.common.application.KApplication;
import com.kcb.common.util.LogUtil;

public class RequestUtil {

    private String TAG_REQUEST = "volley_request";

    private static RequestUtil instance;
    private RequestQueue mRequestQueue;

    public static RequestUtil getInstance() {
        if (null == instance) {
            synchronized (RequestUtil.class) {
                if (null == instance) {
                    instance = new RequestUtil();
                }
            }
        }
        return instance;
    }

    private RequestUtil() {
        mRequestQueue = Volley.newRequestQueue(KApplication.getInstance());
    }

    public <T> void addToRequestQueue(Request<T> request) {
        addToRequestQueue(request, null);
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG_REQUEST : tag);
        mRequestQueue.add(request);
        LogUtil.i(TAG_REQUEST, "url: " + request.getUrl());
    }

    public void cancelPendingRequests(String tag) {
        mRequestQueue.cancelAll(tag);
    }
}
