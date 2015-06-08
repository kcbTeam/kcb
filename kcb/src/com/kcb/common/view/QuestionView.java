package com.kcb.common.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcbTeam.R;

public class QuestionView extends LinearLayout {

    public QuestionView(Context context) {
        super(context);
        init(context);
    }

    public QuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuestionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private TextView questionIndexTextView;

    private TextView titleTextView;
    private TextView choiceATextView;
    private TextView choiceBTextView;
    private TextView choiceCTextView;
    private TextView choiceDTextView;

    private ImageView checkBoxA;
    private ImageView checkBoxB;
    private ImageView checkBoxC;
    private ImageView checkBoxD;

    private Context mContext;

    public void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.comm_view_question, null);
        initView();
    }

    private void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_question_index);

        titleTextView = (TextView) findViewById(R.id.textview_title);
        choiceATextView = (TextView) findViewById(R.id.textview_A);
        choiceBTextView = (TextView) findViewById(R.id.textview_B);
        choiceCTextView = (TextView) findViewById(R.id.textview_C);
        choiceDTextView = (TextView) findViewById(R.id.textview_D);

        checkBoxA = (ImageView) findViewById(R.id.checkBox_A);
        checkBoxB = (ImageView) findViewById(R.id.checkBox_B);
        checkBoxC = (ImageView) findViewById(R.id.checkBox_C);
        checkBoxD = (ImageView) findViewById(R.id.checkBox_D);
    }

    public void showQuestionIndex(String text) {
        questionIndexTextView.setText(text);
    }

    public void showQuestion(Question question) {
        showQuestionItem(titleTextView, null, question.getTitle());
        showQuestionItem(choiceATextView, checkBoxA, question.getChoiceA());
        showQuestionItem(choiceBTextView, checkBoxB, question.getChoiceB());
        showQuestionItem(choiceCTextView, checkBoxC, question.getChoiceC());
        showQuestionItem(choiceDTextView, checkBoxD, question.getChoiceD());
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(TextView view, ImageView checkIcon, QuestionItem item) {
        if (item.isText()) {
            view.setText(item.getText());
            view.setBackgroundResource(0);
        } else {
            view.setText("");
            view.setBackgroundDrawable(new BitmapDrawable(item.getBitmap()));
        }
        if (null != checkIcon) {
            if (item.isSelected()) {
                checkIcon.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
            } else {
                checkIcon.setBackgroundResource(R.drawable.ic_check_box_outline_blank_grey600_18dp);
            }
        }
    }
}
