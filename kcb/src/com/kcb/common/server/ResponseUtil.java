package com.kcb.common.server;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
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

    /**
     * 
     * @title: checkError
     * @description: check volley response and tip
     * @author: wanghang
     * @date: 2015-5-17 下午12:34:49
     * @param error
     */
    public static void toastError(VolleyError error) {
        if (isNetworkProblem(error)) {
            ToastUtil.toast(R.string.network_error);
        }
        if (isServerProblem(error)) {
            ToastUtil.toast(R.string.server_error);
        }
    }

    private static boolean isNetworkProblem(VolleyError error) {
        return (error instanceof TimeoutError) || (error instanceof NetworkError)
                || (error instanceof NoConnectionError);
    }

    private static boolean isServerProblem(VolleyError error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }
}
