package com.kcb.student.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcb.common.base.BaseFragment;
import com.kcbTeam.R;

public class TestChoiceFragment extends BaseFragment {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private String questionContent;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    public static TestChoiceFragment newInstance(String[] Index) {
        TestChoiceFragment title = new TestChoiceFragment();
        Bundle args = new Bundle();
        args.putStringArray("Index", Index);
        title.setArguments(args);
        return title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            questionContent = args.getStringArray("Index")[0];
            answer1 = args.getStringArray("Index")[1];
            answer2 = args.getStringArray("Index")[2];
            answer3 = args.getStringArray("Index")[3];
            answer4 = args.getStringArray("Index")[4];
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stu_fragment_testchoice, container, false);
        textView1 = (TextView) view.findViewById(R.id.textview_choice_question);
        textView2 = (TextView) view.findViewById(R.id.textview_choice_one);
        view.findViewById(R.id.checkbox_one).setBackgroundColor(Color.parseColor("#1E88E5"));
        textView3 = (TextView) view.findViewById(R.id.textview_choice_two);
        textView4 = (TextView) view.findViewById(R.id.textview_choice_three);
        textView5 = (TextView) view.findViewById(R.id.textview_choice_four);
        textView1.setText(questionContent);
        textView2.setText(answer1);
        textView3.setText(answer2);
        textView4.setText(answer3);
        textView5.setText(answer4);
        return view;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {}

}
