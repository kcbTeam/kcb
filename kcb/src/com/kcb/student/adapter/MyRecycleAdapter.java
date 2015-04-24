package com.kcb.student.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcb.student.util.ItemBeam;
import com.kcbTeam.R;

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.TextHoler>
        implements
            View.OnClickListener {

    private List<ItemBeam> mItemBeams;
    public static MyItemClickListener mItemClickListener = null;

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public MyRecycleAdapter(List<ItemBeam> itemBeams) {
        super();
        this.mItemBeams = itemBeams;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        mItemClickListener = listener;
    }

    public class TextHoler extends RecyclerView.ViewHolder implements OnClickListener {
        public TextView textView1;
        private MyItemClickListener mListener;

        public TextHoler(View textview, MyItemClickListener listener) {
            super(textview);
            this.textView1 = (TextView) textview.findViewById(R.id.textview_clicknum1);
            this.mListener = listener;
            this.textView1.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }

    }

    @Override
    public int getItemCount() {
        return mItemBeams.size();

    }

    @Override
    public TextHoler onCreateViewHolder(ViewGroup arg0, int arg1) {
        View v = LayoutInflater.from(arg0.getContext()).inflate(R.layout.student_vhitem, null);
        TextHoler mh = new TextHoler(v, mItemClickListener);
        return mh;
    }

    @Override
    public void onBindViewHolder(TextHoler arg0, int arg1) {
        ItemBeam item = mItemBeams.get(arg1);
        arg0.textView1.setText(item.itemInt1);
    }

    @Override
    public void onClick(View arg0) {}

}
