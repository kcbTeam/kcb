package com.kcb.student.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kcbTeam.R;


public class CheckinRecycleAdapter
        extends RecyclerView.Adapter<CheckinRecycleAdapter.CheckinViewHolder> {


    private String[] mItems;
    public ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public CheckinRecycleAdapter(String[] itemStrings) {
        super();
        mItems = itemStrings;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
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
        viewHolder.textView.setText(mItems[position]);
    }

    public class CheckinViewHolder extends RecyclerView.ViewHolder {


        private TextView textView;

        public void setText(String strings) {
            textView.setText(strings);
        }

        public CheckinViewHolder(View textview) {
            super(textview);
            textView = (TextView) textview.findViewById(R.id.textview);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mItemClickListener) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                }
            });
        }
    }
}
