package com.kcb.teacher.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.teacher.model.checkin.CheckInResult;
import com.kcbTeam.R;

/**
 * 
 * @className: ListAdapterSignRecod
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:23:50
 */
@SuppressLint("ViewHolder")
public class ListAdapterSignRecod extends BaseAdapter {

    private Context mContext;
    private List<CheckInResult> mList;
    private final String FORMAT_RATE = "%1$d%%";

    public ListAdapterSignRecod(Context context, List<CheckInResult> list) {
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
        View view = View.inflate(mContext, R.layout.tch_listitem_checkinrecord, null);
        TextView signDate = (TextView) view.findViewById(R.id.textview_signdate);
        TextView signRate = (TextView) view.findViewById(R.id.textview_signrate);
        signDate.setText(mList.get(position).getDate().toString());
        signRate.setText(String.format(FORMAT_RATE, (int) (100 * mList.get(position).getRate())));
        return view;
    }
}
