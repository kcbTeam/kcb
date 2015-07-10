package com.kcb.teacher.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.activity.stucentre.StuCentreActivity;
import com.kcb.teacher.database.students.Student;
import com.kcbTeam.R;

/**
 * 
 * @className: ListAdapterStudent
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:24:01
 */
public class StuCentreAdapter extends BaseAdapter {

    private List<Student> mStudents;
    private Context mContext;

    public StuCentreAdapter(Context context, List<Student> students) {
        mContext = context;
        mStudents = students;
    }

    @Override
    public int getCount() {
        return mStudents.size();
    }

    @Override
    public Student getItem(int position) {
        return mStudents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_stucentre, null);
            holder = new ViewHolder();
            holder.rootButton = (ButtonFlat) convertView.findViewById(R.id.button_root);
            holder.rootButton.setRippleColor(mContext.getResources().getColor(R.color.black_300));
            holder.studentName = (TextView) convertView.findViewById(R.id.textview_studentname);
            holder.studentId = (TextView) convertView.findViewById(R.id.textview_studentid);
            holder.checkInRate = (TextView) convertView.findViewById(R.id.textview_checkinRate);
            holder.correctRate = (TextView) convertView.findViewById(R.id.textview_correctRate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setStudent(getItem(position));
        holder.rootButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StuCentreActivity.start(mContext, getItem(position));
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public ButtonFlat rootButton;
        public TextView studentName;
        public TextView studentId;
        public TextView checkInRate;
        public TextView correctRate;

        public void setStudent(Student student) {
            studentName.setText(student.getName());
            studentId.setText(student.getId());
            checkInRate.setText(String.valueOf(student.getCheckInRate()));
            correctRate.setText(String.valueOf(student.getCorrectRate()));
        }
    }

    public void release() {
        mStudents = null;
    }
}
