package com.kcb.student.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcb.common.base.BaseFragment;
import com.kcbTeam.R;

public class TestShortAnwFragment extends BaseFragment {

	private TextView textView1;
	private TextView textView2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.stu_fragment_testshortanw,
				container, false);
		textView1 = (TextView) view.findViewById(R.id.textview_shortanw);
		textView2 = (TextView) view
				.findViewById(R.id.textview_shortanw_question);
		textView1.setText("简答题");
		textView2.setText("请同学们认真回答");
		return view;
	}

	@Override
	protected void initView() {
	}

	@Override
	protected void initData() {
	}

}
