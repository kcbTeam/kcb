package com.kcb.common.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
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
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.teacher.activity.test.CropPictureActivity;
import com.kcbTeam.R;

public class QuestionEditView extends LinearLayout implements OnClickListener, OnLongClickListener {

    public QuestionEditView(Context context) {
        super(context);
        init(context);
    }

    public QuestionEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuestionEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private TextView questionIndexTextView;

    private EditText titleEditText;
    private ImageView deleteTitleImageView;
    private EditText aEditText;
    private ImageView deleteAImageView;
    private EditText bEditText;
    private ImageView deleteBImageView;
    private EditText cEditText;
    private ImageView deleteCImageView;
    private EditText dEditText;
    private ImageView deleteDImageView;

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

    // long click to take photo;
    private int mLongClickTag = FLAG_TITLE;

    // save temp camera photo;
    private String mBitmapPath;

    private final int EDIT_MODE_TEXT = 1;
    private final int EDIT_MODE_BITMAP = 2;

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.comm_view_question_edit, this);
        initView();
    }

    private void initView() {
        questionIndexTextView = (TextView) findViewById(R.id.textview_input_problem_tip);

        titleEditText = (EditText) findViewById(R.id.edittext_question_title);
        titleEditText.setOnLongClickListener(this);
        deleteTitleImageView = (ImageView) findViewById(R.id.imageview_delete_title);
        deleteTitleImageView.setOnClickListener(this);

        aEditText = (EditText) findViewById(R.id.edittext_A);
        aEditText.setOnLongClickListener(this);
        deleteAImageView = (ImageView) findViewById(R.id.imageview_delete_a);
        deleteAImageView.setOnClickListener(this);

        bEditText = (EditText) findViewById(R.id.edittext_B);
        bEditText.setOnLongClickListener(this);
        deleteBImageView = (ImageView) findViewById(R.id.imageview_delete_b);
        deleteBImageView.setOnClickListener(this);

        cEditText = (EditText) findViewById(R.id.edittext_C);
        cEditText.setOnLongClickListener(this);
        deleteCImageView = (ImageView) findViewById(R.id.imageview_delete_c);
        deleteCImageView.setOnClickListener(this);

        dEditText = (EditText) findViewById(R.id.edittext_D);
        dEditText.setOnLongClickListener(this);
        deleteDImageView = (ImageView) findViewById(R.id.imageview_delete_d);
        deleteDImageView.setOnClickListener(this);

        checkBoxA = (CheckBox) findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) findViewById(R.id.checkBox_D);
    }

    public void showQuestion(int index, Question question) {
        mQuestion = question;

        showQuestionNum(index);

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

    private void showQuestionNum(int index) {
        questionIndexTextView.setText(String.format(
                mContext.getResources().getString(R.string.tch_input_title), index + 1));
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(int flag, QuestionItem item) {
        EditText editText = getEdittextByTag(flag);
        if (item.isText()) {
            setEditMode(flag, EDIT_MODE_TEXT);
            editText.setText(item.getText());
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                setEditMode(flag, EDIT_MODE_BITMAP);
                editText.setBackgroundDrawable(new BitmapDrawable(bitmap));
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
        EditText editText = getEdittextByTag(flag);
        ImageView deleteIcon = getDeleteIconByTag(flag);
        switch (mode) {
            case EDIT_MODE_TEXT:
                editText.setText("");
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.setBackgroundResource(R.drawable.stu_checkin_textview);
                deleteIcon.setVisibility(View.INVISIBLE);
                break;
            case EDIT_MODE_BITMAP:
                editText.setText(" ");
                editText.setFocusable(false);
                deleteIcon.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private EditText getEdittextByTag(int flag) {
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
            case R.id.edittext_question_title:
                mLongClickTag = FLAG_TITLE;
                break;
            case R.id.edittext_A:
                mLongClickTag = FLAG_A;
                break;
            case R.id.edittext_B:
                mLongClickTag = FALG_B;
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
        mBitmapPath = Environment.getExternalStorageDirectory() + "/kcb/";
        File file = new File(mBitmapPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(mBitmapPath + "temp.jpg");
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (intent.resolveActivity(mContext.getPackageManager()) == null) {
            ToastUtil.toast(R.string.tch_no_camera_app);
            return;
        }
        ((Activity) mContext).startActivityForResult(intent, REQUEST_TAKEPHOTO);
    }

    @SuppressWarnings("deprecation")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKEPHOTO) {
            if (resultCode != Activity.RESULT_CANCELED) {
                File picture = new File(mBitmapPath + "temp.jpg");
                try {
                    Uri uri =
                            Uri.parse(MediaStore.Images.Media.insertImage(
                                    mContext.getContentResolver(), picture.getAbsolutePath(), null,
                                    null));
                    CropPictureActivity.startForResult(mContext, uri,
                            CropPictureActivity.REQUEST_CROPPHOTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CropPictureActivity.REQUEST_CROPPHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = (Uri) data.getParcelableExtra(CropPictureActivity.DATA_PICTURE);
                try {
                    Bitmap bitmap = Media.getBitmap(mContext.getContentResolver(), uri);
                    switch (mLongClickTag) {
                        case FLAG_TITLE:
                            setEditMode(FLAG_TITLE, EDIT_MODE_BITMAP);
                            titleEditText.setBackgroundDrawable(new BitmapDrawable(bitmap));
                            mQuestion.getTitle().setBitmap(bitmap);
                            break;
                        case FLAG_A:
                            setEditMode(FLAG_A, EDIT_MODE_BITMAP);
                            aEditText.setBackgroundDrawable(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceA().setBitmap(bitmap);
                            break;
                        case FALG_B:
                            setEditMode(FALG_B, EDIT_MODE_BITMAP);
                            bEditText.setBackgroundDrawable(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceB().setBitmap(bitmap);
                            break;
                        case FLAG_C:
                            setEditMode(FLAG_C, EDIT_MODE_BITMAP);
                            cEditText.setBackgroundDrawable(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceC().setBitmap(bitmap);
                            break;
                        case FLAG_D:
                            setEditMode(FLAG_D, EDIT_MODE_BITMAP);
                            dEditText.setBackgroundDrawable(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceD().setBitmap(bitmap);
                            break;
                        default:
                            break;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
