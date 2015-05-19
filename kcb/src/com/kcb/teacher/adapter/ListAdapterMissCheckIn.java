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
 * @className: ListAdapterMissCheckIn
 * @description:
 * @author: ZQJ
 * @date: 2015年4月28日 下午3:21:22
 */
public class ListAdapterMissCheckIn extends BaseAdapter {

    private List<StudentInfo> mList;
    private Context mContext;
    private final String FORMAT_CHECKIN_DETAIL = "%1$d/%2$d";

    public ListAdapterMissCheckIn(Context context, List<StudentInfo> list) {
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.tch_listitem_misscheckin, null);
        TextView studentName = (TextView) view.findViewById(R.id.textview_studentname);
        studentName.setText(mList.get(position).getStudentID());

        TextView studentId = (TextView) view.findViewById(R.id.textview_studentid);
        studentId.setText(mList.get(position).getStudentName());

        TextView checkinDetail = (TextView) view.findViewById(R.id.textview_checkin_detail);
        checkinDetail.setText(String.format(FORMAT_CHECKIN_DETAIL, mList.get(position)
                .getTotalCheckInTimes() - mList.get(position).getCheckInTimes(), mList
                .get(position).getTotalCheckInTimes()));
        return view;
    }
}
