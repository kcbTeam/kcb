package com.kcb.student.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb.common.activity.AboutusActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.student.activity.common.FeedBackActivity;
import com.kcb.student.activity.common.HomeActivity;
import com.kcb.student.activity.common.LoginActivity;
import com.kcb.student.activity.common.ModifyPasswordActivity;
import com.kcb.student.database.checkin.CheckInDao;
import com.kcb.student.database.test.TestDao;
import com.kcb.student.model.KAccount;
import com.kcb.student.util.SharedPreferenceUtil;
import com.kcbTeam.R;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * 
 * @className: LeftDrawerLayout
 * @description: 主页的侧边栏
 * @author: wanghang
 * @date: 2015-7-6 下午12:41:23
 */
public class LeftDrawerLayout extends LinearLayout implements OnClickListener {

    public LeftDrawerLayout(Context context) {
        super(context);
        init(context);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private TextView userNameTextView;
    private TextView idTextView;

    private TextView universityTextView;
    private TextView courseTextView;
    private TextView teacherNameTextView;

    private ButtonFlat switchSourceButton;
    private ButtonFlat modifyPasswordButton;
    private ButtonFlat feedbackButton;
    private ButtonFlat updateAppButton;
    private ButtonFlat exitAccountButton;
    private ButtonFlat aboutusButton;

    private Context mContext;

    private void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.stu_activity_home_leftdrawer, this);
        initView();
    }

    private void initView() {
        userNameTextView = (TextView) findViewById(R.id.textview_username);
        userNameTextView.setText(KAccount.getAccountName());
        idTextView = (TextView) findViewById(R.id.textview_id);
        idTextView.setText(KAccount.getAccountId());

        universityTextView = (TextView) findViewById(R.id.textview_university);
        universityTextView.setText("东南大学");
        courseTextView = (TextView) findViewById(R.id.textview_course);
        courseTextView.setText("数字信号处理");
        teacherNameTextView = (TextView) findViewById(R.id.textview_teachername);
        teacherNameTextView.setText(KAccount.getTchName());

        switchSourceButton = (ButtonFlat) findViewById(R.id.button_switchsource);
        switchSourceButton.setOnClickListener(this);
        switchSourceButton.setRippleColor(getResources().getColor(R.color.black_300));

        modifyPasswordButton = (ButtonFlat) findViewById(R.id.button_modifypassword);
        modifyPasswordButton.setOnClickListener(this);
        modifyPasswordButton.setRippleColor(getResources().getColor(R.color.black_300));

        feedbackButton = (ButtonFlat) findViewById(R.id.button_feedback);
        feedbackButton.setOnClickListener(this);
        feedbackButton.setRippleColor(getResources().getColor(R.color.black_300));

        updateAppButton = (ButtonFlat) findViewById(R.id.button_updateapp);
        updateAppButton.setOnClickListener(this);
        updateAppButton.setRippleColor(getResources().getColor(R.color.black_300));

        exitAccountButton = (ButtonFlat) findViewById(R.id.button_exitaccount);;
        exitAccountButton.setOnClickListener(this);
        exitAccountButton.setRippleColor(getResources().getColor(R.color.black_300));

        aboutusButton = (ButtonFlat) findViewById(R.id.button_aboutus);
        aboutusButton.setOnClickListener(this);
        aboutusButton.setRippleColor(getResources().getColor(R.color.black_300));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_switchsource:

                break;
            case R.id.button_modifypassword:
                Intent intent = new Intent(mContext, ModifyPasswordActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.button_feedback:
                Intent intent2 = new Intent(mContext, FeedBackActivity.class);
                mContext.startActivity(intent2);
                break;
            case R.id.button_updateapp:
                updateApp();
                break;
            case R.id.button_exitaccount:
                exitAccount();
                break;
            case R.id.button_aboutus:
                AboutusActivity.startStu(mContext);
                break;
            default:
                break;
        }
    }

    /**
     * 更新app
     */
    private void updateApp() {
        // 不自动弹出更新框，有更新的时候再弹出
        UmengUpdateAgent.setUpdateAutoPopup(false);
        // 任何网络都允许更新
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        // 监听检测更新的结果
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // 有更新
                        UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
                        break;
                    case UpdateStatus.No: // 没有更新
                        ToastUtil.toast(R.string.stu_has_newest);
                        break;
                    case UpdateStatus.Timeout: // 超时，网络问题
                        ToastUtil.toast(R.string.network_error);
                        break;
                }
            }
        });
        // 开始检查更新
        UmengUpdateAgent.update(mContext);
    }

    private void exitAccount() {
        DialogUtil.showNormalDialog(mContext, R.string.stu_drawer_exit_account,
                R.string.stu_exit_account_tip, R.string.stu_comm_sure, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 删除账户信息
                        KAccount.clear();

                        // 删除签到结果
                        CheckInDao checkInDao = new CheckInDao(mContext);
                        checkInDao.deleteAll();
                        checkInDao.close();

                        // 删除测试结果
                        TestDao testDao = new TestDao(mContext);
                        testDao.deleteAll();
                        testDao.close();

                        // 删除SharedPreference数据
                        SharedPreferenceUtil.clear();

                        // 显示登录页面
                        LoginActivity.start(mContext);

                        // 销毁主界面
                        ((HomeActivity) mContext).finish();
                    }
                }, R.string.stu_comm_cancel, null);
    }
}
