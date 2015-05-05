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
        TextView studentName = (TextView) view.findViewById(R.id.textview_stu_name);
        studentName.setText(mList.get(position).getStudentName());
        
        TextView studentId = (TextView) view.findViewById(R.id.textview_stu_id);
        studentId.setText(mList.get(position).getStudentID());
        
        TextView studentMissTime = (TextView) view.findViewById(R.id.textview_stu_miss_time);
        studentMissTime.setText(String.valueOf(mList.get(position).getMissTimes()) + '/'
                + String.valueOf(mList.get(position).getCheckInTimes()));
        switch (position % 2) {
            case 0:
                view.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                break;
            default:
                view.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                break;
        }
        return view;
    }
}
