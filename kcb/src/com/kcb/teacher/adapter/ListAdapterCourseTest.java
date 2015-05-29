package com.kcb.teacher.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

public class ListAdapterCourseTest extends BaseAdapter {
    @SuppressWarnings("unused")
    private static final String TAG = "ListAdapterCourseTest";

    private Context mContext;
    private List<Test> mList;


    public ListAdapterCourseTest(Context context, List<Test> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int conversePosition = getCount() - 1 - position;
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_coursetest, null);
            holder = new ViewHolder();
            holder.testName = (TextView) convertView.findViewById(R.id.textview_testname);
            holder.testDate = (TextView) convertView.findViewById(R.id.textview_testdate);
            holder.questionTotalNum =
                    (TextView) convertView.findViewById(R.id.textview_questiontotalnum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        holder.testName.setText(mList.get(conversePosition).getName());
        holder.testDate.setText(formatter.format(mList.get(conversePosition).getDate()).toString());
        holder.questionTotalNum.setText(String
                .valueOf(mList.get(conversePosition).getQuestionNum()));
        return convertView;
    }

    static class ViewHolder {
        TextView testName;
        TextView testDate;
        TextView questionTotalNum;
    }
}
