package com.kcb.teacher.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.QuestionItem;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

/**
 * 
 * @className: ListAdapterQuestions
 * @description:
 * @author: ZQJ
 * @date: 2015年5月11日 下午10:34:21
 */
public class SetTestTimeAdapter extends BaseAdapter {

    public interface EditQuestionListener {
        void onEdit(int index, Question question);
    }

    private Context mContext;
    private Test mTest;
    private EditQuestionListener mListener;

    public SetTestTimeAdapter(Context context, Test test, EditQuestionListener listener) {
        mContext = context;
        mTest = test;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mTest.getQuestionNum();
    }

    @Override
    public Question getItem(int position) {
        return mTest.getQuestion(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItem(int index, Question question) {
        mTest.updateQuestion(index, question);
    }

    public void deleteItem(int index) {
        mTest.deleteQuestion(index);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_settesttime, null);
            viewHolder = new ViewHolder();
            viewHolder.questionIndexTextView =
                    (TextView) convertView.findViewById(R.id.textview_questionindex);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.textview_title);
            viewHolder.editImageView = (ImageView) convertView.findViewById(R.id.imageview_edit);
            viewHolder.editImageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mListener.onEdit(position, getItem(position));
                }
            });
            viewHolder.aTextView = (TextView) convertView.findViewById(R.id.textview_A);
            viewHolder.aCheckBox = (ImageView) convertView.findViewById(R.id.checkBox_A);
            viewHolder.bTextView = (TextView) convertView.findViewById(R.id.textview_B);
            viewHolder.bCheckBox = (ImageView) convertView.findViewById(R.id.checkBox_B);
            viewHolder.cTextView = (TextView) convertView.findViewById(R.id.textview_C);
            viewHolder.cCheckBox = (ImageView) convertView.findViewById(R.id.checkBox_C);
            viewHolder.dTextView = (TextView) convertView.findViewById(R.id.textview_D);
            viewHolder.dCheckBox = (ImageView) convertView.findViewById(R.id.checkBox_D);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setQuestion(position, getItem(position));
        return convertView;
    }

    // TODO use ImageView for low api phone
    private class ViewHolder {
        private final String questionIndexString = "第%1$d题";

        private TextView questionIndexTextView;
        private TextView titleTextView;
        private ImageView editImageView;
        private TextView aTextView;
        private TextView bTextView;
        private TextView cTextView;
        private TextView dTextView;
        private ImageView aCheckBox;
        private ImageView bCheckBox;
        private ImageView cCheckBox;
        private ImageView dCheckBox;

        @SuppressLint("NewApi")
        public void setQuestion(int index, Question question) {
            questionIndexTextView.setText(String.format(questionIndexString, index + 1));
            showContent(titleTextView, null, question.getTitle());
            showContent(aTextView, aCheckBox, question.getChoiceA());
            showContent(bTextView, bCheckBox, question.getChoiceB());
            showContent(cTextView, cCheckBox, question.getChoiceC());
            showContent(dTextView, dCheckBox, question.getChoiceD());
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void showContent(TextView view, ImageView checkIcon, QuestionItem item) {
        if (item.isText()) {
            view.setText(item.getText());
        } else {
            view.setBackground(new BitmapDrawable(item.getBitmap()));
        }
        if (null != checkIcon) {
            if (item.isRight()) {
                checkIcon.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
            } else {
                checkIcon.setBackgroundResource(R.drawable.ic_check_box_outline_blank_black_18dp);
            }
        }
    }
}
