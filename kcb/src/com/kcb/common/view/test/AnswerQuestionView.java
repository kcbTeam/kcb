package com.kcb.common.view.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.base.BaseLinearLayout;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ImageLoaderUtil;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcbTeam.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @className: AnswerQuestionView
 * @description: 学生回答题目
 * @author: wanghang
 * @date: 2015-6-11 下午3:33:31
 */
public class AnswerQuestionView extends BaseLinearLayout {

    public AnswerQuestionView(Context context) {
        super(context);
    }

    public AnswerQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnswerQuestionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private TextView questionIndexTextView;

    private TextView titleTextView;
    private ImageView titleImageView;

    private TextView aTextView;
    private ImageView aImageView;

    private TextView bTextView;
    private ImageView bImageView;

    private TextView cTextView;
    private ImageView cImageView;

    private TextView dTextView;
    private ImageView dImageView;

    private CheckBox aCheckBox;
    private CheckBox bCheckBox;
    private CheckBox cCheckBox;
    private CheckBox dCheckBox;

    private Question mQuestion;

    @Override
    public void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.comm_view_question_answer, this);
        initView();
    }

    @Override
    public void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_question_num);

        titleTextView = (TextView) findViewById(R.id.textview_title);
        titleImageView = (ImageView) findViewById(R.id.imageview_title);
        titleImageView.setOnClickListener(this);

        aTextView = (TextView) findViewById(R.id.textview_A);
        aImageView = (ImageView) findViewById(R.id.imageview_A);
        aImageView.setOnClickListener(this);

        bTextView = (TextView) findViewById(R.id.textview_B);
        bImageView = (ImageView) findViewById(R.id.imageview_B);
        bImageView.setOnClickListener(this);

        cTextView = (TextView) findViewById(R.id.textview_C);
        cImageView = (ImageView) findViewById(R.id.imageview_C);
        cImageView.setOnClickListener(this);

        dTextView = (TextView) findViewById(R.id.textview_D);
        dImageView = (ImageView) findViewById(R.id.imageview_D);
        dImageView.setOnClickListener(this);

        aCheckBox = (CheckBox) findViewById(R.id.checkBox_A);
        bCheckBox = (CheckBox) findViewById(R.id.checkBox_B);
        cCheckBox = (CheckBox) findViewById(R.id.checkBox_C);
        dCheckBox = (CheckBox) findViewById(R.id.checkBox_D);
    }

    @Override
    public void release() {
        mContext = null;
        mQuestion = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_title:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_title, mQuestion.getTitle());
                break;
            case R.id.imageview_A:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_A, mQuestion.getChoiceA());
                break;
            case R.id.imageview_B:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_B, mQuestion.getChoiceB());
                break;
            case R.id.imageview_C:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_C, mQuestion.getChoiceC());
                break;
            case R.id.imageview_D:
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

        showQuestionItem(titleTextView, titleImageView, mQuestion.getTitle());
        showQuestionItem(aTextView, aImageView, mQuestion.getChoiceA());
        showQuestionItem(bTextView, bImageView, mQuestion.getChoiceB());
        showQuestionItem(cTextView, cImageView, mQuestion.getChoiceC());
        showQuestionItem(dTextView, dImageView, mQuestion.getChoiceD());

        aCheckBox.setChecked(mQuestion.getChoiceA().isSelected());
        bCheckBox.setChecked(mQuestion.getChoiceB().isSelected());
        cCheckBox.setChecked(mQuestion.getChoiceC().isSelected());
        dCheckBox.setChecked(mQuestion.getChoiceD().isSelected());
    }

    // 如果是文字，直接显示；如果是图片，使用UIL加载；
    private void showQuestionItem(TextView textView, ImageView imageView, QuestionItem item) {
        if (item.isText()) {
            textView.setText(item.getText());
        } else {
            ImageLoader.getInstance().displayImage(item.getBitmapUrl(), imageView,
                    ImageLoaderUtil.getOptions());
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
}
