package com.kcb.teacher.adapter.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.model.test.Test;
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
            viewHolder.questionIndexTextView =
                    (TextView) convertView.findViewById(R.id.textview_questionindex);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.textview_title);
            viewHolder.titleImageView = (ImageView) convertView.findViewById(R.id.imageview_title);
            viewHolder.editButton = (ButtonFlat) convertView.findViewById(R.id.button_edit);
            viewHolder.aTextView = (TextView) convertView.findViewById(R.id.textview_A);
            viewHolder.aImageView = (ImageView) convertView.findViewById(R.id.imageview_A);
            viewHolder.aCheckBox = (ImageView) convertView.findViewById(R.id.checkBox_A);
            viewHolder.bTextView = (TextView) convertView.findViewById(R.id.textview_B);
            viewHolder.bImageView = (ImageView) convertView.findViewById(R.id.imageview_B);
            viewHolder.bCheckBox = (ImageView) convertView.findViewById(R.id.checkBox_B);
            viewHolder.cTextView = (TextView) convertView.findViewById(R.id.textview_C);
            viewHolder.cImageView = (ImageView) convertView.findViewById(R.id.imageview_C);
            viewHolder.cCheckBox = (ImageView) convertView.findViewById(R.id.checkBox_C);
            viewHolder.dTextView = (TextView) convertView.findViewById(R.id.textview_D);
            viewHolder.dImageView = (ImageView) convertView.findViewById(R.id.imageview_D);
            viewHolder.dCheckBox = (ImageView) convertView.findViewById(R.id.checkBox_D);
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
        private TextView questionIndexTextView;

        private TextView titleTextView;
        private ImageView titleImageView;

        private ButtonFlat editButton;

        private TextView aTextView;
        private ImageView aImageView;
        private ImageView aCheckBox;

        private TextView bTextView;
        private ImageView bImageView;
        private ImageView bCheckBox;

        private TextView cTextView;
        private ImageView cImageView;
        private ImageView cCheckBox;

        private TextView dTextView;
        private ImageView dImageView;
        private ImageView dCheckBox;

        public void setQuestion(int index, Question question) {
            questionIndexTextView.setText(String.format(
                    mContext.getString(R.string.tch_question_index), index + 1));
            showContent(titleTextView, titleImageView, null, question.getTitle());
            showContent(aTextView, aImageView, aCheckBox, question.getChoiceA());
            showContent(bTextView, bImageView, bCheckBox, question.getChoiceB());
            showContent(cTextView, cImageView, cCheckBox, question.getChoiceC());
            showContent(dTextView, dImageView, dCheckBox, question.getChoiceD());
        }
    }

    private void showContent(TextView textView, ImageView imageView, ImageView checkIcon,
            QuestionItem item) {
        if (item.isText()) {
            textView.setText(item.getText());
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            new LoadBitmapAsyncTask(imageView).execute(item);
        }
        if (null != checkIcon) {
            if (item.isRight()) {
                checkIcon.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
            } else {
                checkIcon.setBackgroundResource(R.drawable.ic_check_box_outline_blank_grey600_18dp);
            }
        }
    }

    private class LoadBitmapAsyncTask extends AsyncTask<QuestionItem, Integer, Bitmap> {

        private ImageView imageView;

        public LoadBitmapAsyncTask(ImageView _imageView) {
            imageView = _imageView;
        }

        @Override
        protected Bitmap doInBackground(QuestionItem... params) {
            Bitmap bitmap = params[0].getBitmap();
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
