package com.kcb.student.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.student.activity.LookTestResultActivity;
import com.kcb.student.fragment.TestFragment;
import com.kcbTeam.R;

/**
 * @className: TestResultAdapter
 * @description:
 * @author: Ding
 * @date: 2015年5月15日 下午7:47:36
 */
public class TestResultAdapter extends BaseAdapter {

    ArrayList<String> Data = null;
    Context context;

    public TestResultAdapter(Context context, List<String> list) {
        this.context = context;
        this.Data = (ArrayList<String>) list;
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
            holder.nextButtonFlat = (ButtonFlat) convertView.findViewById(R.id.button_testdetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.testname.setText(Data.get(position).toString());
        holder.nextButtonFlat.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LookTestResultActivity.class);
                intent.putExtra("testTitle1", Data.get(position));     
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public final class ViewHolder {

        public TextView testname;
        public ButtonFlat nextButtonFlat;
    }
}
