package com.kcb.teacher.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcbTeam.R;

public class ListAdapterChoices extends BaseAdapter {

    private List<QuestionItem> mQuestonItemList;
    private Context mContext;
    private final int CHOICE_NUM = 4;

    private final String[] choiceTitleString = {"A", "B", "C", "D"};
    private final String mRateFormat = "%1$d%%";

    public ListAdapterChoices(Context context, Question question) {
        mContext = context;
        mQuestonItemList = new ArrayList<QuestionItem>();
        mQuestonItemList.add(question.getChoiceA());
        mQuestonItemList.add(question.getChoiceB());
        mQuestonItemList.add(question.getChoiceC());
        mQuestonItemList.add(question.getChoiceD());
    }

    @Override
    public int getCount() {
        return CHOICE_NUM;
    }

    @Override
    public Object getItem(int position) {
        return mQuestonItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            View view = View.inflate(mContext, R.layout.tch_listitem_choices, null);
            holder = new ViewHolder();
            holder.choiceTitle = (TextView) view.findViewById(R.id.textview_choice);
            holder.choiceContent = (EditText) view.findViewById(R.id.edittext_content);
            holder.choiceRate = (TextView) view.findViewById(R.id.textview_rate);
            convertView = view;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        QuestionItem tempItem = mQuestonItemList.get(position);
        holder.choiceTitle.setText(choiceTitleString[position]);
        if (tempItem.isRight()) {
            holder.choiceTitle.setTextColor(Color.RED);
        } else {
            holder.choiceTitle.setTextColor(Color.BLACK);
        }
        if (tempItem.isText()) {
            holder.choiceContent.setText(tempItem.getText());
        } else {
            holder.choiceContent.setBackground(new BitmapDrawable(tempItem.getBitmap()));
        }
        holder.choiceRate.setText(String.format(mRateFormat, (int) (100 * tempItem.getRate())));
        return convertView;
    }

    static class ViewHolder {
        TextView choiceTitle;
        EditText choiceContent;
        TextView choiceRate;
    }
}
