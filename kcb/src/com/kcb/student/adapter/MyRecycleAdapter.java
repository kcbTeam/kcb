package com.kcb.student.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcbTeam.R;

// TODO
// rename to CheckinRecyclerAdapter
public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.CheckinViewHolder> {

    // TODO rename to mItems
    private String[] ItemStrings;
    // TODO needn't =null, default is null
    public ItemClickListener mItemClickListener = null;

    public interface ItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public MyRecycleAdapter(String[] itemStrings) {
        super();
        ItemStrings = itemStrings;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return ItemStrings.length;
    }

    // TODO rename arg0 to viewGroup, rename arg1 to position
    // rename stu_vhitem to stu_view_checkin_recycler
    @SuppressLint("InflateParams")
    @Override
    public CheckinViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = LayoutInflater.from(arg0.getContext()).inflate(R.layout.stu_vhitem, null);
        // TODO rename mh to viewHolder
        CheckinViewHolder mh = new CheckinViewHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(CheckinViewHolder viewHolder, int position) {
        String itemString = ItemStrings[position];
        // TODO use viewHolder.setText(mItems[position]);
        viewHolder.textView.setText(itemString);
    }

    public class CheckinViewHolder extends RecyclerView.ViewHolder {
        // TODO public to private
        // add public void setText(); function
        public TextView textView;

        public CheckinViewHolder(View textview) {
            super(textview);
            // TODO rename textview_clicknum1 to textview
            textView = (TextView) textview.findViewById(R.id.textview_clicknum1);
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
