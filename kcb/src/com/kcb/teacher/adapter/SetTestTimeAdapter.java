package com.kcb.teacher.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcb.library.view.checkbox.CheckBox;
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

    private Context mContext;
    private Test mTest;

    public SetTestTimeAdapter(Context context, Test test) {
        mContext = context;
        mTest = test;
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_settesttime, null);
            viewHolder = new ViewHolder();
            viewHolder.questionIndexTextView =
                    (TextView) convertView.findViewById(R.id.textview_questionindex);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.textview_title);
            viewHolder.aTextView = (TextView) convertView.findViewById(R.id.textview_A);
            viewHolder.aCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox_A);
            viewHolder.bTextView = (TextView) convertView.findViewById(R.id.textview_B);
            viewHolder.bCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox_B);
            viewHolder.cTextView = (TextView) convertView.findViewById(R.id.textview_C);
            viewHolder.cCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox_C);
            viewHolder.dTextView = (TextView) convertView.findViewById(R.id.textview_D);
            viewHolder.dCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox_D);
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
        private TextView aTextView;
        private TextView bTextView;
        private TextView cTextView;
        private TextView dTextView;
        private CheckBox aCheckBox;
        private CheckBox bCheckBox;
        private CheckBox cCheckBox;
        private CheckBox dCheckBox;

        @SuppressLint("NewApi")
        public void setQuestion(int index, Question question) {
            questionIndexTextView.setText(String.format(questionIndexString, index + 1));

            QuestionItem titleItem = question.getTitle();
            showContent(titleTextView, titleItem);

            QuestionItem aItem = question.getChoiceA();
            showContent(aTextView, aItem);
            aCheckBox.setChecked(aItem.isRight());

            QuestionItem bItem = question.getChoiceB();
            showContent(bTextView, bItem);
            bCheckBox.setChecked(bItem.isRight());

            QuestionItem cItem = question.getChoiceC();
            showContent(cTextView, cItem);
            cCheckBox.setChecked(cItem.isRight());

            QuestionItem dItem = question.getChoiceD();
            showContent(dTextView, dItem);
            dCheckBox.setChecked(dItem.isRight());
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void showContent(TextView view, QuestionItem content) {
        if (content.isText()) {
            view.setText(content.getText());
        } else {
            view.setBackground(new BitmapDrawable(content.getBitmap()));
        }
    }
}
