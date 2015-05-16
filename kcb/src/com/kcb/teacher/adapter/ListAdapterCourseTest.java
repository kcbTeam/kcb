package com.kcb.teacher.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.activity.CheckTestActivity;
import com.kcb.teacher.activity.CheckTestDetailsActivity;
import com.kcb.teacher.model.CourseTest;
import com.kcbTeam.R;

public class ListAdapterCourseTest extends BaseAdapter {
    @SuppressWarnings("unused")
    private static final String TAG = "ListAdapterCourseTest";

    private Context mContext;
    private List<CourseTest> mList;


    public ListAdapterCourseTest(Context context, List<CourseTest> list) {
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
            holder.detailsButton = (ButtonFlat) convertView.findViewById(R.id.button_details);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.testName.setText(mList.get(conversePosition).getTestName());
        holder.testDate.setText(mList.get(conversePosition).getTestDate());
        holder.questionTotalNum.setText(String
                .valueOf(mList.get(conversePosition).getQuestionList().size()));
        holder.detailsButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CheckTestDetailsActivity.class);
                intent.putExtra(CheckTestActivity.CLICKED_TEST_KEY, mList.get(getCount() - 1 - position));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView testName;
        TextView testDate;
        TextView questionTotalNum;
        ButtonFlat detailsButton;
    }
}
