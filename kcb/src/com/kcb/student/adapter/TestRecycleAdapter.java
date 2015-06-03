package com.kcb.student.adapter;

import android.annotation.SuppressLint;
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

    @SuppressLint("InflateParams")
    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.stu_recycleritem_test, null);
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
            view.setBackgroundColor(Color.parseColor("#db7093"));
        }

        public void setViewNormalColor() {
            view.setBackgroundColor(Color.parseColor("#eeeeee"));
        }
    }
}
