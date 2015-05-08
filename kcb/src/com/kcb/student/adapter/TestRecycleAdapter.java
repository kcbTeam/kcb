package com.kcb.student.adapter;

import java.util.List;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcbTeam.R;

public class TestRecycleAdapter extends
		RecyclerView.Adapter<TestRecycleAdapter.TestViewHolder> {

	public List<String> mItems;

	public TestRecycleAdapter(List<String> items) {
		super();
		mItems = items;
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	@Override
	public TestViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.stu_view_test_recycler, null);
		TestViewHolder viewHolder = new TestViewHolder(view);
		return viewHolder;
	}

	public class TestViewHolder extends RecyclerView.ViewHolder {
		private TextView textView;

		public TestViewHolder(View v) {
			super(v);
			textView = (TextView) v.findViewById(R.id.viewblank);
		}
	}

	@Override
	public void onBindViewHolder(TestViewHolder viewHolder, int position) {
		viewHolder.textView.setBackgroundColor(Color.parseColor(mItems
				.get(position)));
	}

}
