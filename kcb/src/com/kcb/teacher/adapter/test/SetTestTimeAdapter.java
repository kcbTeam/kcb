package com.kcb.teacher.adapter.test;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.Test;
import com.kcb.common.view.test.ShowQuestionView;
import com.kcb.library.view.buttonflat.ButtonFlat;
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

    public void release() {
        mContext = null;
        mTest = null;
        mListener = null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.tch_listitem_set_test_time, null);
            viewHolder = new ViewHolder();
            viewHolder.showQuestionView =
                    (ShowQuestionView) convertView.findViewById(R.id.showquestionview);
            viewHolder.editButton = (ButtonFlat) convertView.findViewById(R.id.button_edit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.editButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.onEdit(position, getItem(position));
            }
        });
        viewHolder.setQuestion(position, getItem(position));
        return convertView;
    }

    private class ViewHolder {

        private ShowQuestionView showQuestionView;
        private ButtonFlat editButton;

        public void setQuestion(int questionIndex, Question question) {
            showQuestionView.setType(ShowQuestionView.TYPE_TEACHER);
            showQuestionView.showQuestion(questionIndex, question);
        }
    }
}
