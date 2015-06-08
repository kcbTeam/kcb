package com.kcb.student.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.common.model.test.Test;
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView =
                    LayoutInflater.from(mContext).inflate(R.layout.stu_listitem_look_test, null);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.textview_testname);
            holder.numTextView = (TextView) convertView.findViewById(R.id.textview_questionnum);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.textview_testdate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setTest(getItem(position));
        return convertView;
    }

    public final class ViewHolder {
        public TextView nameTextView;
        public TextView numTextView;
        public TextView dateTextView;

        public void setTest(Test test) {
            nameTextView.setText(test.getName());
            numTextView.setText(String.valueOf(test.getQuestionNum()));
            dateTextView.setText(test.getDateString());
        }
    }
}
