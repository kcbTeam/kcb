package com.kcb.teacher.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.teacher.model.StudentInfo;
import com.kcbTeam.R;

/**
 * 
 * @className: ListAdapterStudent
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:24:01
 */
@SuppressLint("ViewHolder")
public class ListAdapterStudent extends BaseAdapter {

    private List<StudentInfo> mList;
    private Context mContext;

    private final String FORMAT_RATE = "%1$d%%";

    public ListAdapterStudent(Context context, List<StudentInfo> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.tch_listitem_stucentre, null);

        TextView studentName = (TextView) view.findViewById(R.id.textview_studentname);
        studentName.setText(mList.get(position).getStudentName());

        TextView studentId = (TextView) view.findViewById(R.id.textview_studentid);
        studentId.setText(mList.get(position).getStudentID());

        TextView checkInRate = (TextView) view.findViewById(R.id.textview_checkinRate);
        checkInRate.setText(String.format(FORMAT_RATE, (int) (100 * mList.get(position)
                .getCheckInRate())));

        TextView correctRate = (TextView) view.findViewById(R.id.textview_correctRate);
        correctRate.setText(String.format(FORMAT_RATE, (int) (100 * mList.get(position)
                .getCorrectRate())));


        return view;
    }

}
