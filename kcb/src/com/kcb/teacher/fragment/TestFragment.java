package com.kcb.teacher.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kcb.common.base.BaseFragment;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.model.test.Test;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.dialog.MaterialListDialog;
import com.kcb.common.view.dialog.MaterialListDialog.OnClickSureListener;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.activity.test.LookTestActivity;
import com.kcb.teacher.activity.test.SetTestNameActivity;
import com.kcb.teacher.activity.test.SetTestTimeActivity;
import com.kcb.teacher.database.test.TestDao;
import com.kcb.teacher.model.account.KAccount;
import com.kcbTeam.R;

/**
 * 
 * @className: TestFragment
 * @description:
 * @author: ZQJ & ljx
 * @date: 2015年4月24日 下午3:24:15
 */
public class TestFragment extends BaseFragment {

    private final String TAG = TestFragment.class.getName();

    private PaperButton startTestButton;
    private SmoothProgressBar startProgressBar;

    private PaperButton addOrEditTestButton;
    private PaperButton lookTestResultButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tch_fragment_test, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    @Override
    protected void initView() {
        View view = getView();
        startTestButton = (PaperButton) view.findViewById(R.id.button_start);
        startTestButton.setOnClickListener(mClickListener);
        startProgressBar = (SmoothProgressBar) view.findViewById(R.id.progressbar_start);

        addOrEditTestButton = (PaperButton) view.findViewById(R.id.button_edit);
        addOrEditTestButton.setOnClickListener(mClickListener);

        lookTestResultButton = (PaperButton) view.findViewById(R.id.button_look_result);
        lookTestResultButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_start:
                    startTest();
                    break;
                case R.id.button_edit:
                    addOrEditTest();
                    break;
                case R.id.button_look_result:
                    Intent intent = new Intent(getActivity(), LookTestActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    private void startTest() {
        if (startProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        final List<String> names = getTestNames();
        if (names.isEmpty()) {
            ToastUtil.toast(R.string.tch_add_test_first);
        } else {
            DialogUtil.showListDialog(getActivity(), R.string.tch_select_test, names,
                    R.string.tch_comm_sure, new OnClickSureListener() {

                        @Override
                        public void onClick(View view, int position) {
                            TestDao testDao = new TestDao(getActivity());
                            Test test = testDao.getByName(names.get(position));
                            testDao.close();
                            sendTestToServer(test);
                        }
                    }, R.string.tch_comm_cancel, null);
        }
    }

    private final String KEY_ID = "id";
    private final String KEY_TEST = "test";

    private void sendTestToServer(final Test test) {
        OnClickListener sureListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startProgressBar.setVisibility(View.VISIBLE);

                JSONObject requestObject = new JSONObject();
                try {
                    requestObject.put(KEY_ID, KAccount.getAccountId());
                    test.setQuestionId();
                    requestObject.put(KEY_TEST, test.toJsonObject());
                } catch (JSONException e) {}
                JsonObjectRequest request =
                        new JsonObjectRequest(Method.POST, UrlUtil.getTchTestStartUrl(),
                                requestObject, new Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        TestDao testDao = new TestDao(getActivity());
                                        test.setHasTested(true);
                                        testDao.update(test);
                                        testDao.close();

                                        ToastUtil.toast(R.string.tch_test_started);
                                        startProgressBar.hide(getActivity());
                                    }
                                }, new ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        startProgressBar.hide(getActivity());
                                        ResponseUtil.toastError(error);
                                    }
                                });
                RequestUtil.getInstance().addToRequestQueue(request, TAG);
            }
        };
        DialogUtil.showNormalDialog(
                getActivity(),
                R.string.tch_start_test,
                String.format(getString(R.string.tch_start_test_tip), test.getName(),
                        test.getQuestionNum(), test.getTime()), R.string.tch_comm_sure,
                sureListener, R.string.tch_comm_cancel, null);
    }

    private void addOrEditTest() {
        final List<String> names = getTestNames();
        names.add(0, getString(R.string.tch_add_new_test));
        MaterialListDialog dialog =
                DialogUtil.showListDialog(getActivity(), R.string.tch_edit_test, names,
                        R.string.tch_comm_sure, new OnClickSureListener() {

                            @Override
                            public void onClick(View view, int position) {
                                if (position == 0) { // add new test
                                    Intent intent =
                                            new Intent(getActivity(), SetTestNameActivity.class);
                                    startActivity(intent);
                                } else {
                                    TestDao testDao = new TestDao(getActivity());
                                    Test test = testDao.getByName(names.get(position));
                                    testDao.close();
                                    SetTestTimeActivity.startFromEditTest(getActivity(), test);
                                }
                            }
                        }, R.string.tch_comm_cancel, null);
        dialog.enableAddOrEditMode();
    }

    private List<String> getTestNames() {
        TestDao testDao = new TestDao(getActivity());
        List<String> names = testDao.getAllTestName();
        testDao.close();
        return names;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
        mClickListener = null;
    }
}
