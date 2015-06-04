package com.kcb.common.server;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kcb.common.application.KApplication;
import com.kcb.common.util.LogUtil;

/**
 * 
 * @className: RequestUtil
 * @description: send http request based on Volley
 * @author: wanghang
 * @date: 2015-5-2 下午1:28:13
 */
public class RequestUtil {

    private String TAG_REQUEST = "volley_request";

    // singleton
    private static RequestUtil instance;

    // http request queue in volley
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

    /**
     * 
     * @title: addToRequestQueue
     * @description: if you need send a http request, just new a Volley Request and
     *               addToRequestQueue, it's easy
     * @author: wanghang
     * @date: 2015-5-14 下午1:33:16
     * @param request
     */
    public <T> void addToRequestQueue(Request<T> request) {
        addToRequestQueue(request, null);
    }

    /**
     * 
     * @title: addToRequestQueue
     * @description: if you tag one request, you can cancel it according tag
     * @author: wanghang
     * @date: 2015-5-14 下午1:34:17
     * @param request
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG_REQUEST : tag);
        mRequestQueue.add(request);
        LogUtil.i(TAG_REQUEST, "url: " + request.getUrl());
    }

    /**
     * 
     * @title: cancelPendingRequests
     * @description: cancel request according tag when activity/fragment destroy and so on
     * @author: wanghang
     * @date: 2015-5-14 下午1:35:11
     * @param tag
     */
    public void cancelPendingRequests(String tag) {
        mRequestQueue.cancelAll(tag);
    }
}
