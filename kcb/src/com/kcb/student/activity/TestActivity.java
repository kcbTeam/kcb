package com.kcb.student.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.student.adapter.TestRecycleAdapter;
import com.kcb.student.fragment.TestChoiceFragment;
import com.kcb.student.fragment.TestShortAnwFragment;
import com.kcbTeam.R;

// TODO 如果是最后一题，右边的按钮变成"完成"。
// 没有简答题，只有选择题。
// 提交答案的对话框，参考HomeActivity中的注销对话框。
// 每一道题目不需要一个Fragment，这个问一下章勤杰，他的出题页面讨论后做了改动。
public class TestActivity extends BaseFragmentActivity {

    private TextView timeTextView;
    private RecyclerView recyclerView;
    private TestRecycleAdapter mAdapter;
    private Button preButton;
    private Button nextButton;
    private int questionNum = 5;
    private int currentPageIndex;
    private List<Fragment> mFragments;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_test);
        initView();
    }

    @Override
    protected void initView() {
        preButton = (Button) findViewById(R.id.button_previous);
        preButton.setOnClickListener(this);
        nextButton = (Button) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        timeTextView = (TextView) findViewById(R.id.textview_timecounter);
        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, questionNum));
        fiveCountDownTimer timeCountDown = new fiveCountDownTimer(300000, 1000);
        timeCountDown.start();
        mAdapter = new TestRecycleAdapter(questionNum);
        recyclerView.setAdapter(mAdapter);
        // TODO request data from Network
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new TestChoiceFragment());
        mFragments.add(new TestShortAnwFragment());
        mFragments.add(new TestChoiceFragment());
        mFragments.add(new TestShortAnwFragment());
        mFragments.add(new TestChoiceFragment());
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(R.id.fragment_content, mFragments.get(currentPageIndex));
        beginTransaction.commit();
    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_previous: {
                if (currentPageIndex > 0) {
                    currentPageIndex--;
                    FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                    beginTransaction.replace(R.id.fragment_content,
                            mFragments.get(currentPageIndex));
                    beginTransaction.commit();
                    mAdapter.setCurrentIndex(currentPageIndex);
                } else {
                    ToastUtil.toast(R.string.first_question);
                }
            }
                break;
            case R.id.button_next: {
                if (currentPageIndex < 4) {
                    currentPageIndex++;
                    FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                    beginTransaction.replace(R.id.fragment_content,
                            mFragments.get(currentPageIndex));
                    beginTransaction.commit();
                    mAdapter.setCurrentIndex(currentPageIndex);
                } else {
                    ToastUtil.toast(R.string.last_question);
                    new AlertDialog.Builder(TestActivity.this)
                            .setTitle(R.string.tip)
                            .setMessage(R.string.if_submit_answer)
                            .setNegativeButton(R.string.cancel, null)
                            .setPositiveButton(R.string.confirm,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent(TestActivity.this,SubmitActivity.class);
                                            startActivity(intent);
                                            dialog.cancel();
                                            finish();
                                        }
                                    }).create().show();
                }
            }
                break;
            default:
                break;
        }
    }

    protected class fiveCountDownTimer extends CountDownTimer {

        public fiveCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeTextView.setText("倒计时：" + millisUntilFinished / 60000 + ":" + millisUntilFinished
                    % 60000 / 1000);
        }

        @Override
        public void onFinish() {
            timeTextView.setText(R.string.end);
            new AlertDialog.Builder(TestActivity.this).setTitle(R.string.tip)
                    .setMessage(R.string.to_submit_answer)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Submit data to network
                            dialog.cancel();
                        }
                    }).create().show();
        }
    }
}
