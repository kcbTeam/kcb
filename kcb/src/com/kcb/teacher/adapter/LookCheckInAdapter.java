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
public class LookCheckInAdapter extends BaseAdapter {

    private Context mContext;
    private List<CheckInResult> mResults;
    private final String FORMAT_RATE = "%1$d%%";

    public LookCheckInAdapter(Context context, List<CheckInResult> list) {
        mContext = context;
        mResults = list;
    }

    public void setData(List<CheckInResult> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public CheckInResult getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_look_checkin, null);
            viewHolder = new ViewHolder();
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.textview_date);
            viewHolder.rateTextView = (TextView) convertView.findViewById(R.id.textview_rate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setCheckInResult(getItem(position));
        return convertView;
    }

    private class ViewHolder {
        public TextView dateTextView;
        public TextView rateTextView;

        public void setCheckInResult(CheckInResult result) {
            dateTextView.setText(result.getDateString());
            rateTextView.setText(String.format(FORMAT_RATE, (int) (100 * result.getRate())));
        }
    }
}
