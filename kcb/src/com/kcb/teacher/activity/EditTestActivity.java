package com.kcb.teacher.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
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
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.teacher.database.test.TestDao;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.QuestionItem;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

/**
 * 
 * @className: EditTestActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 下午6:03:59
 */
public class EditTestActivity extends BaseActivity implements OnLongClickListener {

    private TextView testNameTextView;
    private ButtonFlat cancelButton;
    private ImageView cancelImageView;

    private TextView inputIndexTextView;

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

    private ButtonFlat lastButton;
    private ButtonFlat nextButton;
    private ButtonFlat addButton;
    private ButtonFlat deleteButton;

    // tag title/A/B/C/D;
    private final int FLAG_TITLE = 1;
    private final int FLAG_A = 2;
    private final int FALG_B = 3;
    private final int FLAG_C = 4;
    private final int FLAG_D = 5;

    // test and current question index;
    private Test mTest;
    private int mCurrentQuestionIndex;

    // when click last/next, show a mTempQuestion, used for compare it is changed or not;
    private Question mTempQuestion;

    // long click to take photo;
    private int mLongClickTag = FLAG_TITLE;

    // save temp camera photo;
    private String mBitmapPath;

    private String mAction;

    private TestDao mTestDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_edittest);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        testNameTextView = (TextView) findViewById(R.id.textview_title);
        cancelButton = (ButtonFlat) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);
        cancelImageView = (ImageView) findViewById(R.id.imageview_cancel);

        inputIndexTextView = (TextView) findViewById(R.id.textview_input_problem_tip);

        lastButton = (ButtonFlat) findViewById(R.id.button_last);
        lastButton.setOnClickListener(this);
        nextButton = (ButtonFlat) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        addButton = (ButtonFlat) findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
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
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mAction = intent.getAction();
        if (mAction.equals(ACTION_EDIT_TEST)) {
            cancelImageView.setImageResource(R.drawable.ic_delete_black_24dp);
        }
        mTest = (Test) intent.getSerializableExtra(DATA_TEST);
        testNameTextView.setText(mTest.getName() + "（共" + mTest.getQuestionNum() + "题）");
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
            case R.id.button_cancel:
                if (mAction.equals(ACTION_ADD_TEST)) {
                    cancelAddTest();
                } else {
                    deleteTest();
                }
                break;
            case R.id.imageview_delete_title:
                setEditMode(FLAG_TITLE, EDIT_MODE_TEXT);
                getCurrentQuestion().getTitle().setText("");
                break;
            case R.id.imageview_delete_a:
                setEditMode(FLAG_A, EDIT_MODE_TEXT);
                getCurrentQuestion().getChoiceA().setText("");
                break;
            case R.id.imageview_delete_b:
                setEditMode(FALG_B, EDIT_MODE_TEXT);
                getCurrentQuestion().getChoiceB().setText("");
                break;
            case R.id.imageview_delete_c:
                setEditMode(FLAG_C, EDIT_MODE_TEXT);
                getCurrentQuestion().getChoiceC().setText("");
                break;
            case R.id.imageview_delete_d:
                setEditMode(FLAG_D, EDIT_MODE_TEXT);
                getCurrentQuestion().getChoiceD().setText("");
                break;
            case R.id.button_last:
                lastQuestion();
                break;
            case R.id.button_next:
                nextQuesion();
                break;
            case R.id.button_add:
                addQuestion();
                break;
            case R.id.button_delete:
                deleteQuestion();
                break;
            default:
                break;
        }
    }

    private void lastQuestion() {
        if (mCurrentQuestionIndex > 0) {
            saveQuestion();
            if (!getCurrentQuestion().equal(mTempQuestion)) {
                ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                        1 + mCurrentQuestionIndex));
            }
            mCurrentQuestionIndex--;
            showQuestion();
        }
    }

    private void nextQuesion() {
        saveQuestion();
        if (mCurrentQuestionIndex == mTest.getQuestionNum() - 1) {
            if (!mTest.isCompleted()) {
                int unCompletedIndex = mTest.getUnCompleteIndex() + 1;
                ToastUtil.toast(String
                        .format(getResources().getString(R.string.format_edit_empty_hint),
                                unCompletedIndex));
            } else {
                // if add test, goto set time activity; if edit test, finish;
                if (ACTION_ADD_TEST.equals(mAction)) {
                    SetTestTimeActivity.start(EditTestActivity.this, mTest);
                    finish();
                } else {
                    saveEditTest();
                    finish();
                }
            }
        } else {
            if (!getCurrentQuestion().equal(mTempQuestion)) {
                ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                        1 + mCurrentQuestionIndex));
            }
            mCurrentQuestionIndex++;
            showQuestion();
        }
    }

    // four click functions: add ,delete ,next ,last.
    private void addQuestion() {
        if (mTest.getQuestionNum() == 9) {
            ToastUtil.toast("最多只能有9题");
            return;
        }
        final int questionNum = mTest.getQuestionNum() + 1;
        DialogUtil.showNormalDialog(this, R.string.dialog_title_add,
                String.format(getString(R.string.add_msg), questionNum), R.string.sure,
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ToastUtil.toast("第" + questionNum + "题已添加");
                        mTest.addQuestion();
                        showQuestionNum();
                        showNextButton();
                    }
                }, R.string.cancel, null);
    }

    private void deleteQuestion() {
        DialogUtil.showNormalDialog(this, R.string.dialog_title_delete,
                String.format(getString(R.string.delete_question_tip), mCurrentQuestionIndex + 1),
                R.string.sure, new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (mTest.getQuestionNum() > 1) {
                            int originQuestionNum = mTest.getQuestionNum();
                            mTest.deleteQuestion(mCurrentQuestionIndex);
                            mCurrentQuestionIndex =
                                    mCurrentQuestionIndex == originQuestionNum - 1 ? mTest
                                            .getQuestionNum() - 1 : mCurrentQuestionIndex;
                            showQuestion();
                            ToastUtil.toast(R.string.delete_success);
                        } else {
                            finish();
                        }
                    }
                }, R.string.cancel, null);
    }

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
                            getCurrentQuestion().getTitle().setBitmap(bitmap);
                            break;
                        case FLAG_A:
                            setEditMode(FLAG_A, EDIT_MODE_BITMAP);
                            aEditText.setBackground(new BitmapDrawable(bitmap));
                            getCurrentQuestion().getChoiceA().setBitmap(bitmap);
                            break;
                        case FALG_B:
                            setEditMode(FALG_B, EDIT_MODE_BITMAP);
                            bEditText.setBackground(new BitmapDrawable(bitmap));
                            getCurrentQuestion().getChoiceB().setBitmap(bitmap);
                            break;
                        case FLAG_C:
                            setEditMode(FLAG_C, EDIT_MODE_BITMAP);
                            cEditText.setBackground(new BitmapDrawable(bitmap));
                            getCurrentQuestion().getChoiceC().setBitmap(bitmap);
                            break;
                        case FLAG_D:
                            setEditMode(FLAG_D, EDIT_MODE_BITMAP);
                            dEditText.setBackground(new BitmapDrawable(bitmap));
                            getCurrentQuestion().getChoiceD().setBitmap(bitmap);
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
        mTempQuestion = Question.clone(getCurrentQuestion());

        showQuestionNum();
        showNextButton();

        Question question = getCurrentQuestion();

        showQuestionItem(FLAG_TITLE, question.getTitle());
        showQuestionItem(FLAG_A, question.getChoiceA());
        showQuestionItem(FALG_B, question.getChoiceB());
        showQuestionItem(FLAG_C, question.getChoiceC());
        showQuestionItem(FLAG_D, question.getChoiceD());

        checkBoxA.setChecked(question.getChoiceA().isRight());
        checkBoxB.setChecked(question.getChoiceB().isRight());
        checkBoxC.setChecked(question.getChoiceC().isRight());
        checkBoxD.setChecked(question.getChoiceD().isRight());
    }

    private void showQuestionNum() {
        testNameTextView.setText(String.format(getString(R.string.test_name_num), mTest.getName(),
                mTest.getQuestionNum()));
        inputIndexTextView.setText(String.format(getString(R.string.problem_hint),
                mCurrentQuestionIndex + 1));
    }

    private void showNextButton() {
        if (mCurrentQuestionIndex == mTest.getQuestionNum() - 1) {
            nextButton.setText(getResources().getString(R.string.complete));
            nextButton.setTextColor(getResources().getColor(R.color.blue));
        } else {
            nextButton.setText(getResources().getString(R.string.next_item));
            nextButton.setTextColor(getResources().getColor(R.color.black_700));
        }
    }

    private void saveQuestion() {
        QuestionItem titleItem = getCurrentQuestion().getTitle();
        QuestionItem aItem = getCurrentQuestion().getChoiceA();
        QuestionItem bItem = getCurrentQuestion().getChoiceB();
        QuestionItem cItem = getCurrentQuestion().getChoiceC();
        QuestionItem dItem = getCurrentQuestion().getChoiceD();

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

    private Question getCurrentQuestion() {
        return mTest.getQuestion(mCurrentQuestionIndex);
    }

    /**
     * 
     * @title: onBackPressed
     * @description: backpressed response
     * @author: ZQJ
     * @date: 2015年5月19日 下午9:21:02
     */
    @Override
    public void onBackPressed() {
        if (mAction.equals(ACTION_ADD_TEST)) {
            cancelAddTest();
        } else {
            saveEditTest();
        }
    }

    private void cancelAddTest() {
        OnClickListener sureListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        };
        DialogUtil.showNormalDialog(this, R.string.leave, R.string.sureLeave, R.string.sure,
                sureListener, R.string.cancel, null);
    }

    private void saveEditTest() {
        ToastUtil.toast("已保存");
        try {
            mTestDao = new TestDao(this);
            mTestDao.add(mTest);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mTestDao.close();
        finish();
    }

    private void deleteTest() {
        OnClickListener sureListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtil.toast("已删除");
                try {
                    mTestDao = new TestDao(EditTestActivity.this);
                    mTestDao.deleteTestByName(mTest.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                mTestDao.close();
                finish();
            }
        };
        DialogUtil.showNormalDialog(this, R.string.dialog_title_delete,
                "确定删除测试——" + mTest.getName() + "？", R.string.sure, sureListener, R.string.cancel,
                null);
    }

    /**
     * for add a new Test or Edit Test;
     */
    private static final String ACTION_ADD_TEST = "action_addTest";
    private static final String ACTION_EDIT_TEST = "action_editTest";

    private static final String DATA_TEST = "data_test";

    public static void startAddNewTest(Context context, Test test) {
        Intent intent = new Intent(context, EditTestActivity.class);
        intent.setAction(ACTION_ADD_TEST);
        intent.putExtra(DATA_TEST, test);
        context.startActivity(intent);
    }

    public static void startEditTest(Context context, Test test) {
        Intent intent = new Intent(context, EditTestActivity.class);
        intent.setAction(ACTION_EDIT_TEST);
        intent.putExtra(DATA_TEST, test);
        context.startActivity(intent);
    }
}
