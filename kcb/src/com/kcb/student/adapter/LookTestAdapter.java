package com.kcb.student.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.common.model.test.Test;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.student.activity.test.LookTestDetailActivity;
import com.kcbTeam.R;

/**
 * @className: TestResultAdapter
 * @description:
 * @author: Ding
 * @date: 2015年5月15日 下午7:47:36
 */
public class LookTestAdapter extends BaseAdapter {

    private Context mContext;
    private List<Test> mTests;

    public LookTestAdapter(Context context, List<Test> tests) {
        this.mContext = context;
        this.mTests = tests;
    }

    @Override
    public int getCount() {
        return mTests.size();
    }

    @Override
    public Test getItem(int position) {
        return mTests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void release() {
        mContext = null;
        mTests = null;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView =
                    LayoutInflater.from(mContext).inflate(R.layout.stu_listitem_look_test, null);
            holder.rootButton = (ButtonFlat) convertView.findViewById(R.id.button_root);
            holder.rootButton.setRippleColor(mContext.getResources().getColor(R.color.black_300));
            holder.testNameTextView = (TextView) convertView.findViewById(R.id.textview_testname);
            holder.questionNumTextView =
                    (TextView) convertView.findViewById(R.id.textview_questionnum);
            holder.testDateTextView = (TextView) convertView.findViewById(R.id.textview_testdate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setTest(getItem(position));
        holder.rootButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Test test = getItem(position);
                if (test.hasEnded()) {
                    notifyDataSetChanged();
                    LookTestDetailActivity.start(mContext, test);
                } else {
                    ToastUtil.toast(R.string.stu_look_result_testend);
                }
            }
        });
        return convertView;
    }

    public final class ViewHolder {
        public ButtonFlat rootButton;
        public TextView testNameTextView;
        public TextView questionNumTextView;
        public TextView testDateTextView;

        public void setTest(Test test) {
            testNameTextView.setText(test.getName());
            if (!test.hasEnded()) { // 如果测试未结束，显示一个时钟图标表示
                Drawable drawable =
                        mContext.getResources().getDrawable(R.drawable.ic_access_time_grey600_24dp);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                testNameTextView.setCompoundDrawables(drawable, null, null, null);
            } else {
                testNameTextView.setCompoundDrawables(null, null, null, null);
            }
            questionNumTextView.setText(String.valueOf(test.getQuestionNum()));
            testDateTextView.setText(test.getDateString());
        }
    }
}
