package com.kcb.teacher.adapter;

import java.util.List;

import com.kcb.common.util.SignRecordInfo;
import com.kcbTeam.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapterSignRecod extends BaseAdapter {

    private Context mContext;
    private List<SignRecordInfo> mList;

    public ListAdapterSignRecod(Context context, List<SignRecordInfo> list) {
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
        View view = View.inflate(mContext, R.layout.teacher_listitem_signrecord, null);
        TextView signDate = (TextView) view.findViewById(R.id.textview_signdate);
        TextView signRate = (TextView) view.findViewById(R.id.textview_signrate);
        signDate.setText(mList.get(position).getSignDate());
        signRate.setText(mList.get(position).getSignRate());
        return view;
    }
}
