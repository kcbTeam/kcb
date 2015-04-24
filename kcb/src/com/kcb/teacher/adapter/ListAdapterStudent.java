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
        View view = View.inflate(mContext, R.layout.teacher_listitem_stucentre, null);
        TextView studentName = (TextView) view.findViewById(R.id.textview_studentname);
        studentName.setText(mList.get(position).getStudentName());
        TextView studentId = (TextView) view.findViewById(R.id.textview_studentid);
        studentId.setText(mList.get(position).getStudentID());
        return view;
    }
}
