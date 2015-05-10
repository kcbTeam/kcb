package com.kcb.student.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.library.view.PaperButton;
import com.kcb.student.adapter.TestRecycleAdapter;
import com.kcb.student.fragment.TestChoiceFragment;
import com.kcb.student.fragment.TestShortAnwFragment;
import com.kcbTeam.R;

public class TestActivity extends BaseFragmentActivity {

    private TextView timeTextView;
    private RecyclerView recyclerView;
    private TestRecycleAdapter mAdapter;
    private PaperButton preButton;
    private PaperButton nextButton;
    private List<String> colorItems;
    private int currentPageIndex;
    private List<Fragment> mFragments;
    private FragmentManager fragmentManager;
    private static int questionNum = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_test);
        initView();
    }

    @Override
    protected void initView() {
        preButton = (PaperButton) findViewById(R.id.button_previous);
        preButton.setColor(Color.parseColor("#808080"));// color of grey
        preButton.setOnClickListener(this);
        nextButton = (PaperButton) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, questionNum));
        setTimeCounterDown();
        colorListSet();
        mAdapter = new TestRecycleAdapter(colorItems);
        recyclerView.setAdapter(mAdapter);
        getFragments();

    }

    // Set the horizontal scroll bar' color
    public void colorListSet() {
        colorItems = new ArrayList<String>();
        colorItems.add(new String("#db7093"));// color of amaranthine
        colorItems.add(new String("#ffffff"));// color of white
        colorItems.add(new String("#ffffff"));
        colorItems.add(new String("#ffffff"));
        colorItems.add(new String("#ffffff"));
    }

    // Set the countdown Timer,5 minutes
    public void setTimeCounterDown() {
        timeTextView = (TextView) findViewById(R.id.textview_timecounter);
        fiveCountDownTimer timeCountDown = new fiveCountDownTimer(300000, 1000);
        timeCountDown.start();
    }

    public void getFragments() {
        getTestContentFromNet();
        String questionOne = new String("这是第一题，我想试试这个到底可不可以滑动，如果不可以的话，就是说十分烂的设计了");
        String questionTwo = new String("这是第二题");
        String questionThree = new String("这是第三题");
        String answerOne1 = new String("A.这是第一题的第1个答案");
        String answerOne2 = new String("B.这是第一题的第2个答案");
        String answerOne3 = new String("C.这是第一题的第3个答案");
        String answerOne4 = new String("D.这是第一题的第4个答案");
        String answerTwo1 = new String("A.这是第二题的第1个答案");
        String answerTwo2 = new String("B.这是第二题的第2个答案");
        String answerTwo3 = new String("C.这是第二题的第3个答案");
        String answerTwo4 = new String("D.这是第二题的第4个答案");
        String answerThree1 = new String("A.这是第三题的第1个答案");
        String answerThree2 = new String("B.这是第三题的第2个答案");
        String answerThree3 = new String("C.这是第三题的第3个答案");
        String answerThree4 = new String("D.这是第三题的第4个答案");
        String[] choiceQ1 =
                new String[] {questionOne, answerOne1, answerOne2, answerOne3, answerOne4};
        String[] choiceQ2 =
                new String[] {questionTwo, answerTwo1, answerTwo2, answerTwo3, answerTwo4};
        String[] choiceQ3 =
                new String[] {questionThree, answerThree1, answerThree2, answerThree3, answerThree4};
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new TestChoiceFragment().newInstance(choiceQ1));
        mFragments.add(new TestShortAnwFragment());
        mFragments.add(new TestChoiceFragment().newInstance(choiceQ2));
        mFragments.add(new TestShortAnwFragment());
        mFragments.add(new TestChoiceFragment().newInstance(choiceQ3));
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(R.id.fragment_content, mFragments.get(currentPageIndex));
        beginTransaction.commit();
    }

    public void getTestContentFromNet() {


    }

    @Override
    protected void initData() {}

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_previous:
                clickPreButton();
                break;
            case R.id.button_next:
                clickNextButton();
                break;
            default:
                break;
        }

    }

    public void clickPreButton() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.replace(R.id.fragment_content, mFragments.get(currentPageIndex));
            beginTransaction.commit();
            colorItems.set(currentPageIndex, "#db7093");
            mAdapter.notifyItemChanged(currentPageIndex);
            colorItems.set(currentPageIndex + 1, "#ffffff");
            mAdapter.notifyItemChanged(currentPageIndex + 1);
            if (currentPageIndex == 0) {
                preButton.setColor(Color.parseColor("#808080"));
            }
            if (currentPageIndex == questionNum - 1)
                nextButton.setText("已完成");
            else
                nextButton.setText("下一题");
        }
    }

    public void clickNextButton() {

        if (currentPageIndex < questionNum - 1) {
            currentPageIndex++;
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.replace(R.id.fragment_content, mFragments.get(currentPageIndex));
            beginTransaction.commit();
            colorItems.set(currentPageIndex - 1, "#ffffff");
            mAdapter.notifyItemChanged(currentPageIndex - 1);
            colorItems.set(currentPageIndex, "#db7093");
            mAdapter.notifyItemChanged(currentPageIndex);
            if (currentPageIndex == questionNum - 1)
                nextButton.setText("已完成");
            else {
                nextButton.setText("下一题");
                preButton.setColor(Color.parseColor("#ffffff"));
            }
            preButton.setText("上一题");
        } else {
            new AlertDialog.Builder(TestActivity.this).setTitle(R.string.tip)
                    .setMessage(R.string.if_submit_answer).setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Submit data to database
                            dialog.cancel();
                            Intent intent = new Intent(TestActivity.this, CommitAnwActivity.class);
                            startActivity(intent);
                        }
                    }).create().show();
        }

    }

    protected class fiveCountDownTimer extends CountDownTimer {

        public fiveCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeTextView.setText(millisUntilFinished / 60000 + ":" + millisUntilFinished % 60000
                    / 1000);
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
