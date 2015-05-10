package com.kcb.student.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcbTeam.R;

public class TestRecycleAdapter extends RecyclerView.Adapter<TestRecycleAdapter.TestViewHolder> {

    private int mSize;
    private int mCurrentIndex;

    public TestRecycleAdapter(int size) {
        super();
        mSize = size;
        mCurrentIndex = 0;
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    public void setCurrentIndex(int index) {
        mCurrentIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.stu_view_test_recycler, null);
        TestViewHolder viewHolder = new TestViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TestViewHolder viewHolder, int position) {
        if (position == mCurrentIndex) {
            viewHolder.setViewSelectColor();
        } else {
            viewHolder.setViewNormalColor();
        }
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {
        private TextView view;

        public TestViewHolder(View v) {
            super(v);
            view = (TextView) v.findViewById(R.id.viewblank);
        }

        public void setViewSelectColor() {
            view.setBackgroundColor(Color.parseColor("#00cc00"));
        }

        public void setViewNormalColor() {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }
}
