package com.kcb.common.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcbTeam.R;

public class AnswerQuestionView extends LinearLayout {

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
    private TextView choiceATextView;
    private TextView choiceBTextView;
    private TextView choiceCTextView;
    private TextView choiceDTextView;

    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private CheckBox checkBoxD;

    private Context mContext;
    private Question mQuestion;

    // tag title/A/B/C/D;
    private final int FLAG_TITLE = 1;
    private final int FLAG_A = 2;
    private final int FALG_B = 3;
    private final int FLAG_C = 4;
    private final int FLAG_D = 5;

    private void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.comm_view_question_answer, this);
        initView();
    }

    private void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_question_num);

        titleTextView = (TextView) findViewById(R.id.textview_question_title);
        choiceATextView = (TextView) findViewById(R.id.textview_A);
        choiceBTextView = (TextView) findViewById(R.id.textview_B);
        choiceCTextView = (TextView) findViewById(R.id.textview_C);
        choiceDTextView = (TextView) findViewById(R.id.textview_D);

        checkBoxA = (CheckBox) findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) findViewById(R.id.checkBox_D);
    }

    public void showQuestion(Question question, int questionIndex) {
        mQuestion = question;

        questionIndexTextView.setText(String.format(
                mContext.getString(R.string.stu_question_index), questionIndex + 1));

        showQuestionItem(FLAG_TITLE, mQuestion.getTitle());
        showQuestionItem(FLAG_A, mQuestion.getChoiceA());
        showQuestionItem(FALG_B, mQuestion.getChoiceB());
        showQuestionItem(FLAG_C, mQuestion.getChoiceC());
        showQuestionItem(FLAG_D, mQuestion.getChoiceD());

        checkBoxA.setChecked(mQuestion.getChoiceA().isSelected());
        checkBoxB.setChecked(mQuestion.getChoiceB().isSelected());
        checkBoxC.setChecked(mQuestion.getChoiceC().isSelected());
        checkBoxD.setChecked(mQuestion.getChoiceD().isSelected());
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(int flag, QuestionItem item) {
        TextView textView = getTextViewByTag(flag);
        if (item.isText()) {
            textView.setText(item.getText());
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                textView.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
        }
    }

    private TextView getTextViewByTag(int flag) {
        TextView textView = null;
        switch (flag) {
            case FLAG_TITLE:
                textView = titleTextView;
                break;
            case FLAG_A:
                textView = choiceATextView;
                break;
            case FALG_B:
                textView = choiceBTextView;
                break;
            case FLAG_C:
                textView = choiceCTextView;
                break;
            case FLAG_D:
                textView = choiceDTextView;
                break;
            default:
                break;
        }
        return textView;
    }

    public void saveAnswer() {
        QuestionItem aItem = mQuestion.getChoiceA();
        QuestionItem bItem = mQuestion.getChoiceB();
        QuestionItem cItem = mQuestion.getChoiceC();
        QuestionItem dItem = mQuestion.getChoiceD();
        aItem.setIsSelected(checkBoxA.isCheck());
        bItem.setIsSelected(checkBoxB.isCheck());
        cItem.setIsSelected(checkBoxC.isCheck());
        dItem.setIsSelected(checkBoxD.isCheck());
    }

    public void release() {
        mContext = null;
        mQuestion = null;
    }
}
