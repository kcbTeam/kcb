/**
 * Copyright© 2015-2020 kcbTeam. All Rights Reserved.
 * 
 * @Title: MultipartRequest.java
 * @Package com.kcb.common.server
 * @Description:
 * @author:
 * @date: 2015-7-8 上午10:05:46
 * @version V1.0
 */

package com.kcb.common.server;

import java.io.ByteArrayOutputStream;

import org.apache.http.HttpEntity;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kcb.common.util.LogUtil;

/**
 * @className: MultipartRequest
 * @description: 用于发送带多种类型参数的请求，比如既包括String、int、byte[]、又包括文件、图片等等
 * @author: wanghang
 * @date: 2015-7-8 上午10:05:46
 */
public class MultipartRequest extends JsonObjectRequest {

    private HttpEntity mHttpEntity;

    /**
     * Constructor: MultipartRequest
     */
    public MultipartRequest(String url, HttpEntity entity, Listener<JSONObject> listener,
            ErrorListener errorListener) {
        super(url, listener, errorListener);
        mHttpEntity = entity;
    }

    /**
     * Constructor: MultipartRequest
     */
    public MultipartRequest(int method, String url, HttpEntity entity,
            Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mHttpEntity = entity;
    }

    @Override
    public String getBodyContentType() {
        return mHttpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 将mHttpEntity写入到bos中
            mHttpEntity.writeTo(bos);
        } catch (Exception e) {
            LogUtil.e("", "IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }
}
