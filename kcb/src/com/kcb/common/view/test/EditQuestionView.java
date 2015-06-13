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
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.util.FileUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.teacher.activity.test.CropPictureActivity;
import com.kcbTeam.R;

public class EditQuestionView extends LinearLayout implements OnClickListener, OnLongClickListener {

    public EditQuestionView(Context context) {
        super(context);
        init(context);
    }

    public EditQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditQuestionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private TextView questionIndexTextView;

    private EditText titleEditText;
    private ImageView titleImageView;
    private ImageView deleteTitleImageView;

    private EditText aEditText;
    private ImageView aImageView;
    private ImageView deleteAImageView;

    private EditText bEditText;
    private ImageView bImageView;
    private ImageView deleteBImageView;

    private EditText cEditText;
    private ImageView cImageView;
    private ImageView deleteCImageView;

    private EditText dEditText;
    private ImageView dImageView;
    private ImageView deleteDImageView;

    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private CheckBox checkBoxD;

    private Context mContext;
    private String mTestName;
    private int mIndex;
    private Question mQuestion;

    // tag title/A/B/C/D;
    private final int FLAG_TITLE = 0;
    private final int FLAG_A = 1;
    private final int FALG_B = 2;
    private final int FLAG_C = 3;
    private final int FLAG_D = 4;

    // long click to take photo;
    private int mLongClickTag = FLAG_TITLE;

    private final int EDIT_MODE_TEXT = 1;
    private final int EDIT_MODE_BITMAP = 2;

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.comm_view_question_edit, this);
        initView();
    }

    private void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_input_problem_tip);

        titleEditText = (EditText) findViewById(R.id.edittext_title);
        titleEditText.setOnLongClickListener(this);
        titleImageView = (ImageView) findViewById(R.id.imageview_title);
        deleteTitleImageView = (ImageView) findViewById(R.id.imageview_delete_title);
        deleteTitleImageView.setOnClickListener(this);

        aEditText = (EditText) findViewById(R.id.edittext_A);
        aEditText.setOnLongClickListener(this);
        aImageView = (ImageView) findViewById(R.id.imageview_A);
        aImageView.setOnLongClickListener(this);
        deleteAImageView = (ImageView) findViewById(R.id.imageview_delete_a);
        deleteAImageView.setOnClickListener(this);

        bEditText = (EditText) findViewById(R.id.edittext_B);
        bEditText.setOnLongClickListener(this);
        bImageView = (ImageView) findViewById(R.id.imageview_B);
        bImageView.setOnLongClickListener(this);
        deleteBImageView = (ImageView) findViewById(R.id.imageview_delete_b);
        deleteBImageView.setOnClickListener(this);

        cEditText = (EditText) findViewById(R.id.edittext_C);
        cEditText.setOnLongClickListener(this);
        cImageView = (ImageView) findViewById(R.id.imageview_C);
        cImageView.setOnLongClickListener(this);
        deleteCImageView = (ImageView) findViewById(R.id.imageview_delete_c);
        deleteCImageView.setOnClickListener(this);

        dEditText = (EditText) findViewById(R.id.edittext_D);
        dEditText.setOnLongClickListener(this);
        dImageView = (ImageView) findViewById(R.id.imageview_D);
        dImageView.setOnLongClickListener(this);
        deleteDImageView = (ImageView) findViewById(R.id.imageview_delete_d);
        deleteDImageView.setOnClickListener(this);

        checkBoxA = (CheckBox) findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) findViewById(R.id.checkBox_D);
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
        showQuestionItem(FALG_B, mQuestion.getChoiceB());
        showQuestionItem(FLAG_C, mQuestion.getChoiceC());
        showQuestionItem(FLAG_D, mQuestion.getChoiceD());

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
            case R.id.imageview_delete_title:
                setEditMode(FLAG_TITLE, EDIT_MODE_TEXT);
                mQuestion.getTitle().setText("");
                break;
            case R.id.imageview_delete_a:
                setEditMode(FLAG_A, EDIT_MODE_TEXT);
                mQuestion.getChoiceA().setText("");
                break;
            case R.id.imageview_delete_b:
                setEditMode(FALG_B, EDIT_MODE_TEXT);
                mQuestion.getChoiceB().setText("");
                break;
            case R.id.imageview_delete_c:
                setEditMode(FLAG_C, EDIT_MODE_TEXT);
                mQuestion.getChoiceC().setText("");
                break;
            case R.id.imageview_delete_d:
                setEditMode(FLAG_D, EDIT_MODE_TEXT);
                mQuestion.getChoiceD().setText("");
                break;
            default:
                break;
        }
    }

    private void setEditMode(int flag, int mode) {
        EditText editText = getEditTextByFlag(flag);
        ImageView imageView = getImageViewByFlag(flag);
        ImageView deleteIcon = getDeleteIconByTag(flag);
        switch (mode) {
            case EDIT_MODE_TEXT:
                editText.setText("");
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.setBackgroundResource(R.drawable.comm_rectangle);
                editText.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(null);
                imageView.setVisibility(View.GONE);
                deleteIcon.setVisibility(View.GONE);
                break;
            case EDIT_MODE_BITMAP:
                editText.setText(" ");
                editText.setFocusable(false);
                if (flag != FLAG_TITLE) { // 如果的题目，输入框不能够隐藏，因为图片的位置相对它一样；如果是选项，输入框需要隐藏，因为图片的大小是固定的，如果不隐藏，会看到输入框。
                    editText.setVisibility(View.INVISIBLE);
                }
                imageView.setVisibility(View.VISIBLE);
                deleteIcon.setVisibility(View.VISIBLE);
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
            case FALG_B:
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
            case FALG_B:
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

    private ImageView getDeleteIconByTag(int flag) {
        ImageView deleteIcon = null;
        switch (flag) {
            case FLAG_TITLE:
                deleteIcon = deleteTitleImageView;
                break;
            case FLAG_A:
                deleteIcon = deleteAImageView;
                break;
            case FALG_B:
                deleteIcon = deleteBImageView;
                break;
            case FLAG_C:
                deleteIcon = deleteCImageView;
                break;
            case FLAG_D:
                deleteIcon = deleteDImageView;
                break;
            default:
                break;
        }
        return deleteIcon;
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.edittext_title:
                mLongClickTag = FLAG_TITLE;
                break;
            case R.id.edittext_A:
            case R.id.imageview_A:
                mLongClickTag = FLAG_A;
                break;
            case R.id.edittext_B:
            case R.id.imageview_B:
                mLongClickTag = FALG_B;
                break;
            case R.id.edittext_C:
            case R.id.imageview_C:
                mLongClickTag = FLAG_C;
                break;
            case R.id.edittext_D:
            case R.id.imageview_D:
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
                    // 保持的图片path： /kcb/testname/题目index_选项Index.png
                    // 比如：/kcb/导数的意义/1_0.png，表示测试——导数的意义中，第1道题的题目的图片。
                    // 比如：/kcb/导数的意义/2_3.png，表示测试——导数的意义中，第2道题的C选项的图片。
                    final String bitmapPath =
                            FileUtil.getQuestionItemPath(mTestName, mIndex + 1, mLongClickTag);
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            FileUtil.saveBitmap(bitmapPath, bitmap);
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
                        case FALG_B:
                            setEditMode(FALG_B, EDIT_MODE_BITMAP);
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

    public void release() {
        mContext = null;
        mQuestion = null;
    }
}
