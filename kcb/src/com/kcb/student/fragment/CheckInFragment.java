package com.kcb.student.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.LogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.student.activity.checkin.LookCheckInActivity;
import com.kcb.student.activity.checkin.StartCheckInActivity;
import com.kcb.student.database.checkin.CheckInDao;
import com.kcb.student.database.checkin.CheckInResult;
import com.kcb.student.model.KAccount;
import com.kcbTeam.R;

/**
 * @className: SignFragment
 * @description: Sign in fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:01
 */
public class CheckInFragment extends BaseFragment {

    private final String TAG = CheckInFragment.class.getName();

    private PaperButton startCheckInButton;
    private SmoothProgressBar progressBar;

    private PaperButton lookCheckInButton;

    // TODO add 2015-9-6
    private final int MAX_REMAINTIME = 120;
    public static String ckid;
    public static String num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        return inflater.inflate(R.layout.stu_fragment_checkin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    @Override
    protected void initView() {
        View view = getView();
        startCheckInButton = (PaperButton) view.findViewById(R.id.button_start_checkin);
        startCheckInButton.setOnClickListener(mClickListener);
        progressBar = (SmoothProgressBar) view.findViewById(R.id.progressbar_start_checkin);

        lookCheckInButton = (PaperButton) view.findViewById(R.id.button_look_checkin);
        lookCheckInButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_start_checkin:
                    startCheckIn();
                    break;
                case R.id.button_look_checkin:
                    LookCheckInActivity.start(getActivity());
                    break;
                default:
                    break;
            }
        }
    };

    public void startCheckIn() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request =
                new JsonObjectRequest(Method.POST, UrlUtil.getStuCheckinStartUrl(
                        KAccount.getAccountId(), KAccount.getTchId()), new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        // TODO add 2015-9-6
                        long remain = getRemianTime(response.optString("creatdate"));
                        ckid = response.optString("ckid");
                        num = response.optString("num");
                        if (remain <= 0) {
                            ToastUtil.toast(R.string.stu_checkin_time_end);
                            return;
                        }
                        final String remainTime = String.valueOf(remain);
                        LogUtil.i(TAG, "start checkin, remainTime is " + remainTime);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                progressBar.setVisibility(View.INVISIBLE);
                                StartCheckInActivity.start(getActivity(), remainTime);
                            }
                        }, 500);
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.hide(getActivity());
                        NetworkResponse networkResponse = error.networkResponse;
                        if (null != networkResponse) {
                            int statusCode = networkResponse.statusCode;
                            if (statusCode == 400) {
                                ToastUtil.toast(R.string.stu_no_checkin);
                                LogUtil.e(TAG, getString(R.string.stu_no_checkin));
                            } else if (statusCode == 401) {
                                ToastUtil.toast(R.string.stu_has_checkin);
                                LogUtil.e(TAG, getString(R.string.stu_has_checkin));
                            } else if (statusCode == 201) {

                            } else {
                                ResponseUtil.toastError(error);
                            }
                        } else {
                            ResponseUtil.toastError(error);
                        }
                    }
                });
        // StringRequest request1 =
        // new StringRequest(Method.POST, UrlUtil.getStuCheckinStartUrl(
        // KAccount.getAccountId(), KAccount.getTchId()), new Listener<String>() {
        // public void onResponse(final String remainTime) {
        // LogUtil.i(TAG, "start checkin, remainTime is " + remainTime);
        // new Handler().postDelayed(new Runnable() {
        //
        // @Override
        // public void run() {
        // progressBar.setVisibility(View.INVISIBLE);
        // StartCheckInActivity.start(getActivity(), remainTime);
        // }
        // }, 500);
        // };
        // }, new ErrorListener() {
        // public void onErrorResponse(VolleyError error) {
        // progressBar.hide(getActivity());
        // NetworkResponse networkResponse = error.networkResponse;
        // if (null != networkResponse) {
        // int statusCode = networkResponse.statusCode;
        // if (statusCode == 400) {
        // ToastUtil.toast(R.string.stu_no_checkin);
        // LogUtil.e(TAG, getString(R.string.stu_no_checkin));
        // } else if (statusCode == 401) {
        // ToastUtil.toast(R.string.stu_has_checkin);
        // LogUtil.e(TAG, getString(R.string.stu_has_checkin));
        // } else if(statusCode == 201){
        //
        // }else {
        // ResponseUtil.toastError(error);
        // }
        // } else {
        // ResponseUtil.toastError(error);
        // }
        // };
        // });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
        mClickListener = null;
    }

    /**
     * 
     * @title: getRemianTime
     * @description: 由后台返回的创建时间计算剩余时间
     * @author: Zqj
     * @date: 2015年9月6日 下午4:16:08
     * @param createDate
     * @return
     */
    private long getRemianTime(String createDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long creatdata = 0;
        try {
            creatdata = sdf.parse(createDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long data = System.currentTimeMillis();
        return MAX_REMAINTIME - (data - creatdata) / 1000;
    }
}
