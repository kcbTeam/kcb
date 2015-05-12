package com.kcb.teacher.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.teacher.model.ChoiceQuestion;
import com.kcbTeam.R;

/**
 * 
 * @className: ListAdapterQuestions
 * @description:
 * @author: ZQJ
 * @date: 2015年5月11日 下午10:34:21
 */
public class ListAdapterQuestions extends BaseAdapter {
	@SuppressWarnings("unused")
	private static final String TAG = "ListAdapterQuestions";

	private List<ChoiceQuestion> mList;
	private Context mContext;

	public ListAdapterQuestions(Context context, List<ChoiceQuestion> list) {
		mList = list;
		mContext = context;
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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext,
				R.layout.tch_listview_questions_item, null);
		TextView content = (TextView) view.findViewById(R.id.textview);
		content.setText("    " + (1 + position)
				+ mList.get(position).toString());
		return view;
	}
}
