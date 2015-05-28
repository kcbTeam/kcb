package com.kcb.teacher.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.QuestionItem;
import com.kcbTeam.R;

/**
 * 
 * @className: ModifyQuestionActivty
 * @description:
 * @author: ZQJ
 * @date: 2015年5月27日 下午8:24:10
 */
public class ModifyQuestionActivty extends BaseActivity implements OnLongClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = "ModifyQuestionActivty";

    private TextView questionNumHint;
    private TextView titleTextView;
    private ButtonFlat cancelButton;
    private ButtonFlat saveButton;

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

    private Question mTempQustion;
    private Question mQuestion;

    private String testName;
    private int questionId;

    private final String NumHint = "第%1$d题";

    // user want to change what, question title or choice;
    private final int FLAG_TITLE = 1;
    private final int FLAG_A = 2;
    private final int FLAG_B = 3;
    private final int FLAG_C = 4;
    private final int FLAG_D = 5;
    private int mClickTag = FLAG_TITLE; // long click

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_modify_question);
        initData();
        initView();
    }

    @Override
    protected void initView() {

        questionNumHint = (TextView) findViewById(R.id.textview_question_num);
        questionNumHint.setText(String.format(NumHint, 1 + questionId));

        titleTextView = (TextView) findViewById(R.id.textview_title);
        titleTextView.setText("测试：" + testName);

        cancelButton = (ButtonFlat) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);

        saveButton = (ButtonFlat) findViewById(R.id.button_save);
        saveButton.setOnClickListener(this);

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

        showQuestion();
    }


    @Override
    protected void initData() {
        mQuestion =
                (Question) getIntent()
                        .getSerializableExtra(SetTestTimeActivity.MODIFY_QUESTION_KEY);
        mTempQustion = Question.clone(mQuestion);

        testName = getIntent().getStringExtra("TEST_NAME");
        questionId = getIntent().getIntExtra("QUETION_ID", 0);
    }

    public static final int MODIFY_CACELED = 100;
    public static final int MODIFY_SAVED = 200;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageview_delete_title:
                deleteTitleImageView.setVisibility(View.INVISIBLE);
                mQuestion.getTitle().setText("");
                titleEditText.setClickable(true);
                titleEditText.setFocusable(true);
                titleEditText.setHint(R.string.edit_title_hint);
                showQuestion();
                break;
            case R.id.imageview_delete_a:
                setEditMode(FLAG_A, EDIT_MODE_TEXT);
                mQuestion.getChoiceA().setText("");
                break;
            case R.id.imageview_delete_b:
                setEditMode(FLAG_B, EDIT_MODE_TEXT);
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
            case R.id.button_cancel:
                setResult(MODIFY_CACELED, intent);
                finish();
                break;
            case R.id.button_save:
                saveQuestion();
                if (!mQuestion.isCompleted()) {
                    ToastUtil.toast(R.string.empty_hint);
                } else if (!mQuestion.equal(mTempQustion)) {
                    mQuestion.changeQuestionToSerializable();
                    intent.putExtra("MODIFIED", mQuestion);
                    setResult(MODIFY_SAVED, intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private final int EDIT_MODE_TEXT = 1;
    private final int EDIT_MODE_BITMAP = 2;

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

    private ImageView getDeleteIconByTag(int flag) {
        ImageView deleteIcon = null;
        switch (flag) {
            case FLAG_TITLE:
                deleteIcon = deleteTitleImageView;
                break;
            case FLAG_A:
                deleteIcon = deleteAImageView;
                break;
            case FLAG_B:
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

    private EditText getEdittextByTag(int flag) {
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

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.edittext_question_title:
                mClickTag = FLAG_TITLE;
                break;
            case R.id.edittext_A:
                mClickTag = FLAG_A;
                break;
            case R.id.edittext_B:
                mClickTag = FLAG_B;
                break;
            case R.id.edittext_C:
                mClickTag = FLAG_C;
                break;
            case R.id.edittext_D:
                mClickTag = FLAG_D;
                break;
            default:
                break;
        }
        takePhoto();
        return true;
    }

    // take photo and cut photo
    private final int REQUEST_TAKEPHOTO = 100;
    private final int REQUEST_CUTPHOTO = 200;

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        path = Environment.getExternalStorageDirectory() + "/kcb/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + "temp.jpg");
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (intent.resolveActivity(getPackageManager()) == null) {
            ToastUtil.toast("没有可以拍照的应用程序");
            return;
        }
        startActivityForResult(intent, REQUEST_TAKEPHOTO);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKEPHOTO) {
            if (resultCode != RESULT_CANCELED) {
                File picture = new File(path + "temp.jpg");
                try {
                    Uri uri =
                            Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),
                                    picture.getAbsolutePath(), null, null));
                    Intent intent = new Intent(this, CropPictureActivity.class);
                    intent.putExtra("PICTURE", uri);
                    startActivityForResult(intent, REQUEST_CUTPHOTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_CUTPHOTO) {
            if (resultCode == RESULT_OK) {
                Uri uri = (Uri) data.getParcelableExtra("CUTTED_PICTURE");
                try {
                    Bitmap bitmap = Media.getBitmap(getContentResolver(), uri);
                    switch (mClickTag) {
                        case FLAG_TITLE:
                            setEditMode(FLAG_TITLE, EDIT_MODE_BITMAP);
                            titleEditText.setBackground(new BitmapDrawable(bitmap));
                            mQuestion.getTitle().setBitmap(bitmap);
                            break;
                        case FLAG_A:
                            setEditMode(FLAG_A, EDIT_MODE_BITMAP);
                            aEditText.setBackground(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceA().setBitmap(bitmap);
                            break;
                        case FLAG_B:
                            setEditMode(FLAG_B, EDIT_MODE_BITMAP);
                            bEditText.setBackground(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceB().setBitmap(bitmap);
                            break;
                        case FLAG_C:
                            setEditMode(FLAG_C, EDIT_MODE_BITMAP);
                            cEditText.setBackground(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceC().setBitmap(bitmap);
                            break;
                        case FLAG_D:
                            setEditMode(FLAG_D, EDIT_MODE_BITMAP);
                            dEditText.setBackground(new BitmapDrawable(bitmap));
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

    private void showQuestion() {
        Question question = mQuestion;

        showQuestionItem(FLAG_TITLE, question.getTitle());
        showQuestionItem(FLAG_A, question.getChoiceA());
        showQuestionItem(FLAG_B, question.getChoiceB());
        showQuestionItem(FLAG_C, question.getChoiceC());
        showQuestionItem(FLAG_D, question.getChoiceD());

        checkBoxA.setChecked(question.getChoiceA().isRight());
        checkBoxB.setChecked(question.getChoiceB().isRight());
        checkBoxC.setChecked(question.getChoiceC().isRight());
        checkBoxD.setChecked(question.getChoiceD().isRight());
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

    private void saveQuestion() {
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
