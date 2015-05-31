package com.kcb.student.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcbTeam.R;

/**
 * @className: TestResultAdapter
 * @description:
 * @author: Ding
 * @date: 2015年5月15日 下午7:47:36
 */
public class TestResultAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, Object>> Data = null;
    Context context;

    public TestResultAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        this.Data = (ArrayList<HashMap<String, Object>>) list;
    }

    @Override
    public int getCount() {
        return Data == null ? 0 : Data.size();
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.stu_view_list, null);
            holder.testname = (TextView) convertView.findViewById(R.id.testname);
            holder.questionnum = (TextView) convertView.findViewById(R.id.questonnumber);
            holder.testtime = (TextView) convertView.findViewById(R.id.testtime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.testname.setText(Data.get(position).get("testname").toString());
        holder.questionnum.setText(Data.get(position).get("questionnum").toString());
        holder.testtime.setText(Data.get(position).get("testtime").toString());
        return convertView;
    }

    public final class ViewHolder {

        public TextView testname;
        public TextView questionnum;
        public TextView testtime;
    }
}
