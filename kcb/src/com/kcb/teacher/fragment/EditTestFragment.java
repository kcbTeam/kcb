package com.kcb.teacher.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.common.base.BaseFragment;
import com.kcb.common.util.ToastUtil;
import com.kcb.teacher.util.DialogBackListener;
import com.kcb.teacher.util.EditTestDialog;
import com.kcbTeam.R;

public class EditTestFragment extends BaseFragment {

    private TextView questionNnm;

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
    private OnClickListener mCancelClickListener;

    private final int IndexOfQuestion = 1;
    private final int IndexOfA = 2;
    private final int IndexOfB = 3;
    private final int IndexOfC = 4;
    private final int IndexOfD = 5;

    private int mPositionIndex = IndexOfQuestion;
    
    private int backgroundColor = Color.parseColor("#4CAF50");

    public EditTestFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        View view = inflater.inflate(R.layout.tch_fragment_edittest, container, false);
        questionNnm = (TextView) view.findViewById(R.id.textview_question_num);
        questionNnm.setText(String
                .format(getResources().getString(R.string.format_question_num), 1));

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
        
        checkBoxA.setChecked(true);
        
//        checkBoxA.setBackgroundColor(backgroundColor);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initView() {
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

        mCancelClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtil.toast("Edit Canceled");
            }
        };
    }

    @Override
    protected void initData() {}


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
        makeEditDialog(text,title);
    }

    private void makeEditDialog(String text,String title) {
        EditTestDialog dialog =
                new EditTestDialog(getActivity(), text);
        dialog.show();
        dialog.setTitle(title);
        dialog.setSureButton("保存", mSureClickListener);
        dialog.setCancelButton("取消", mCancelClickListener);
    }

}
