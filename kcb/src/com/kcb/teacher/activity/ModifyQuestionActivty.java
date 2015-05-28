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
import android.text.TextUtils;
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
    private EditText choiceAEditText;
    private EditText choiceBEditText;
    private EditText choiceCEditText;
    private EditText choiceDEditText;

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
    private final int CLICK_TAG_TITLE = 1;
    private final int CLICK_TAG_A = 2;
    private final int CLICK_TAG_B = 3;
    private final int CLICK_TAG_C = 4;
    private final int CLICK_TAG_D = 5;
    private int mClickTag = CLICK_TAG_TITLE; // long click

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

        choiceAEditText = (EditText) findViewById(R.id.edittext_A);
        choiceAEditText.setOnLongClickListener(this);

        choiceBEditText = (EditText) findViewById(R.id.edittext_B);
        choiceBEditText.setOnLongClickListener(this);

        choiceCEditText = (EditText) findViewById(R.id.edittext_C);
        choiceCEditText.setOnLongClickListener(this);

        choiceDEditText = (EditText) findViewById(R.id.edittext_D);
        choiceDEditText.setOnLongClickListener(this);

        checkBoxA = (CheckBox) findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) findViewById(R.id.checkBox_D);

        showQuestion();
    }


    @Override
    protected void initData() {
        mQuestion =
                (Question) getIntent().getSerializableExtra(SetTestTimeActivity.MODIFY_QUESTION_KEY);
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

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.edittext_question_title:
                mClickTag = CLICK_TAG_TITLE;
                break;
            case R.id.edittext_A:
                mClickTag = CLICK_TAG_A;
                break;
            case R.id.edittext_B:
                mClickTag = CLICK_TAG_B;
                break;
            case R.id.edittext_C:
                mClickTag = CLICK_TAG_C;
                break;
            case R.id.edittext_D:
                mClickTag = CLICK_TAG_D;
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
                        case CLICK_TAG_TITLE:
                            titleEditText.setText("");
                            titleEditText.setFocusable(false);
                            titleEditText.setHint("");
                            titleEditText.setBackground(new BitmapDrawable(bitmap));
                            deleteTitleImageView.setVisibility(View.VISIBLE);
                            mQuestion.getTitle().setBitmap(bitmap);
                            break;
                        case CLICK_TAG_A:
                            choiceAEditText.setText("");
                            choiceAEditText.setFocusable(false);
                            choiceAEditText.setBackground(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceA().setBitmap(bitmap);
                            break;
                        case CLICK_TAG_B:
                            choiceBEditText.setText("");
                            choiceBEditText.setFocusable(false);
                            choiceBEditText.setBackground(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceB().setBitmap(bitmap);
                            break;
                        case CLICK_TAG_C:
                            choiceCEditText.setText("");
                            choiceCEditText.setFocusable(false);
                            choiceCEditText.setBackground(new BitmapDrawable(bitmap));
                            mQuestion.getChoiceC().setBitmap(bitmap);
                            break;
                        case CLICK_TAG_D:
                            choiceDEditText.setText("");
                            choiceDEditText.setFocusable(false);
                            choiceDEditText.setBackground(new BitmapDrawable(bitmap));
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

        showQuestionItem(titleEditText, question.getTitle());
        showQuestionItem(choiceAEditText, question.getChoiceA());
        showQuestionItem(choiceBEditText, question.getChoiceB());
        showQuestionItem(choiceCEditText, question.getChoiceC());
        showQuestionItem(choiceDEditText, question.getChoiceD());

        checkBoxA.setChecked(question.getChoiceA().getIsRight());
        checkBoxB.setChecked(question.getChoiceB().getIsRight());
        checkBoxC.setChecked(question.getChoiceC().getIsRight());
        checkBoxD.setChecked(question.getChoiceD().getIsRight());
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(EditText view, QuestionItem item) {
        view.setBackgroundResource(R.drawable.stu_checkin_textview);
        if (item.isText()) {
            if (view == titleEditText) {
                deleteTitleImageView.setVisibility(View.INVISIBLE);
            }
            view.setText(item.getText());
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                if (view == titleEditText) {
                    deleteTitleImageView.setVisibility(View.VISIBLE);
                    titleEditText.setHint("");
                }
                view.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
            view.setFocusable(false);
            view.setHint("");
        }
    }

    private void saveQuestion() {
        String questionTitle = titleEditText.getText().toString().trim();
        if (mQuestion.getTitle().isText()) {
            mQuestion.getTitle().setText(questionTitle);
        }
        String choiceA = choiceAEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(choiceA)) {
            mQuestion.getChoiceA().setText(choiceA);
        }
        String choiceB = choiceBEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(choiceB)) {
            mQuestion.getChoiceB().setText(choiceB);
        }
        String choiceC = choiceCEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(choiceC)) {
            mQuestion.getChoiceC().setText(choiceC);
        }
        String choiceD = choiceDEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(choiceD)) {
            mQuestion.getChoiceD().setText(choiceD);
        }
        mQuestion.getChoiceA().setIsRight(checkBoxA.isCheck());
        mQuestion.getChoiceB().setIsRight(checkBoxB.isCheck());
        mQuestion.getChoiceC().setIsRight(checkBoxC.isCheck());
        mQuestion.getChoiceD().setIsRight(checkBoxD.isCheck());
    }
}
