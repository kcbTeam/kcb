package com.kcb.teacher.adapter.checkin;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.teacher.model.checkin.UncheckedStudent;
import com.kcbTeam.R;

/**
 * 
 * @className: ListAdapterMissCheckIn
 * @description:
 * @author: ZQJ
 * @date: 2015年4月28日 下午3:21:22
 */
public class LookCheckInDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<UncheckedStudent> mList;

    public LookCheckInDetailAdapter(Context context, List<UncheckedStudent> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public UncheckedStudent getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void release() {
        mContext = null;
        mList = null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_look_checkin_detail, null);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.textview_name);
            viewHolder.idTextView = (TextView) convertView.findViewById(R.id.textview_id);
            viewHolder.unCheckedRateTextView =
                    (TextView) convertView.findViewById(R.id.textview_unchecked_rate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setUnCheckedStudent(getItem(position));
        return convertView;
    }

    private class ViewHolder {
        public TextView nameTextView;
        public TextView idTextView;
        public TextView unCheckedRateTextView;

        public void setUnCheckedStudent(UncheckedStudent student) {
            nameTextView.setText(student.getName());
            idTextView.setText(student.getId());
            unCheckedRateTextView.setText(String.valueOf(student.getUnCheckedRate()));
        }
    }
}
