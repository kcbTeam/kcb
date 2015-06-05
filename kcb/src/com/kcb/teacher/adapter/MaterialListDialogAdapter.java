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
// TODO use RecyclerView for better performance, learn how RecyclerView works;
// you can ask TaoLi;
public class MaterialListDialogAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mTestNames;
    private int selectedIndex = 0;

    public MaterialListDialogAdapter(Context context, List<String> list) {
        mContext = context;
        mTestNames = list;
    }

    @Override
    public int getCount() {
        return mTestNames.size();
    }

    @Override
    public Object getItem(int position) {
        return mTestNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView testname;
        RadioButton testchosen;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int cov_position = getCount() - position - 1;
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.tch_listitem_dialog, null);
            holder.testname = (TextView) convertView.findViewById(R.id.textview_testname);
            holder.testchosen = (RadioButton) convertView.findViewById(R.id.checkBox_testchosen);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.testchosen.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(boolean isChecked) {
                if (isChecked) {
                    selectedIndex = position;
                }
                notifyDataSetChanged();
            }
        });
        holder.testname.setText(mTestNames.get(cov_position));
        if (selectedIndex == position) {
            holder.testchosen.setChecked(true);
        } else {
            holder.testchosen.setChecked(false);
        }
        return convertView;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
