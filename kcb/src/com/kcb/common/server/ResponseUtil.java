package com.kcb.common.server;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.kcb.common.util.ToastUtil;
import com.kcbTeam.R;

/**
 * 
 * @className: ResponseUtil
 * @description: check response error
 * @author: wanghang
 * @date: 2015-5-17 下午12:22:37
 */
public class ResponseUtil {

    public static void toastError(VolleyError error) {
        if (isNetworkProblem(error)) {
            ToastUtil.toast(R.string.network_error);
        } else {
            ToastUtil.toast(R.string.server_error);
        }
    }

    private static boolean isNetworkProblem(VolleyError error) {
        return (error instanceof TimeoutError) || (error instanceof NetworkError)
                || (error instanceof NoConnectionError);
    }
}
