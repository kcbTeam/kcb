package com.kcb.teacher.adapter.checkin;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.activity.checkin.LookCheckInDetailActivity;
import com.kcb.teacher.database.checkin.CheckInResult;
import com.kcbTeam.R;

/**
 * 
 * @className: ListAdapterSignRecod
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:23:50
 */
public class LookCheckInAdapter extends BaseAdapter {

    private Context mContext;
    private List<CheckInResult> mResults;

    public LookCheckInAdapter(Context context, List<CheckInResult> list) {
        mContext = context;
        mResults = list;
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

    public void release() {
        mContext = null;
        mResults = null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_look_checkin, null);
            viewHolder = new ViewHolder();
            viewHolder.rootButton = (ButtonFlat) convertView.findViewById(R.id.button_root);
            viewHolder.rootButton.setRippleColor(mContext.getResources()
                    .getColor(R.color.black_400));
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.textview_date);
            viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.textview_time);
            viewHolder.rateTextView = (TextView) convertView.findViewById(R.id.textview_rate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setCheckInResult(getItem(position));
        viewHolder.rootButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LookCheckInDetailActivity.start(mContext, getItem(position));
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public ButtonFlat rootButton;
        public TextView dateTextView;
        public TextView timeTextView;
        public TextView rateTextView;

        public void setCheckInResult(CheckInResult result) {
            dateTextView.setText(result.getDateString());
            timeTextView.setText(result.getTimeString());
            rateTextView.setText(String.valueOf(result.getRate()));
        }
    }
}
