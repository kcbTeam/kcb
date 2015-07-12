package com.kcb.common.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.base.BaseLinearLayout;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ImageLoaderUtil;
import com.kcbTeam.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @className: ShowQuestionView
 * @description: 需要实现CustomViewListener;
 * @author: wanghang
 * @date: 2015-7-11 下午2:57:59
 */
public class ShowQuestionView extends BaseLinearLayout {

    public ShowQuestionView(Context context) {
        super(context);
    }

    public ShowQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowQuestionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private TextView questionIndexTextView;

    private TextView titleTextView;
    private ImageView titleImageView;

    private TextView aTipTextView;
    private TextView aTextView;
    private ImageView aImageView;
    private ImageView aCheckBox;

    private TextView bTipTextView;
    private TextView bTextView;
    private ImageView bImageView;
    private ImageView bCheckBox;

    private TextView cTipTextView;
    private TextView cTextView;
    private ImageView cImageView;
    private ImageView cCheckBox;

    private TextView dTipTextView;
    private TextView dTextView;
    private ImageView dImageView;
    private ImageView dCheckBox;

    private Question mQuestion;
    private int mType; // 两种类型，对于老师——checkbox显示正确的选项；对于学生——checkbox显示选择的选项，蓝色的A-D表示正确的选项

    public static final int TYPE_STUDENT = 1;
    public static final int TYPE_TEACHER = 2;

    @Override
    public void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.comm_view_question_show, this);
        initView();
    }

    @Override
    public void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_question_index);

        titleTextView = (TextView) findViewById(R.id.textview_title);
        titleImageView = (ImageView) findViewById(R.id.imageview_title);
        titleImageView.setOnClickListener(this);

        aTipTextView = (TextView) findViewById(R.id.textview_tipa);
        aTextView = (TextView) findViewById(R.id.textview_A);
        aImageView = (ImageView) findViewById(R.id.imageview_A);
        aImageView.setOnClickListener(this);
        aCheckBox = (ImageView) findViewById(R.id.checkBox_A);

        bTipTextView = (TextView) findViewById(R.id.textview_tipb);
        bTextView = (TextView) findViewById(R.id.textview_B);
        bImageView = (ImageView) findViewById(R.id.imageview_B);
        bImageView.setOnClickListener(this);
        bCheckBox = (ImageView) findViewById(R.id.checkBox_B);

        cTipTextView = (TextView) findViewById(R.id.textview_tipc);
        cTextView = (TextView) findViewById(R.id.textview_C);
        cImageView = (ImageView) findViewById(R.id.imageview_C);
        cImageView.setOnClickListener(this);
        cCheckBox = (ImageView) findViewById(R.id.checkBox_C);

        dTipTextView = (TextView) findViewById(R.id.textview_tipd);
        dTextView = (TextView) findViewById(R.id.textview_D);
        dImageView = (ImageView) findViewById(R.id.imageview_D);
        dImageView.setOnClickListener(this);
        dCheckBox = (ImageView) findViewById(R.id.checkBox_D);
    }

    @Override
    public void release() {
        mContext = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_title:
                showBitmapDiaog(R.string.comm_bitmap_dialog_title_title, mQuestion.getTitle()
                        .getBitmap());
                break;
            case R.id.imageview_A:
                showBitmapDiaog(R.string.comm_bitmap_dialog_title_A, mQuestion.getChoiceA()
                        .getBitmap());
                break;
            case R.id.imageview_B:
                showBitmapDiaog(R.string.comm_bitmap_dialog_title_B, mQuestion.getChoiceB()
                        .getBitmap());
                break;
            case R.id.imageview_C:
                showBitmapDiaog(R.string.comm_bitmap_dialog_title_C, mQuestion.getChoiceC()
                        .getBitmap());
                break;
            case R.id.imageview_D:
                showBitmapDiaog(R.string.comm_bitmap_dialog_title_D, mQuestion.getChoiceD()
                        .getBitmap());
                break;
            default:
                break;
        }
    }

    /**
     * 点击图片，显示大图；
     */
    private void showBitmapDiaog(int titleResId, Bitmap bitmap) {
        DialogUtil.showBitmapDialog(mContext, titleResId, bitmap, null, -1);
    }

    /**
     * 设置题目显示view的类型，在老师和学生模块不一样；
     */
    public void setType(int type) {
        mType = type;
    }

    /**
     * 显示题目
     */
    public void showQuestion(int questionIndex, Question question) {
        mQuestion = question;
        questionIndexTextView.setText(String.format(
                mContext.getString(R.string.tch_question_index), questionIndex + 1));

        showQuestionItem(null, titleTextView, titleImageView, null, mQuestion.getTitle());
        showQuestionItem(aTipTextView, aTextView, aImageView, aCheckBox, mQuestion.getChoiceA());
        showQuestionItem(bTipTextView, bTextView, bImageView, bCheckBox, mQuestion.getChoiceB());
        showQuestionItem(cTipTextView, cTextView, cImageView, cCheckBox, mQuestion.getChoiceC());
        showQuestionItem(dTipTextView, dTextView, dImageView, dCheckBox, mQuestion.getChoiceD());
    }

    private void showQuestionItem(TextView tipTextView, TextView textView, ImageView imageView,
            ImageView checkIcon, QuestionItem item) {
        if (item.isText()) {
            textView.setText(item.getText());
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        } else {
            textView.setText("");
            textView.setVisibility(View.GONE);
            // TODO 使用UIL网络图片加载框架
            ImageLoader.getInstance().displayImage(item.getBitmapUrl(), imageView,
                    ImageLoaderUtil.getOptions());

            new LoadBitmapAsyncTask(imageView).execute(item);
            imageView.setVisibility(View.VISIBLE);
        }
        if (null != checkIcon) {
            switch (mType) {
                case TYPE_STUDENT:
                    // 选择框显示学生的选择
                    if (item.isSelected()) {
                        checkIcon.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
                    } else {
                        checkIcon
                                .setBackgroundResource(R.drawable.ic_check_box_outline_blank_grey600_18dp);
                    }
                    // A-D提示显示正确的答案
                    if (item.isRight()) {
                        tipTextView.setTextColor(getResources().getColor(R.color.stu_primary));
                    } else {
                        tipTextView.setTextColor(getResources().getColor(R.color.black_700));
                    }
                    break;
                case TYPE_TEACHER:
                    // 选择框显示正确的答案
                    if (item.isRight()) {
                        checkIcon.setBackgroundResource(R.drawable.ic_check_box_grey600_18dp);
                    } else {
                        checkIcon
                                .setBackgroundResource(R.drawable.ic_check_box_outline_blank_grey600_18dp);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 异步加载图片；
     */
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
