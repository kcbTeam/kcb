package com.kcb.teacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.teacher.model.QuestionObj;
import com.kcb.teacher.util.EditTestDialog;
import com.kcb.teacher.util.EditTestDialog.DialogBackListener;
import com.kcbTeam.R;

/**
 * 
 * @className: EditTestActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 下午6:03:59
 */
public class EditTestActivity extends BaseActivity {

    private PaperButton lastButton;
    private PaperButton nextButton;
    private ImageButton addButton;
    private ImageButton deleteButton;

    private int mCurrentPosition;
    private QuestionObj mNextObj;
    private List<QuestionObj> mQuestionList;

    private int MaxFragmentNum = 3; // set max question !!!!num from 0

    private TextView numHintTextView;

    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private CheckBox checkBoxD;

    private EditText questionEditText;
    private EditText optionAEditText;
    private EditText optionBEditText;
    private EditText optionCEditText;
    private EditText optionDEditText;

    private DialogBackListener mSureClickListener;

    private final int IndexOfQuestion = 1;
    private final int IndexOfA = 2;
    private final int IndexOfB = 3;
    private final int IndexOfC = 4;
    private final int IndexOfD = 5;

    private int mPositionIndex = IndexOfQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_edittest);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        lastButton = (PaperButton) findViewById(R.id.pagerbutton_last);
        lastButton.setOnClickListener(this);
        lastButton.setTextColor(getResources().getColor(R.color.gray));

        nextButton = (PaperButton) findViewById(R.id.pagerbutton_next);
        nextButton.setOnClickListener(this);
        nextButton.setTextColor(getResources().getColor(R.color.black));

        addButton = (ImageButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        deleteButton = (ImageButton) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);

        numHintTextView = (TextView) findViewById(R.id.textview_question_num);
        numHintTextView.setText(String.format(getResources()
                .getString(R.string.format_question_num), mCurrentPosition + 1));

        questionEditText = (EditText) findViewById(R.id.edittext_question);
        questionEditText.setOnClickListener(this);
        optionAEditText = (EditText) findViewById(R.id.edittext_A);
        optionAEditText.setOnClickListener(this);
        optionBEditText = (EditText) findViewById(R.id.edittext_B);
        optionBEditText.setOnClickListener(this);
        optionCEditText = (EditText) findViewById(R.id.edittext_C);
        optionCEditText.setOnClickListener(this);
        optionDEditText = (EditText) findViewById(R.id.edittext_D);
        optionDEditText.setOnClickListener(this);

        checkBoxA = (CheckBox) findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) findViewById(R.id.checkBox_D);

        refreshInfo(null);
    }

    @Override
    protected void initData() {
        mQuestionList = new ArrayList<QuestionObj>();
        for (int i = 0; i < MaxFragmentNum; i++) {
            mQuestionList.add(getCurrentObj());
        }
        mCurrentPosition = 0;
        mSureClickListener = new DialogBackListener() {

            @Override
            public void refreshActivity(String text) {
                switch (mPositionIndex) {
                    case IndexOfQuestion:
                        questionEditText.setText(text);
                        break;
                    case IndexOfA:
                        optionAEditText.setText(text);
                        break;
                    case IndexOfB:
                        optionBEditText.setText(text);
                        break;
                    case IndexOfC:
                        optionCEditText.setText(text);
                        break;
                    case IndexOfD:
                        optionDEditText.setText(text);
                        break;
                    default:
                        break;
                }
            }
        };

    }

    @Override
    public void onClick(View v) {
        String text;
        String title;
        switch (v.getId()) {
            case R.id.delete_button:
                clickDelete();
                break;
            case R.id.pagerbutton_last:
                clickLast();
                break;
            case R.id.pagerbutton_next:
                clickNext();
                break;
            case R.id.add_button:
                clickAdd();
                break;
            case R.id.edittext_question:
                mPositionIndex = IndexOfQuestion;
                text = questionEditText.getText().toString();
                title = "输入题目";
                makeEditDialog(text, title);
                break;
            case R.id.edittext_A:
                mPositionIndex = IndexOfA;
                text = optionAEditText.getText().toString();
                title = "输入A选项";
                makeEditDialog(text, title);
                break;
            case R.id.edittext_B:
                mPositionIndex = IndexOfB;
                text = optionBEditText.getText().toString();
                title = "输入B选项";
                makeEditDialog(text, title);
                break;
            case R.id.edittext_C:
                mPositionIndex = IndexOfC;
                text = optionCEditText.getText().toString();
                title = "输入C选项";
                makeEditDialog(text, title);
                break;
            case R.id.edittext_D:
                mPositionIndex = IndexOfD;
                text = optionDEditText.getText().toString();
                title = "输入D选项";
                makeEditDialog(text, title);
                break;
            default:
                break;
        }
    }

    private void makeEditDialog(String text, String title) {
        EditTestDialog dialog = new EditTestDialog(this, text);
        dialog.show();
        dialog.setTitle(title);
        dialog.setSureButton("保存", mSureClickListener);
        dialog.setCancelButton("取消", null);
    }

    private void clickAdd() {
        MaxFragmentNum++;
        mQuestionList.add(new QuestionObj());
        if (!mQuestionList.get(mCurrentPosition).equal(getCurrentObj())) {
            mQuestionList.set(mCurrentPosition, getCurrentObj());
            ToastUtil.toast("第" + (1 + mCurrentPosition) + "题已保存");
        }
        mCurrentPosition++;
        refreshInfo(mQuestionList.get(mCurrentPosition));
    }

    private void clickNext() {
        if (mCurrentPosition + 1 >= MaxFragmentNum) {
            if (!getCurrentObj().equal(mQuestionList.get(mCurrentPosition))) {
                mQuestionList.set(mCurrentPosition, getCurrentObj());
            }
            for (int i = 0; i < mQuestionList.size(); i++) {
                if (!mQuestionList.get(i).isLegal()) {
                    ToastUtil.toast("第" + (i + 1) + "题有空选项哦");
                    return;
                }
            }
            completeEdit();
        } else {
            if (!getCurrentObj().equal(mQuestionList.get(mCurrentPosition))) {
                mQuestionList.set(mCurrentPosition, getCurrentObj());
                ToastUtil.toast("第" + (1 + mCurrentPosition) + "题已保存");
            }
            mCurrentPosition++;
            mNextObj = mQuestionList.get(mCurrentPosition);
            refreshInfo(mNextObj);
        }

    }

    private void clickLast() {
        if (mCurrentPosition != 0) {
            if (!mQuestionList.get(mCurrentPosition).equal(getCurrentObj())) {
                mQuestionList.set(mCurrentPosition, getCurrentObj());
                ToastUtil.toast("第" + (1 + mCurrentPosition) + "题已保存");
            }
            mCurrentPosition--;
            mNextObj = mQuestionList.get(mCurrentPosition);
            refreshInfo(mNextObj);
        }
    }

    private void clickDelete() {
        if (MaxFragmentNum > 1) {
            MaxFragmentNum--;
            mQuestionList.remove(mCurrentPosition);
            if (mCurrentPosition == 0) {
                refreshInfo(mQuestionList.get(mCurrentPosition));
            } else {
                mCurrentPosition--;
                refreshInfo(mQuestionList.get(mCurrentPosition));
            }
            ToastUtil.toast("删除成功");
        } else {
            OnClickListener sureListener = new OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            };
            // TODO
            // DialogUtil.showNormalDialog(this, "离开", "确认放弃此次编辑吗？", "确定", sureListener, "取消",
            // null);
        }
    }

    private QuestionObj getCurrentObj() {
        String question = questionEditText.getText().toString();
        String optionA = optionAEditText.getText().toString();
        String optionB = optionBEditText.getText().toString();
        String optionC = optionCEditText.getText().toString();
        String optionD = optionDEditText.getText().toString();
        boolean[] correctOption =
                {checkBoxA.isCheck(), checkBoxB.isCheck(), checkBoxC.isCheck(), checkBoxD.isCheck()};
        return new QuestionObj(question, optionA, optionB, optionC, optionD, correctOption);
    }

    private void refreshInfo(QuestionObj currentObj) {
        String question = "";
        String optionA = "";
        String optionB = "";
        String optionC = "";
        String optionD = "";
        boolean[] correctId = {false, false, false, false};
        if (null != currentObj) {
            question = currentObj.getQuestion();
            optionA = currentObj.getOptionA();
            optionB = currentObj.getOptionB();
            optionC = currentObj.getOptionC();
            optionD = currentObj.getOptionD();
            correctId = currentObj.getCorrectId();
        }
        numHintTextView.setText(String.format(getResources()
                .getString(R.string.format_question_num), mCurrentPosition + 1));
        questionEditText.setText(question);
        optionAEditText.setText(optionA);
        optionBEditText.setText(optionB);
        optionCEditText.setText(optionC);
        optionDEditText.setText(optionD);
        checkBoxA.setChecked(correctId[0]);
        checkBoxB.setChecked(correctId[1]);
        checkBoxC.setChecked(correctId[2]);
        checkBoxD.setChecked(correctId[3]);
        if (mCurrentPosition + 1 >= MaxFragmentNum) {
            nextButton.setText("完成");
            addButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setText("下一题");
            addButton.setVisibility(View.INVISIBLE);
        }
    }

    private void completeEdit() {
        ToastUtil.toast("need for speed!!!");
    }

}
