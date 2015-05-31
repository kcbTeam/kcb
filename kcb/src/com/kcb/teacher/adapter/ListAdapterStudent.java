package com.kcb.teacher.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.teacher.model.stucentre.Student;
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

    private List<Student> mList;
    private Context mContext;

    private final String FORMAT_RATE = "%1$d%%";

    public ListAdapterStudent(Context context, List<Student> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Student getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_stucentre, null);
            holder = new ViewHolder();
            holder.studentName = (TextView) convertView.findViewById(R.id.textview_studentname);
            holder.studentId = (TextView) convertView.findViewById(R.id.textview_studentid);
            holder.checkInRate = (TextView) convertView.findViewById(R.id.textview_checkinRate);
            holder.correctRate = (TextView) convertView.findViewById(R.id.textview_correctRate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.studentName.setText(mList.get(position).getName());
        holder.studentId.setText(mList.get(position).getId());
        holder.checkInRate.setText(String.format(FORMAT_RATE, (int) (100 * mList.get(position)
                .getCheckInRate())));
        holder.correctRate.setText(String.format(FORMAT_RATE, (int) (100 * mList.get(position)
                .getCorrectRate())));
        return convertView;
    }

    static class ViewHolder {
        TextView studentName;
        TextView studentId;
        TextView checkInRate;
        TextView correctRate;
    }

}
