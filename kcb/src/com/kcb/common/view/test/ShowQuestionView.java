package com.kcb.common.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcbTeam.R;

public class ShowQuestionView extends LinearLayout {

    public ShowQuestionView(Context context) {
        super(context);
        init(context);
    }

    public ShowQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowQuestionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private TextView questionIndexTextView;

    private TextView titleTextView;
    private ImageView titleImageView;

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
    private ImageView dCheckBoxd;

    private Context mContext;

    public void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.comm_view_question_show, this);
        initView();
    }

    private void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_question_index);

        titleTextView = (TextView) findViewById(R.id.textview_title);
        titleImageView = (ImageView) findViewById(R.id.imageview_title);

        aTextView = (TextView) findViewById(R.id.textview_A);
        aImageView = (ImageView) findViewById(R.id.imageview_A);
        aCheckBox = (ImageView) findViewById(R.id.checkBox_A);

        bTextView = (TextView) findViewById(R.id.textview_B);
        bImageView = (ImageView) findViewById(R.id.imageview_B);
        bCheckBox = (ImageView) findViewById(R.id.checkBox_B);

        cTextView = (TextView) findViewById(R.id.textview_C);
        cImageView = (ImageView) findViewById(R.id.imageview_C);
        cCheckBox = (ImageView) findViewById(R.id.checkBox_C);

        dTextView = (TextView) findViewById(R.id.textview_D);
        dImageView = (ImageView) findViewById(R.id.imageview_D);
        dCheckBoxd = (ImageView) findViewById(R.id.checkBox_D);
    }

    public void showQuestion(int questionIndex, Question question) {
        questionIndexTextView.setText(String.format(
                mContext.getString(R.string.tch_question_index), questionIndex + 1));

        showQuestionItem(titleTextView, titleImageView, null, question.getTitle());
        showQuestionItem(aTextView, aImageView, aCheckBox, question.getChoiceA());
        showQuestionItem(bTextView, bImageView, bCheckBox, question.getChoiceB());
        showQuestionItem(cTextView, cImageView, cCheckBox, question.getChoiceC());
        showQuestionItem(dTextView, dImageView, dCheckBoxd, question.getChoiceD());
    }

    private void showQuestionItem(TextView textView, ImageView imageView, ImageView checkIcon,
            QuestionItem item) {
        if (item.isText()) {
            textView.setText(item.getText());
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        } else {
            textView.setText("");
            textView.setVisibility(View.GONE);
            new LoadBitmapAsyncTask(imageView).execute(item);
            imageView.setVisibility(View.VISIBLE);
        }
        if (null != checkIcon) {
            if (item.isSelected()) {
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

    public void release() {
        mContext = null;
    }
}
