package com.kcb.student.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcbTeam.R;


public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.CheckinViewHolder> {
    private String[] ItemStrings;
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

    @Override
    public CheckinViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = LayoutInflater.from(arg0.getContext()).inflate(R.layout.student_vhitem, null);
        CheckinViewHolder mh = new CheckinViewHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(CheckinViewHolder viewHolder, int position) {
        String itemString = ItemStrings[position];
        viewHolder.textView.setText(itemString);
    }

    public class CheckinViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public CheckinViewHolder(View textview) {
            super(textview);
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
