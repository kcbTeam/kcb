package com.kcb.teacher.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.library.view.radioButton.RadioButton;
import com.kcb.library.view.radioButton.RadioButton.OnCheckedChangeListener;
import com.kcbTeam.R;

/**
 * 
 * @className: ListAdapterEdit
 * @description:
 * @author: ljx
 * @date: 2015年5月7日 下午8:19:23
 */
public class MaterialListDialogAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mTestNames;
    private int mSelectedIndex = 0;
    private boolean mIsAddOrEditMode;

    public MaterialListDialogAdapter(Context context, List<String> list) {
        mContext = context;
        mTestNames = list;
    }

    @Override
    public int getCount() {
        return mTestNames.size();
    }

    @Override
    public String getItem(int position) {
        return mTestNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void enableAddOrEditMode() {
        mIsAddOrEditMode = true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.tch_listitem_material_list_dialog, null);
            holder.testname = (TextView) convertView.findViewById(R.id.textview_testname);
            holder.radioButton = (RadioButton) convertView.findViewById(R.id.checkBox_testchosen);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(boolean isChecked) {
                if (isChecked) {
                    mSelectedIndex = position;
                }
                notifyDataSetChanged();
            }
        });
        holder.setContent(position, getItem(position));
        return convertView;
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    private class ViewHolder {
        TextView testname;
        RadioButton radioButton;

        public void setContent(int position, String text) {
            testname.setText(text);
            if (mIsAddOrEditMode && position == 0) {
                testname.setTextColor(mContext.getResources().getColor(R.color.blue));
            } else {
                testname.setTextColor(mContext.getResources().getColor(R.color.black_700));
            }
            if (mSelectedIndex == position) {
                radioButton.setChecked(true);
            } else {
                radioButton.setChecked(false);
            }
        }
    }
}
