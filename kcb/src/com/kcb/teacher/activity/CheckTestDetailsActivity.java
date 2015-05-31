package com.kcb.teacher.activity;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.ListAdapterChoices;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.QuestionItem;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

public class CheckTestDetailsActivity extends BaseActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "CheckTest";

    private ButtonFlat backButton;
    private ButtonFlat lastButton;
    private ButtonFlat nextButton;

    private TextView testNameTextView;
    private TextView questionNumTextView;

    private EditText questionTitle;
    private TextView correctRate;

    private ListView choiceList;
    private ListAdapterChoices mAdapter;

    private Test mTest;
    private List<Question> mQuestionList;

    private int mQuestionNum;
    private int mCurrentPosition;

    private final String mRateFormat = "正确率：%1$d%%";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_checktestdetails);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        testNameTextView = (TextView) findViewById(R.id.textview_title);
        testNameTextView.setText(mTest.getName());

        questionNumTextView = (TextView) findViewById(R.id.textview_question_num);

        questionTitle = (EditText) findViewById(R.id.edittext_question_title);
        correctRate = (TextView) findViewById(R.id.textview_correctrate);

        choiceList = (ListView) findViewById(R.id.listview_choices);

        lastButton = (ButtonFlat) findViewById(R.id.button_last);
        lastButton.setOnClickListener(this);
        nextButton = (ButtonFlat) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);

        showContent();
    }


    @Override
    protected void initData() {

        mTest = (Test) getIntent().getSerializableExtra(CheckTestActivity.CLICKED_TEST_KEY);
        mQuestionList = mTest.getQuestions();
        mQuestionNum = mQuestionList.size();
        mCurrentPosition = 0;
        mQuestionList.get(mCurrentPosition).getTitle().setRate(0.8);
        mQuestionList.get(mCurrentPosition).getChoiceA().setRate(0.3);
        mQuestionList.get(mCurrentPosition).getChoiceB().setRate(0.4);
        mQuestionList.get(mCurrentPosition).getChoiceC().setRate(0.5);
        mQuestionList.get(mCurrentPosition).getChoiceD().setRate(0.2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_last:
                lastQuestion();
                break;
            case R.id.button_next:
                nextQuestin();
                break;
            default:
                break;
        }
        showContent();
    }


    private void lastQuestion() {
        if (mCurrentPosition > 0) {
            mCurrentPosition--;
        }
    }

    private void nextQuestin() {
        if (mCurrentPosition != mQuestionNum - 1) {
            mCurrentPosition++;
        }
    }

    private void showContent() {
        Question question = mQuestionList.get(mCurrentPosition);
        showQuestionNum();
        showQuestionItem(questionTitle, question.getTitle());
        correctRate
                .setText(String.format(mRateFormat, (int) (100 * question.getTitle().getRate())));
        mAdapter = new ListAdapterChoices(this, question);
        choiceList.setAdapter(mAdapter);
    }

    private void showQuestionNum() {
        // questionNumTextView.setText(String.format(
        // getResources().getString(R.string.format_question_num), mCurrentPosition + 1,
        // mQuestionNum));
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(EditText view, QuestionItem item) {
        view.setBackgroundResource(R.drawable.stu_checkin_textview);
        if (item.isText()) {
            view.setText(item.getText());
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                view.setText("");
                view.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
            view.setFocusable(false);
        }
    }
}
