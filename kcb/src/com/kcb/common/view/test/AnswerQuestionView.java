package com.kcb.common.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.util.DialogUtil;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcbTeam.R;

public class AnswerQuestionView extends LinearLayout implements OnClickListener {

    public AnswerQuestionView(Context context) {
        super(context);
        init(context);
    }

    public AnswerQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnswerQuestionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

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

    private Context mContext;
    private Question mQuestion;

    private void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.comm_view_question_answer, this);
        initView();
    }

    private void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_question_num);

        titleTextView = (TextView) findViewById(R.id.textview_question_title);
        titleTextView.setOnClickListener(this);
        aTextView = (TextView) findViewById(R.id.textview_A);
        aTextView.setOnClickListener(this);
        bTextView = (TextView) findViewById(R.id.textview_B);
        bTextView.setOnClickListener(this);
        cTextView = (TextView) findViewById(R.id.textview_C);
        cTextView.setOnClickListener(this);
        dTextView = (TextView) findViewById(R.id.textview_D);
        dTextView.setOnClickListener(this);

        aCheckBox = (CheckBox) findViewById(R.id.checkBox_A);
        bCheckBox = (CheckBox) findViewById(R.id.checkBox_B);
        cCheckBox = (CheckBox) findViewById(R.id.checkBox_C);
        dCheckBox = (CheckBox) findViewById(R.id.checkBox_D);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_question_title:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_title, mQuestion.getTitle());
                break;
            case R.id.textview_A:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_A, mQuestion.getChoiceA());
                break;
            case R.id.textview_B:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_B, mQuestion.getChoiceB());
                break;
            case R.id.textview_C:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_C, mQuestion.getChoiceC());
                break;
            case R.id.textview_D:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_D, mQuestion.getChoiceD());
                break;
            default:
                break;
        }
    }

    public void showQuestion(Question question, int questionIndex) {
        mQuestion = question;

        questionIndexTextView.setText(String.format(
                mContext.getString(R.string.stu_question_index), questionIndex + 1));

        showQuestionItem(titleTextView, mQuestion.getTitle());
        showQuestionItem(aTextView, mQuestion.getChoiceA());
        showQuestionItem(bTextView, mQuestion.getChoiceB());
        showQuestionItem(cTextView, mQuestion.getChoiceC());
        showQuestionItem(dTextView, mQuestion.getChoiceD());

        aCheckBox.setChecked(mQuestion.getChoiceA().isSelected());
        bCheckBox.setChecked(mQuestion.getChoiceB().isSelected());
        cCheckBox.setChecked(mQuestion.getChoiceC().isSelected());
        dCheckBox.setChecked(mQuestion.getChoiceD().isSelected());
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(TextView textView, QuestionItem item) {
        if (item.isText()) {
            textView.setText(item.getText());
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                textView.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
        }
    }

    /**
     * 单击图片显示放大的图片
     */
    private void showBitmapDialog(int titleResId, QuestionItem item) {
        // 先判断是不是图片
        if (!item.isText()) {
            DialogUtil.showBitmapDialog(mContext, titleResId, item.getBitmap(), null, -1);
        }
    }

    public void saveAnswer() {
        QuestionItem aItem = mQuestion.getChoiceA();
        QuestionItem bItem = mQuestion.getChoiceB();
        QuestionItem cItem = mQuestion.getChoiceC();
        QuestionItem dItem = mQuestion.getChoiceD();
        aItem.setIsSelected(aCheckBox.isCheck());
        bItem.setIsSelected(bCheckBox.isCheck());
        cItem.setIsSelected(cCheckBox.isCheck());
        dItem.setIsSelected(dCheckBox.isCheck());
    }

    public void release() {
        mContext = null;
        mQuestion = null;
    }
}
