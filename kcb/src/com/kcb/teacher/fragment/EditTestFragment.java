package com.kcb.teacher.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.util.AnimationUtil;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.teacher.model.QuestionObj;
import com.kcb.teacher.util.EditTestDialog;
import com.kcb.teacher.util.EditTestDialog.DialogBackListener;
import com.kcbTeam.R;


/**
 * 
 * @className: EditTestFragment
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 上午10:14:21
 */
public class EditTestFragment extends BaseFragment {

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

    private int checkedId = 0;

    private int mPositionIndex = IndexOfQuestion;
    private int questionNum;

    public EditTestFragment(int questionNum) {
        this.questionNum = questionNum;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        return inflater.inflate(R.layout.tch_fragment_edittest, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        View view = getView();
        numHintTextView = (TextView) view.findViewById(R.id.textview_question_num);
        numHintTextView.setText(String.format(getResources()
                .getString(R.string.format_question_num), questionNum));

        questionEditText = (EditText) view.findViewById(R.id.edittext_question);
        optionAEditText = (EditText) view.findViewById(R.id.edittext_A);
        optionBEditText = (EditText) view.findViewById(R.id.edittext_B);
        optionCEditText = (EditText) view.findViewById(R.id.edittext_C);
        optionDEditText = (EditText) view.findViewById(R.id.edittext_D);

        questionEditText.setOnClickListener(this);
        optionAEditText.setOnClickListener(this);
        optionBEditText.setOnClickListener(this);
        optionCEditText.setOnClickListener(this);
        optionDEditText.setOnClickListener(this);

        checkBoxA = (CheckBox) view.findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) view.findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) view.findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) view.findViewById(R.id.checkBox_D);
    }

    @Override
    protected void initData() {
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
    public void onResume() {
        super.onResume();
        numHintTextView.setText(String.format(getResources()
                .getString(R.string.format_question_num), questionNum));
    }

    public void questionNumPlus() {
        questionNum++;
    }

    public void questionNumReduce() {
        questionNum--;
    }

    @Override
    public void onClick(View v) {
        String text;
        String title;
        if (v == questionEditText) {
            mPositionIndex = IndexOfQuestion;
            text = questionEditText.getText().toString();
            title = "输入题目";
        } else if (v == optionAEditText) {
            mPositionIndex = IndexOfA;
            text = optionAEditText.getText().toString();
            title = "输入A选项";
        } else if (v == optionBEditText) {
            mPositionIndex = IndexOfB;
            text = optionBEditText.getText().toString();
            title = "输入B选项";
        } else if (v == optionCEditText) {
            mPositionIndex = IndexOfC;
            text = optionCEditText.getText().toString();
            title = "输入C选项";
        } else {
            mPositionIndex = IndexOfD;
            text = optionDEditText.getText().toString();
            title = "输入D选项";
        }
        makeEditDialog(text, title);
    }

    private void makeEditDialog(String text, String title) {
        EditTestDialog dialog = new EditTestDialog(getActivity(), text);
        dialog.show();
        dialog.setTitle(title);
        dialog.setSureButton("保存", mSureClickListener);
        dialog.setCancelButton("取消", null);
    }

    public QuestionObj getQuestionObj() {
        String question = questionEditText.getText().toString();
        String optionA = optionAEditText.getText().toString();
        String optionB = optionBEditText.getText().toString();
        String optionC = optionCEditText.getText().toString();
        String optionD = optionDEditText.getText().toString();
        int correctOption = getCorrectOption();
        if (question.equals("")) {
            AnimationUtil.shake(questionEditText);
        } else if (optionA.equals("")) {
            AnimationUtil.shake(optionAEditText);
        } else if (optionB.equals("")) {
            AnimationUtil.shake(optionBEditText);
        } else if (optionC.equals("")) {
            AnimationUtil.shake(optionCEditText);
        } else if (optionD.equals("")) {
            AnimationUtil.shake(optionDEditText);
        } else if (correctOption == 0) {
            AnimationUtil.shake(checkBoxA);
            AnimationUtil.shake(checkBoxB);
            AnimationUtil.shake(checkBoxC);
            AnimationUtil.shake(checkBoxD);
        } else {
//            return new QuestionObj(question, optionA, optionB, optionC, optionD, correctOption);
        }
        return null;
    }

    public void setCheckId(int checkedId) {
        this.checkedId = checkedId;
    }

    private int getCorrectOption() {
        if (checkBoxA.isCheck()) {
            return IndexOfA;
        }
        if (checkBoxB.isCheck()) {
            return IndexOfB;
        }
        if (checkBoxC.isCheck()) {
            return IndexOfC;
        }
        if (checkBoxD.isCheck()) {
            return IndexOfD;
        }
        return 0;
    }

}
