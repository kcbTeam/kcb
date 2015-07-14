package com.kcb.common.view.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.base.BaseLinearLayout;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.util.BitmapUtil;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.common.view.dialog.MaterialBitmapDialog.OnActionListener;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.teacher.activity.test.CropPictureActivity;
import com.kcb.teacher.util.FileUtil;
import com.kcbTeam.R;

public class EditQuestionView extends BaseLinearLayout implements OnLongClickListener {

    public EditQuestionView(Context context) {
        super(context);
    }

    public EditQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditQuestionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private TextView questionIndexTextView;

    private EditText titleEditText;
    private ImageView titleImageView;

    private EditText aEditText;
    private ImageView aImageView;

    private EditText bEditText;
    private ImageView bImageView;

    private EditText cEditText;
    private ImageView cImageView;

    private EditText dEditText;
    private ImageView dImageView;

    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private CheckBox checkBoxD;

    private String mTestName;
    private int mIndex;
    private Question mQuestion;

    // tag title/A/B/C/D;
    private final int FLAG_TITLE = 0;
    private final int FLAG_A = 1;
    private final int FLAG_B = 2;
    private final int FLAG_C = 3;
    private final int FLAG_D = 4;

    // long click to take photo;
    private int mLongClickTag = FLAG_TITLE;

    private final int EDIT_MODE_TEXT = 1;
    private final int EDIT_MODE_BITMAP = 2;

    @Override
    public void init(Context context) {
        mContext = context;
        inflate(context, R.layout.comm_view_question_edit, this);
        initView();
    }

