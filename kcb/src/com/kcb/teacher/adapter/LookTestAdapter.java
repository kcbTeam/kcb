package com.kcb.teacher.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.common.model.test.Test;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_looktest, null);
            holder = new ViewHolder();
            holder.testName = (TextView) convertView.findViewById(R.id.textview_testname);
            holder.testDate = (TextView) convertView.findViewById(R.id.textview_testdate);
            holder.questionNum = (TextView) convertView.findViewById(R.id.textview_questionnum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setTest(getItem(position));
        return convertView;
    }

    private class ViewHolder {
        TextView testName;
        TextView questionNum;
        TextView testDate;

        @SuppressLint("SimpleDateFormat")
        public void setTest(Test test) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            testName.setText(test.getName());
            testDate.setText(formatter.format(test.getDate()).toString());
            questionNum.setText(String.valueOf(test.getQuestionNum()));
        }
    }
}
