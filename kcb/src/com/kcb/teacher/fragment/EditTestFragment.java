package com.kcb.teacher.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.util.ToastUtil;
import com.kcb.teacher.util.DialogBackListener;
import com.kcb.teacher.util.EditTestDialog;
import com.kcbTeam.R;

public class EditTestFragment extends BaseFragment {

    private EditText questionEditText;
    private TextView questionNnm;

    private DialogBackListener mSureClickListener;
    private OnClickListener mCancelClickListener;
    
    private final int IndexOfQuestion = 1;
    private final int IndexOfA = 2;
    private final int IndexOfB = 3;
    private final int IndexOfC = 4;
    private final int IndexOfD = 5;

    private int mPositionIndex = IndexOfQuestion;

    public EditTestFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        View view = inflater.inflate(R.layout.tch_fragment_edittest, container, false);
        questionEditText = (EditText) view.findViewById(R.id.edittext_question);
        questionEditText.setOnClickListener(this);
        questionNnm = (TextView) view.findViewById(R.id.textview_question_num);
        questionNnm.setText(String.format(getResources().getString(R.string.format_question_num), 1));
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
                        //TODO :add op
                        break;
                    case IndexOfB:
                        //TODO :add op
                        break;
                    case IndexOfC:
                        //TODO :add op
                        break;
                    case IndexOfD:
                        //TODO :add op
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
        if (v == questionEditText) {
            mPositionIndex = 1;
            EditTestDialog dialog = new EditTestDialog(getActivity(), questionEditText.getText().toString());
            dialog.show();
            dialog.setTitle("输入题目");
            dialog.setSureButton("保存", mSureClickListener);
            dialog.setCancelButton("取消", mCancelClickListener);
        }
    }

}
