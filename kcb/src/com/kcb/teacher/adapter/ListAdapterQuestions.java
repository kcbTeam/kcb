package com.kcb.teacher.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.kcb.teacher.model.ChoiceQuestion;
import com.kcb.teacher.model.TextContent;
import com.kcbTeam.R;

/**
 * 
 * @className: ListAdapterQuestions
 * @description:
 * @author: ZQJ
 * @date: 2015年5月11日 下午10:34:21
 */
public class ListAdapterQuestions extends BaseAdapter {
    @SuppressWarnings("unused")
    private static final String TAG = "ListAdapterQuestions";
    private final String numFormatString = "题%1$d";


    private List<ChoiceQuestion> mList;
    private Context mContext;

    public ListAdapterQuestions(Context context, List<ChoiceQuestion> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ChoiceQuestion temp = mList.get(position);
        if (mList.get(position).isAllString()) {
            view = View.inflate(mContext, R.layout.tch_listview_questions_item_string, null);
            TextView questionNum = (TextView) view.findViewById(R.id.textview_question_num);
            questionNum.setText(String.format(numFormatString, 1 + position));

            TextView content = (TextView) view.findViewById(R.id.textview_content);
            content.setText(temp.contentString());

            TextView correctOptions = (TextView) view.findViewById(R.id.textview_answer);
            correctOptions.setText(temp.getCorrectOptionString());

        } else {
            view = View.inflate(mContext, R.layout.tch_listview_questions_item_common, null);

            TextView questionNumHint = (TextView) view.findViewById(R.id.textview_question_num);
            questionNumHint.setText(String.format(numFormatString, 1 + position));

            EditText quesitionEditText = (EditText) view.findViewById(R.id.edittext_question);
            showContent(quesitionEditText, temp.getQuestion());

            EditText optionAEditText = (EditText) view.findViewById(R.id.edittext_A);
            showContent(optionAEditText, temp.getOptionA());

            EditText optionBEditText = (EditText) view.findViewById(R.id.edittext_B);
            showContent(optionBEditText, temp.getOptionB());

            EditText optionCEditText = (EditText) view.findViewById(R.id.edittext_C);
            showContent(optionCEditText, temp.getOptionC());

            EditText optionDEditText = (EditText) view.findViewById(R.id.edittext_D);
            showContent(optionDEditText, temp.getOptionD());

            TextView correctOptions = (TextView) view.findViewById(R.id.textview_answer);
            correctOptions.setText(mList.get(position).getCorrectOptionString());
        }

        return view;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void showContent(EditText view, TextContent content) {
        if (content.isString()) {
            view.setText(content.getContentString());
        } else {
            view.setBackground(new BitmapDrawable(content.getContentBitmap()));
        }
    }
}
