package com.kcb.teacher.adapter.test;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.common.model.test.Test;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.activity.test.LookTestDetailActivity;
import com.kcbTeam.R;

/**
 * 
 * @className: LookTestAdapter
 * @description:
 * @author: ZQJ
 * @date: 2015-6-5 上午9:15:42
 */
public class LookTestAdapter extends BaseAdapter {

    private Context mContext;
    private List<Test> mTests;

    public LookTestAdapter(Context context, List<Test> list) {
        mContext = context;
        mTests = list;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_look_test, null);
            holder = new ViewHolder();
            holder.rootButton = (ButtonFlat) convertView.findViewById(R.id.button_root);
            holder.rootButton.setRippleColor(mContext.getResources().getColor(R.color.black_300));
            holder.testNameTextView = (TextView) convertView.findViewById(R.id.textview_testname);
            holder.testDateTextView = (TextView) convertView.findViewById(R.id.textview_testdate);
            holder.questionNumTextView =
                    (TextView) convertView.findViewById(R.id.textview_questionnum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setTest(getItem(position));
        holder.rootButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LookTestDetailActivity.start(mContext, getItem(position));
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public ButtonFlat rootButton;
        public TextView testNameTextView;
        public TextView questionNumTextView;
        public TextView testDateTextView;

        @SuppressLint("SimpleDateFormat")
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
            testDateTextView.setText(test.getDateString());
            questionNumTextView.setText(String.valueOf(test.getQuestionNum()));
        }
    }
}