    @Override
    public void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_input_problem_tip);

        titleEditText = (EditText) findViewById(R.id.edittext_title);
        titleEditText.setOnLongClickListener(this);
        titleImageView = (ImageView) findViewById(R.id.imageview_title);
        titleImageView.setOnClickListener(this);

        aEditText = (EditText) findViewById(R.id.edittext_A);
        aEditText.setOnLongClickListener(this);
        aImageView = (ImageView) findViewById(R.id.imageview_A);
        aImageView.setOnClickListener(this);

        bEditText = (EditText) findViewById(R.id.edittext_B);
        bEditText.setOnLongClickListener(this);
        bImageView = (ImageView) findViewById(R.id.imageview_B);
        bImageView.setOnClickListener(this);

        cEditText = (EditText) findViewById(R.id.edittext_C);
        cEditText.setOnLongClickListener(this);
        cImageView = (ImageView) findViewById(R.id.imageview_C);
        cImageView.setOnClickListener(this);

        dEditText = (EditText) findViewById(R.id.edittext_D);
        dEditText.setOnLongClickListener(this);
        dImageView = (ImageView) findViewById(R.id.imageview_D);
        dImageView.setOnClickListener(this);

        checkBoxA = (CheckBox) findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) findViewById(R.id.checkBox_D);
    }

    @Override
    public void release() {
        mContext = null;
        mQuestion = null;
    }

    public void showQuestion(String testName, int index, Question question) {
        mTestName = testName;
        mIndex = index;
        mQuestion = question;

        // 显示这是第几题
        questionIndexTextView.setText(String.format(
                mContext.getResources().getString(R.string.tch_input_title), index + 1));

        showQuestionItem(FLAG_TITLE, mQuestion.getTitle());
        showQuestionItem(FLAG_A, mQuestion.getChoiceA());
        showQuestionItem(FLAG_B, mQuestion.getChoiceB());
        showQuestionItem(FLAG_C, mQuestion.getChoiceC());
        showQuestionItem(FLAG_D, mQuestion.getChoiceD());
        titleEditText.requestFocus();

        checkBoxA.setChecked(mQuestion.getChoiceA().isRight());
        checkBoxB.setChecked(mQuestion.getChoiceB().isRight());
        checkBoxC.setChecked(mQuestion.getChoiceC().isRight());
        checkBoxD.setChecked(mQuestion.getChoiceD().isRight());
    }

    private void showQuestionItem(int flag, QuestionItem item) {
        EditText editText = getEditTextByFlag(flag);
        ImageView imageView = getImageViewByFlag(flag);
        if (item.isText()) {
            setEditMode(flag, EDIT_MODE_TEXT);
            editText.setText(item.getText());
            editText.setSelection(item.getText().length());
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                setEditMode(flag, EDIT_MODE_BITMAP);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_title:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_title, mQuestion.getTitle(),
                        FLAG_TITLE);
                break;
            case R.id.imageview_A:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_A, mQuestion.getChoiceA(),
                        FLAG_A);
                break;
            case R.id.imageview_B:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_B, mQuestion.getChoiceB(),
                        FLAG_B);
                break;
            case R.id.imageview_C:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_C, mQuestion.getChoiceC(),
                        FLAG_C);
                break;
            case R.id.imageview_D:
                showBitmapDialog(R.string.comm_bitmap_dialog_title_D, mQuestion.getChoiceD(),
                        FLAG_D);
                break;
            default:
                break;
        }
    }

    private void setEditMode(int flag, int mode) {
        EditText editText = getEditTextByFlag(flag);
        ImageView imageView = getImageViewByFlag(flag);
        switch (mode) {
            case EDIT_MODE_TEXT:
                editText.setText("");
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.setBackgroundResource(R.drawable.comm_rectangle);
                editText.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(null);
                imageView.setVisibility(View.GONE);
                break;
            case EDIT_MODE_BITMAP:
                editText.setText(" ");
                editText.setFocusable(false);
                if (flag != FLAG_TITLE) { // 如果的题目，输入框不能够隐藏，因为图片的位置相对它一样；如果是选项，输入框需要隐藏，因为图片的大小是固定的，如果不隐藏，会看到输入框。
                    editText.setVisibility(View.INVISIBLE);
                }
                imageView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private EditText getEditTextByFlag(int flag) {
        EditText editText = null;
        switch (flag) {
            case FLAG_TITLE:
                editText = titleEditText;
                break;
            case FLAG_A:
                editText = aEditText;
                break;
            case FLAG_B:
                editText = bEditText;
                break;
            case FLAG_C:
                editText = cEditText;
                break;
            case FLAG_D:
                editText = dEditText;
                break;
            default:
                break;
        }
        return editText;
    }

    private ImageView getImageViewByFlag(int flag) {
        ImageView imageView = null;
        switch (flag) {
            case FLAG_TITLE:
                imageView = titleImageView;
                break;
            case FLAG_A:
                imageView = aImageView;
                break;
            case FLAG_B:
                imageView = bImageView;
                break;
            case FLAG_C:
                imageView = cImageView;
                break;
            case FLAG_D:
                imageView = dImageView;
                break;
            default:
                break;
        }
        return imageView;
    }

    /**
     * 单击图片显示放大的图片
     */
    private void showBitmapDialog(int titleResId, QuestionItem item, int flag) {
        // 先判断是不是图片
        if (!item.isText()) {
            OnActionListener listener = new OnActionListener() {

                @Override
                public void onDelete(final int flag) {

                    switch (flag) {
                        case FLAG_TITLE:
                            setEditMode(FLAG_TITLE, EDIT_MODE_TEXT);
                            mQuestion.getTitle().setText("");
                            break;
                        case FLAG_A:
                            setEditMode(FLAG_A, EDIT_MODE_TEXT);
                            mQuestion.getChoiceA().setText("");
                            break;
                        case FLAG_B:
                            setEditMode(FLAG_B, EDIT_MODE_TEXT);
                            mQuestion.getChoiceB().setText("");
                            break;
                        case FLAG_C:
                            setEditMode(FLAG_C, EDIT_MODE_TEXT);
                            mQuestion.getChoiceC().setText("");
                            break;
                        case FLAG_D:
                            setEditMode(FLAG_D, EDIT_MODE_TEXT);
                            mQuestion.getChoiceD().setText("");
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onReTakePhoto(int flag) {
                    mLongClickTag = flag;
                    takePhoto();
                }
            };
            DialogUtil.showBitmapDialog(mContext, titleResId, item.getBitmap(), listener, flag);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.edittext_title:
                mLongClickTag = FLAG_TITLE;
                break;
            case R.id.edittext_A:
                mLongClickTag = FLAG_A;
                break;
            case R.id.edittext_B:
                mLongClickTag = FLAG_B;
                break;
            case R.id.edittext_C:
                mLongClickTag = FLAG_C;
                break;
            case R.id.edittext_D:
                mLongClickTag = FLAG_D;
                break;
            default:
                break;
        }
        takePhoto();
        return true;
    }

    // take photo and cut photo
    private final int REQUEST_TAKEPHOTO = 100;

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(FileUtil.getTakePhotoPath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        if (intent.resolveActivity(mContext.getPackageManager()) == null) {
            ToastUtil.toast(R.string.tch_no_camera_app);
            return;
        }
        ((Activity) mContext).startActivityForResult(intent, REQUEST_TAKEPHOTO);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKEPHOTO) {
            if (resultCode != Activity.RESULT_CANCELED) {
                try {
                    CropPictureActivity.startForResult(mContext, FileUtil.getTakePhotoPath(),
                            CropPictureActivity.REQUEST_CROPPHOTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CropPictureActivity.REQUEST_CROPPHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri uri = (Uri) data.getParcelableExtra(CropPictureActivity.DATA_PATH);
                    final Bitmap bitmap = Media.getBitmap(mContext.getContentResolver(), uri);
                    new File(uri.toString()).delete();
                    // 保持的图片path： /kcb/testname/题目index_选项Index.png
                    // 比如：/kcb/导数的意义/1_0.png，表示测试——导数的意义中，第1道题的题目的图片。
                    // 比如：/kcb/导数的意义/2_3.png，表示测试——导数的意义中，第2道题的C选项的图片。
                    final String bitmapPath =
                            FileUtil.getQuestionItemPath(mTestName, mIndex + 1, mLongClickTag);
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            BitmapUtil.saveBitmap(bitmapPath, bitmap);
                        }
                    }).start();

                    switch (mLongClickTag) {
                        case FLAG_TITLE:
                            setEditMode(FLAG_TITLE, EDIT_MODE_BITMAP);
                            titleImageView.setImageBitmap(bitmap); // 显示图片，保存图片和图片路径
                            mQuestion.getTitle().setBitmap(bitmap);
                            mQuestion.getTitle().setBitmapPath(bitmapPath);
                            break;
                        case FLAG_A:
                            setEditMode(FLAG_A, EDIT_MODE_BITMAP);
                            aImageView.setImageBitmap(bitmap);
                            mQuestion.getChoiceA().setBitmap(bitmap);
                            mQuestion.getChoiceA().setBitmapPath(bitmapPath);
                            break;
                        case FLAG_B:
                            setEditMode(FLAG_B, EDIT_MODE_BITMAP);
                            bImageView.setImageBitmap(bitmap);
                            mQuestion.getChoiceB().setBitmap(bitmap);
                            mQuestion.getChoiceB().setBitmapPath(bitmapPath);
                            break;
                        case FLAG_C:
                            setEditMode(FLAG_C, EDIT_MODE_BITMAP);
                            cImageView.setImageBitmap(bitmap);
                            mQuestion.getChoiceC().setBitmap(bitmap);
                            mQuestion.getChoiceC().setBitmapPath(bitmapPath);
                            break;
                        case FLAG_D:
                            setEditMode(FLAG_D, EDIT_MODE_BITMAP);
                            dImageView.setImageBitmap(bitmap);
                            mQuestion.getChoiceD().setBitmap(bitmap);
                            mQuestion.getChoiceD().setBitmapPath(bitmapPath);
                            break;
                        default:
                            break;
                    }
                } catch (FileNotFoundException e) {} catch (IOException e) {}
            }
        }
    }

    public void saveQuestion() {
        QuestionItem titleItem = mQuestion.getTitle();
        QuestionItem aItem = mQuestion.getChoiceA();
        QuestionItem bItem = mQuestion.getChoiceB();
        QuestionItem cItem = mQuestion.getChoiceC();
        QuestionItem dItem = mQuestion.getChoiceD();

        String questionTitle = titleEditText.getText().toString().trim();
        if (titleItem.isText()) {
            titleItem.setText(questionTitle);
        }
        String choiceA = aEditText.getText().toString().trim();
        if (aItem.isText()) {
            aItem.setText(choiceA);
        }
        String choiceB = bEditText.getText().toString().trim();
        if (bItem.isText()) {
            bItem.setText(choiceB);
        }
        String choiceC = cEditText.getText().toString().trim();
        if (cItem.isText()) {
            cItem.setText(choiceC);
        }
        String choiceD = dEditText.getText().toString().trim();
        if (dItem.isText()) {
            dItem.setText(choiceD);
        }
        aItem.setIsRight(checkBoxA.isCheck());
        bItem.setIsRight(checkBoxB.isCheck());
        cItem.setIsRight(checkBoxC.isCheck());
        dItem.setIsRight(checkBoxD.isCheck());
    }
}
