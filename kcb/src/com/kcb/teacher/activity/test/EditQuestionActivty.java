package com.kcb.teacher.activity.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.DelayClickListener;
import com.kcb.common.model.test.Question;
import com.kcb.common.model.test.QuestionItem;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcbTeam.R;

/**
 * 
 * @className: ModifyQuestionActivty
 * @description:
 * @author: ZQJ
 * @date: 2015年5月27日 下午8:24:10
 */
public class EditQuestionActivty extends BaseActivity implements OnLongClickListener {

    private TextView questionNumTextView;
    private ButtonFlat backButton;
    private ButtonFlat deleteButton;

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

    private PaperButton finishButton;

    // tag title/A/B/C/D;
    private final int FLAG_TITLE = 1;
    private final int FLAG_A = 2;
    private final int FALG_B = 3;
    private final int FLAG_C = 4;
    private final int FLAG_D = 5;

    // test and current question index;
    public static Question sQuestion;
    private int mIndex;

    // long click to take photo;
    private int mLongClickTag = FLAG_TITLE;

    // save temp camera photo;
    private String mBitmapPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_editquestion);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        questionNumTextView = (TextView) findViewById(R.id.textview_title);
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        deleteButton = (ButtonFlat) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(this);

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

        finishButton = (PaperButton) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mIndex = intent.getIntExtra(DATA_INDEX, 0);
        questionNumTextView.setText("第" + (mIndex + 1) + "题");
        showQuestion();
    }

    /**
     * 
     * @title: onClick
     * @description: buttons and edittexts click listener
     * @author: ZQJ
     * @date: 2015年5月19日 下午9:19:20
     * @param v
     * 
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_delete:
                DialogUtil.showNormalDialog(this, R.string.dialog_title_delete,
                        R.string.delete_msg, R.string.tch_comm_sure, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putExtra(DATA_INDEX, mIndex);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            }
                        }, R.string.tch_comm_cancel, null);
                break;
            case R.id.imageview_delete_title:
                setEditMode(FLAG_TITLE, EDIT_MODE_TEXT);
                sQuestion.getTitle().setText("");
                break;
            case R.id.imageview_delete_a:
                setEditMode(FLAG_A, EDIT_MODE_TEXT);
                sQuestion.getChoiceA().setText("");
                break;
            case R.id.imageview_delete_b:
                setEditMode(FALG_B, EDIT_MODE_TEXT);
                sQuestion.getChoiceB().setText("");
                break;
            case R.id.imageview_delete_c:
                setEditMode(FLAG_C, EDIT_MODE_TEXT);
                sQuestion.getChoiceC().setText("");
                break;
            case R.id.imageview_delete_d:
                setEditMode(FLAG_D, EDIT_MODE_TEXT);
                sQuestion.getChoiceD().setText("");
                break;
            default:
                break;
        }
    }

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_finish:
                    saveQuestion();
                    Intent intent = new Intent();
                    intent.putExtra(DATA_INDEX, mIndex);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 
     * @title: onLongClick
     * @description: edittexts long click listener
     * @author: ZQJ
     * @date: 2015年5月19日 下午9:19:49
     * @param v
     * @return
     */
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
    private final int REQUEST_CUTPHOTO = 200;

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
                File picture = new File(mBitmapPath + "temp.jpg");
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
                    switch (mLongClickTag) {
                        case FLAG_TITLE:
                            setEditMode(FLAG_TITLE, EDIT_MODE_BITMAP);
                            titleEditText.setBackground(new BitmapDrawable(bitmap));
                            sQuestion.getTitle().setBitmap(bitmap);
                            break;
                        case FLAG_A:
                            setEditMode(FLAG_A, EDIT_MODE_BITMAP);
                            aEditText.setBackground(new BitmapDrawable(bitmap));
                            sQuestion.getChoiceA().setBitmap(bitmap);
                            break;
                        case FALG_B:
                            setEditMode(FALG_B, EDIT_MODE_BITMAP);
                            bEditText.setBackground(new BitmapDrawable(bitmap));
                            sQuestion.getChoiceB().setBitmap(bitmap);
                            break;
                        case FLAG_C:
                            setEditMode(FLAG_C, EDIT_MODE_BITMAP);
                            cEditText.setBackground(new BitmapDrawable(bitmap));
                            sQuestion.getChoiceC().setBitmap(bitmap);
                            break;
                        case FLAG_D:
                            setEditMode(FLAG_D, EDIT_MODE_BITMAP);
                            dEditText.setBackground(new BitmapDrawable(bitmap));
                            sQuestion.getChoiceD().setBitmap(bitmap);
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

    /**
     * 
     * @title: refreshInfo
     * @description: refresh contents
     * @author: ZQJ
     * @date: 2015年5月19日 下午9:30:36
     * @param question
     */
    private void showQuestion() {
        showQuestionItem(FLAG_TITLE, sQuestion.getTitle());
        showQuestionItem(FLAG_A, sQuestion.getChoiceA());
        showQuestionItem(FALG_B, sQuestion.getChoiceB());
        showQuestionItem(FLAG_C, sQuestion.getChoiceC());
        showQuestionItem(FLAG_D, sQuestion.getChoiceD());

        checkBoxA.setChecked(sQuestion.getChoiceA().isRight());
        checkBoxB.setChecked(sQuestion.getChoiceB().isRight());
        checkBoxC.setChecked(sQuestion.getChoiceC().isRight());
        checkBoxD.setChecked(sQuestion.getChoiceD().isRight());
    }

    private void saveQuestion() {
        QuestionItem titleItem = sQuestion.getTitle();
        QuestionItem aItem = sQuestion.getChoiceA();
        QuestionItem bItem = sQuestion.getChoiceB();
        QuestionItem cItem = sQuestion.getChoiceC();
        QuestionItem dItem = sQuestion.getChoiceD();

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

    private static final String ACTION_EDIT_QUESTION = "action_editQuestion";
    public static final String DATA_INDEX = "data_index";

    public static final int REQUEST_EDIT = 0;
    public static final int RESULT_DELETE = 1;

    public static void startForResult(Context context, int index, Question question) {
        Intent intent = new Intent(context, EditQuestionActivty.class);
        intent.setAction(ACTION_EDIT_QUESTION);
        intent.putExtra(DATA_INDEX, index);
        ((Activity) context).startActivityForResult(intent, REQUEST_EDIT);
        sQuestion = question;
    }
}
