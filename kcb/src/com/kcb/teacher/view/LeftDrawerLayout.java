package com.kcb.teacher.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb.common.util.DialogUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.activity.common.FeedBackActivity;
import com.kcb.teacher.activity.common.HomeActivity;
import com.kcb.teacher.activity.common.LoginActivity;
import com.kcb.teacher.activity.common.ModifyPasswordActivity;
import com.kcb.teacher.database.checkin.CheckInDao;
import com.kcb.teacher.database.students.StudentDao;
import com.kcb.teacher.database.test.TestDao;
import com.kcb.teacher.model.KAccount;
import com.kcbTeam.R;

// TODO 分享一下、牛人排行
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

    private TextView universityTextview;
    private TextView courseTextView;
    private TextView classTextView;

    private ButtonFlat switchSourceButton;
    private ButtonFlat modifyPasswordButton;
    private ButtonFlat feedbackButton;
    private ButtonFlat updateAppButton;
    private ButtonFlat exitAccountButton;

    private Context mContext;

    private void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.tch_activity_home_leftdrawer, this);
        initView();
    }

    private void initView() {
        userNameTextView = (TextView) findViewById(R.id.textview_username);
        userNameTextView.setText(KAccount.getAccountName());
        idTextView = (TextView) findViewById(R.id.textview_id);
        idTextView.setText(KAccount.getAccountId());

        universityTextview = (TextView) findViewById(R.id.textview_university);
        universityTextview.setText("东南大学");
        courseTextView = (TextView) findViewById(R.id.textview_course);
        courseTextView.setText("数字信号处理");
        classTextView = (TextView) findViewById(R.id.textview_class);
        classTextView.setText("Y22010220");

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

                break;
            case R.id.button_exitaccount:
                exitAccount();
                break;
            default:
                break;
        }
    }

    private void exitAccount() {
        DialogUtil.showNormalDialog(mContext, R.string.tch_drawer_exit_account,
                R.string.tch_exit_account_tip, R.string.tch_comm_sure, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // delete account
                        KAccount.deleteAccount();

                        // delete checkin result
                        CheckInDao checkInDao = new CheckInDao(mContext);
                        checkInDao.deleteAll();
                        checkInDao.close();

                        // delete test result
                        TestDao testDao = new TestDao(mContext);
                        testDao.deleteAll();
                        testDao.close();

                        // delete student
                        StudentDao studentDao = new StudentDao(mContext);
                        studentDao.deleteAll();
                        studentDao.close();

                        // goto login activity
                        LoginActivity.start(mContext);
                        ((HomeActivity) mContext).finish();
                    }
                }, R.string.tch_comm_cancel, null);
    }
}
