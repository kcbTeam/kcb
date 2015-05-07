package com.kcb.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.student.adapter.CheckInRecycleAdapter;
import com.kcb.student.adapter.CheckInRecycleAdapter.RecyclerItemClickListener;
import com.kcbTeam.R;

/**
 * 
 * @className: CheckinActivity
 * @description: Check in,six Textview,one reclyclerView,one Button
 * @author: Tao Li
 * @date: 2015-4-24 下午9:16:22
 */
public class CheckInActivity extends BaseActivity {

	private TextView num1TextView;
	private TextView num2TextView;
	private TextView num3TextView;
	private TextView num4TextView;
	private RecyclerView recyclerView;
	private PaperButton finishButton;

	private CheckInRecycleAdapter mAdapter;
	private int currentInputIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_activity_checkin);

		initView();
		initData();
	}

	@Override
	protected void initView() {
		num1TextView = (TextView) findViewById(R.id.textview_shownum1);
		num2TextView = (TextView) findViewById(R.id.textview_shownum2);
		num3TextView = (TextView) findViewById(R.id.textview_shownum3);
		num4TextView = (TextView) findViewById(R.id.textview_shownum4);
		finishButton = (PaperButton) findViewById(R.id.button_finish);
		finishButton.setOnClickListener(this);

		recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
		mAdapter = new CheckInRecycleAdapter();
		mAdapter.setRecyclerItemClickListener(mRecyclerItemClickListener);
		recyclerView.setAdapter(mAdapter);
	}

	@Override
	protected void initData() {
	}

	@Override
	public void onClick(View v) {
		String passwordString = new String(num1TextView.getText().toString()
				+ num2TextView.getText().toString()
				+ num3TextView.getText().toString()
				+ num4TextView.getText().toString());
		ToastUtil.toast(passwordString);
		Intent intent = new Intent(CheckInActivity.this, HomeActivity.class);
		startActivity(intent);
	}

	private RecyclerItemClickListener mRecyclerItemClickListener = new RecyclerItemClickListener() {

		@Override
		public void onItemClick(View view, int postion) {
			if (postion == 9) { // clean all num
				num1TextView.setText("");
				num2TextView.setText("");
				num3TextView.setText("");
				num4TextView.setText("");
				currentInputIndex = 0;
			} else if (postion == 11) { // clean last num
				switch (currentInputIndex) {
				case 1:
					num1TextView.setText("");
					break;
				case 2:
					num2TextView.setText("");
					break;
				case 3:
					num3TextView.setText("");
					break;
				case 4:
					num4TextView.setText("");
					break;
				default:
					break;
				}
				if (currentInputIndex > 0)
					currentInputIndex--;
				else
					currentInputIndex = 0;
			} else { // input num
				if (postion == 10) {
					postion = -1;
				}
				if (currentInputIndex != 4) {
					currentInputIndex++;
					switch (currentInputIndex) {
					case 1:
						num1TextView.setText(String.valueOf(postion + 1));
						break;
					case 2:
						num2TextView.setText(String.valueOf(postion + 1));
						break;
					case 3:
						num3TextView.setText(String.valueOf(postion + 1));
						break;
					case 4:
						num4TextView.setText(String.valueOf(postion + 1));
						break;
					default:
						break;
					}
				}
			}
		}
	};
}
