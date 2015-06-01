package com.kcb.student.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.application.KApplication;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.LogUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.student.adapter.TestRecycleAdapter;
import com.kcb.student.constance.DataBaseContract.FeedEntry;
import com.kcb.student.database.KCBProfileDao;
import com.kcb.student.util.IntToBooleans;
import com.kcb.student.util.RedHookDraw;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

@SuppressLint("NewApi")
public class LookTestResultActivity extends BaseActivity {

    private TextView titleTextView;
    private TextView questionTextView;
    private TextView answerATextView;
    private TextView answerBTextView;
    private TextView answerCTextView;
    private TextView answerDTextView;
    private TextView answerATv;
    private TextView answerBTv;
    private TextView answerCTv;
    private TextView answerDTv;
    private PaperButton preButton;
    private PaperButton nextButton;
    private ButtonFlat backButton;
    private ImageView checkBoxA;
    private ImageView checkBoxB;
    private ImageView checkBoxC;
    private ImageView checkBoxD;
    private Test mTest;
    private RecyclerView recyclerView;
    private TestRecycleAdapter mAdapter;
    private RedHookDraw layout;
    private int currentPageIndex;
    private int questionNum;
    public KCBProfileDao dbManager;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_looktestresult);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        titleTextView = (TextView) findViewById(R.id.testresult_title);
        questionTextView = (TextView) findViewById(R.id.textview_choice_question);
        answerATextView = (TextView) findViewById(R.id.textview_choice_a);
        answerBTextView = (TextView) findViewById(R.id.textview_choice_b);
        answerCTextView = (TextView) findViewById(R.id.textview_choice_c);
        answerDTextView = (TextView) findViewById(R.id.textview_choice_d);
        answerATv = (TextView) findViewById(R.id.textview_A);
        answerBTv = (TextView) findViewById(R.id.textview_B);
        answerCTv = (TextView) findViewById(R.id.textview_C);
        answerDTv = (TextView) findViewById(R.id.textview_D);
        preButton = (PaperButton) findViewById(R.id.button_tab_question1);
        nextButton = (PaperButton) findViewById(R.id.button_tab_question2);
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        checkBoxA = (ImageView) findViewById(R.id.checkbox_a1);
        checkBoxB = (ImageView) findViewById(R.id.checkbox_b1);
        checkBoxC = (ImageView) findViewById(R.id.checkbox_c1);
        checkBoxD = (ImageView) findViewById(R.id.checkbox_d1);
        preButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        layout = (RedHookDraw) findViewById(R.id.relativelayout_redhook);
        layout.setVisibility(View.INVISIBLE);
    }



    @Override
    protected void initData() {
        traverseQuestions();
        questionNum = mTest.getQuestionNum();
        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, questionNum));
        mAdapter = new TestRecycleAdapter(questionNum);
        recyclerView.setAdapter(mAdapter);
        showCurrentQuestion(0);
    }



    public void clickPreButton() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            mAdapter.setCurrentIndex(currentPageIndex);
            showCurrentQuestion(currentPageIndex);
        }
    }

    public void clickNextButton() {
        if (currentPageIndex < questionNum - 1) {
            currentPageIndex++;
            mAdapter.setCurrentIndex(currentPageIndex);
            showCurrentQuestion(currentPageIndex);
        } else {}
    }

    
    public void traverseQuestions() {
        Intent intent = this.getIntent();
        String string = intent.getStringExtra("testTitle1");
        titleTextView.setText(string);
        mTest = new Test(string, 0);
        dbManager = ((KApplication) getApplication()).getDB();
        dbManager.getDataDb();
        mCursor = dbManager.select(new String[] {string, "5"});
        int i=0;
        while (mCursor.moveToNext()) {
            mTest.addQuestion();
            mTest.getQuestion(i)
                    .getTitle()
                    .setText(
                            new String(mCursor.getString(mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_QUESTION))));;
            mTest.getQuestion(i)
                    .getChoiceA()
                    .setText(
                            new String(mCursor.getString(mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONA))));
            mTest.getQuestion(i)
                    .getChoiceA()
                    .setIsRight(
                            IntToBooleans.toBoolean(mCursor.getInt((mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONATF)))));
            mTest.getQuestion(i)
                    .getChoiceA()
                    .setIsRight1(
                            IntToBooleans.toBoolean(mCursor.getInt((mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONATF1)))));

            mTest.getQuestion(i)
                    .getChoiceB()
                    .setText(
                            new String(mCursor.getString(mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONB))));
            mTest.getQuestion(i)
                    .getChoiceB()
                    .setIsRight(
                            IntToBooleans.toBoolean(mCursor.getInt((mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONBTF)))));
            mTest.getQuestion(i)
                    .getChoiceB()
                    .setIsRight1(
                            IntToBooleans.toBoolean(mCursor.getInt((mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONBTF1)))));

            mTest.getQuestion(i)
                    .getChoiceC()
                    .setText(
                            new String(mCursor.getString(mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONC))));
            mTest.getQuestion(i)
                    .getChoiceC()
                    .setIsRight(
                            IntToBooleans.toBoolean(mCursor.getInt((mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONCTF)))));
            mTest.getQuestion(i)
                    .getChoiceC()
                    .setIsRight1(
                            IntToBooleans.toBoolean(mCursor.getInt((mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONCTF1)))));

            mTest.getQuestion(i)
                    .getChoiceD()
                    .setText(
                            new String(mCursor.getString(mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIOND))));
            mTest.getQuestion(i)
                    .getChoiceD()
                    .setIsRight(
                            IntToBooleans.toBoolean(mCursor.getInt((mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONDTF)))));
            mTest.getQuestion(i)
                    .getChoiceD()
                    .setIsRight1(
                            IntToBooleans.toBoolean(mCursor.getInt((mCursor
                                    .getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONDTF1)))));
            i++;


        }
        dbManager.closeDataDB();
    }
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("NewApi")
    public void showCurrentQuestion(int position) {
        if (mTest.getQuestion(position).getChoiceA().isTrue()
                && mTest.getQuestion(position).getChoiceB().isTrue()
                && mTest.getQuestion(position).getChoiceC().isTrue()
                && mTest.getQuestion(position).getChoiceD().isTrue())
            layout.setVisibility(View.VISIBLE);
        else {
            layout.setVisibility(View.INVISIBLE);
        }

        if (mTest.getQuestion(position).getTitle().isText()) {
            questionTextView.setText(mTest.getQuestion(position).getTitle().getText());
            questionTextView.setBackgroundColor(Color.WHITE);
        } else {
            questionTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getTitle().getBitmap()));
            questionTextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceA().isText()) {
            answerATextView.setText(mTest.getQuestion(position).getChoiceA().getText());
            answerATextView.setBackgroundColor(Color.WHITE);
        } else {
            answerATextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceA().getBitmap()));
            answerATextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceB().isText()) {
            answerBTextView.setText(mTest.getQuestion(position).getChoiceB().getText());
            answerBTextView.setBackgroundColor(Color.WHITE);
        } else {
            answerBTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceB().getBitmap()));
            answerBTextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceC().isText()) {
            answerCTextView.setText(mTest.getQuestion(position).getChoiceC().getText());
            answerCTextView.setBackgroundColor(Color.WHITE);
        } else {
            answerCTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceC().getBitmap()));
            answerCTextView.setText("");
        }
        if (mTest.getQuestion(position).getTitle().isText()) {
            answerDTextView.setText(mTest.getQuestion(position).getChoiceD().getText());
            answerDTextView.setBackgroundColor(Color.WHITE);
        } else {
            answerDTextView.setBackground(new BitmapDrawable(mTest.getQuestion(position)
                    .getChoiceD().getBitmap()));
            answerDTextView.setText("");
        }
        if (mTest.getQuestion(position).getChoiceA().isRight1()) {
            checkBoxA.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
        } else {
            checkBoxA.setBackgroundResource(R.drawable.ic_check_box_outline_blank_black_18dp);
        }
        if (mTest.getQuestion(position).getChoiceB().isRight1()) {
            checkBoxB.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
        } else {
            checkBoxB.setBackgroundResource(R.drawable.ic_check_box_outline_blank_black_18dp);
        }
        if (mTest.getQuestion(position).getChoiceC().isRight1()) {
            checkBoxC.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
        } else {
            checkBoxC.setBackgroundResource(R.drawable.ic_check_box_outline_blank_black_18dp);
        }
        if (mTest.getQuestion(position).getChoiceD().isRight1()) {
            checkBoxD.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
        } else {
            checkBoxD.setBackgroundResource(R.drawable.ic_check_box_outline_blank_black_18dp);
        }
        if (mTest.getQuestion(position).getChoiceA().isRight()) {
            answerATv.setTextColor(Color.RED);
        } else {
            answerATv.setTextColor(Color.BLACK);
        }
        if (mTest.getQuestion(position).getChoiceB().isRight()) {
            answerBTv.setTextColor(Color.RED);
        } else {
            answerBTv.setTextColor(Color.BLACK);
        }
        if (mTest.getQuestion(position).getChoiceC().isRight()) {
            answerCTv.setTextColor(Color.RED);
        } else {
            answerCTv.setTextColor(Color.BLACK);
        }
        if (mTest.getQuestion(position).getChoiceD().isRight()) {
            answerDTv.setTextColor(Color.RED);
        } else {
            answerDTv.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_tab_question1:
                clickPreButton();
                break;
            case R.id.button_tab_question2:
                clickNextButton();
                break;
            default:
                break;
        }
    }
}
