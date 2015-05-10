package com.kcb.student.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class CheckInRecycleAdapter
        extends RecyclerView.Adapter<CheckInRecycleAdapter.CheckinViewHolder> {

    private String[] mItems;
    public RecyclerItemClickListener mItemClickListener;

    public interface RecyclerItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public CheckInRecycleAdapter() {
        super();
        mItems = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "清除", "0", "<--"};
    }

    public void setRecyclerItemClickListener(RecyclerItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }

    @SuppressLint("InflateParams")
    @Override
    public CheckinViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.stu_view_checkin_recycler, null);
        CheckinViewHolder viewHolder = new CheckinViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CheckinViewHolder viewHolder, int position) {
        viewHolder.setText(mItems[position]);
    }

    public class CheckinViewHolder extends RecyclerView.ViewHolder {
        private ButtonFlat numButton;

        public CheckinViewHolder(View view) {
            super(view);
            numButton = (ButtonFlat) view.findViewById(R.id.button_num);
            numButton.setBackgroundResource(R.drawable.stu_checkin_textview);
            numButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mItemClickListener) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                }
            });
        }

        public void setText(String num) {
            numButton.setText(num);
        }
    }
}
