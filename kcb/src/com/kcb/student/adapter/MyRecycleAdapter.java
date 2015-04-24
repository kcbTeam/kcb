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

// TODO
// 1, rename to CheckinRecyclerAdapter
// 2, why implements View.OnclickListener?
public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.TextHoler>
        implements
            View.OnClickListener {

    private List<ItemBeam> mItemBeams;
    public static MyItemClickListener mItemClickListener = null;

    // TODO rename to ItemClickListener
    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public MyRecycleAdapter(List<ItemBeam> itemBeams) {
        super();
        // TODO delete this.
        this.mItemBeams = itemBeams;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mItemBeams.size();

    }

    @Override
    public TextHoler onCreateViewHolder(ViewGroup arg0, int arg1) {
        // TODO
        // 1, rename v to view
        // 2, rename student_vhitem to item_stu_checkin
        View v = LayoutInflater.from(arg0.getContext()).inflate(R.layout.student_vhitem, null);
        TextHoler mh = new TextHoler(v, mItemClickListener);
        return mh;
    }

    // TODO
    // 1, rename arg0 to viewHolder
    // 2, rename arg1 to position
    @Override
    public void onBindViewHolder(TextHoler arg0, int arg1) {
        ItemBeam item = mItemBeams.get(arg1);
        arg0.textView1.setText(item.itemInt1);
    }

    @Override
    public void onClick(View arg0) {}

    // TODO
    // 1, rename TextHoler to CheckinViewHolder
    // 2, needn't implements OnClickListener
    public class TextHoler extends RecyclerView.ViewHolder implements OnClickListener {
        // TODO rename textView1 to textView;
        public TextView textView1;

        // TODO needn't mListener
        private MyItemClickListener mListener;

        // TODO
        // rename textview to view
        public TextHoler(View textview, MyItemClickListener listener) {
            super(textview);
            this.textView1 = (TextView) textview.findViewById(R.id.textview_clicknum1);
            this.mListener = listener;
            this.textView1.setOnClickListener(this);

            // use below code
            // textview.setOnClickListener(new OnClickListener() {
            //
            // @Override
            // public void onClick(View v) {
            // if (null!=mListener) {
            // mListener.onItemClick(v, getPosition());
            // }
            // }
            // });

        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }

    }

}
