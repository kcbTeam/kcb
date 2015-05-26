package com.kcb.teacher.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.QuestionItem;
import com.kcb.teacher.model.test.Test;
import com.kcb.teacher.util.EditTestDialog;
import com.kcb.teacher.util.EditTestDialog.DialogSureListener;
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
    private TextView questionNumTextView;

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

    private ButtonFlat lastButton;
    private ButtonFlat nextButton;
    private ButtonFlat addButton;
    private ButtonFlat deleteButton;

    //
    private Test mTest;
    private int mCurrentQuestionIndex;

    // when click last/next, show a mTempQuestion, used for compare it is changed or not;
    private Question mTempQuestion;

    // user want to change what, question title or choice;
    private final int CLICK_TAG_TITLE = 1;
    private final int CLICK_TAG_A = 2;
    private final int CLICK_TAG_B = 3;
    private final int CLICK_TAG_C = 4;
    private final int CLICK_TAG_D = 5;
    private int mClickTag = CLICK_TAG_TITLE; // long click

    // save temp camera photo
    private String path;

    public final static String COURSE_TEST_KEY = "current_course_key";

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
        questionNumTextView = (TextView) findViewById(R.id.textview_question_num);

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
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (ACTION_ADD.equals(action)) {
            mTest = (Test) intent.getSerializableExtra(DATA_TEST);
        } else if (ACTION_EDIT.equals(action)) {
            // TODO get data from db & show them
            String testId = intent.getStringExtra(DATA_TEST_ID);
        }

        testNameTextView.setText("测试：" + mTest.getName());
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
            case R.id.imageview_delete_title:
                titleEditText.setText("");
                titleEditText.setFocusable(true);
                titleEditText.setFocusableInTouchMode(true);
                titleEditText.setBackgroundResource(R.drawable.stu_checkin_textview);
                deleteTitleImageView.setVisibility(View.INVISIBLE);
                getCurrentQuestion().getTitle().setBitmap(null);
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
        if (mCurrentQuestionIndex == mTest.getQuestionNum() - 1) {
            String hintString = "";
            if (!mTest.isCompleted()) {
                hintString = hintString + String.valueOf(1 + mTest.getUnCompleteIndex()) + "、";
                ToastUtil.toast(String.format(
                        getResources().getString(R.string.format_edit_empty_hint),
                        hintString.substring(0, hintString.length() - 1)));
                return;
            }
            Intent intent = new Intent(this, SubmitTestActivity.class);
            intent.putExtra(DATA_TEST, mTest);
            startActivity(intent);
            finish();
        } else {
            saveQuestion();
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
        // TODO show add question num
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
        if (mTest.getQuestionNum() > 1) {
            DialogUtil.showNormalDialog(this, R.string.dialog_title_delete, R.string.delete_msg,
                    R.string.sure, new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mTest.getQuestionNum() > 1) {
                                int originQuestionNum = mTest.getQuestionNum();
                                mTest.removeQuestion(mCurrentQuestionIndex);
                                mCurrentQuestionIndex =
                                        mCurrentQuestionIndex == originQuestionNum - 1 ? mTest
                                                .getQuestionNum() - 1 : mCurrentQuestionIndex;
                                showQuestion();
                                ToastUtil.toast(R.string.delete_success);
                            }
                        }
                    }, R.string.cancel, null);
        }
    }

    private void makeEditDialog(String text, String title) {
        EditTestDialog dialog = new EditTestDialog(this, text);
        dialog.show();
        dialog.setTitle(title);
        DialogSureListener sureListener = new DialogSureListener() {

            @Override
            public void onClickSure(String text) {
                switch (mClickTag) {
                    case CLICK_TAG_TITLE:
                        titleEditText.setText(text);
                        getCurrentQuestion().getTitle().setText(text);
                        break;
                    case CLICK_TAG_A:
                        choiceAEditText.setText(text);
                        getCurrentQuestion().getChoiceA().setText(text);
                        break;
                    case CLICK_TAG_B:
                        choiceBEditText.setText(text);
                        getCurrentQuestion().getChoiceB().setText(text);
                        break;
                    case CLICK_TAG_C:
                        choiceCEditText.setText(text);
                        getCurrentQuestion().getChoiceC().setText(text);
                        break;
                    case CLICK_TAG_D:
                        choiceDEditText.setText(text);
                        getCurrentQuestion().getChoiceD().setText(text);
                        break;
                    default:
                        break;
                }
            }
        };
        dialog.setSureButton(getResources().getString(R.string.save), sureListener);
        dialog.setCancelButton(getResources().getString(R.string.cancel), null);
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
                    Intent intent = new Intent(this, CutPictureActivity.class);
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
                            titleEditText.setText(" ");
                            titleEditText.setFocusable(false);
                            titleEditText.setBackground(new BitmapDrawable(bitmap));
                            deleteTitleImageView.setVisibility(View.VISIBLE);
                            getCurrentQuestion().getTitle().setBitmap(bitmap);
                            break;
                        case CLICK_TAG_A:
                            choiceAEditText.setText("");
                            choiceBEditText.setFocusable(false);
                            choiceAEditText.setBackground(new BitmapDrawable(bitmap));
                            getCurrentQuestion().getChoiceA().setBitmap(bitmap);
                            break;
                        case CLICK_TAG_B:
                            choiceCEditText.setText("");
                            choiceDEditText.setFocusable(false);
                            choiceBEditText.setBackground(new BitmapDrawable(bitmap));
                            getCurrentQuestion().getChoiceB().setBitmap(bitmap);
                            break;
                        case CLICK_TAG_C:
                            choiceCEditText.setText("");
                            choiceCEditText.setFocusable(false);
                            choiceCEditText.setBackground(new BitmapDrawable(bitmap));
                            getCurrentQuestion().getChoiceC().setBitmap(bitmap);
                            break;
                        case CLICK_TAG_D:
                            choiceCEditText.setText("");
                            choiceDEditText.setFocusable(false);
                            choiceDEditText.setBackground(new BitmapDrawable(bitmap));
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

        showQuestionItem(titleEditText, question.getTitle());
        showQuestionItem(choiceAEditText, question.getChoiceA());
        showQuestionItem(choiceBEditText, question.getChoiceB());
        showQuestionItem(choiceCEditText, question.getChoiceC());
        showQuestionItem(choiceDEditText, question.getChoiceD());

        boolean[] correctId = question.getCorrectId();
        checkBoxA.setChecked(correctId[0]);
        checkBoxB.setChecked(correctId[1]);
        checkBoxC.setChecked(correctId[2]);
        checkBoxD.setChecked(correctId[3]);
    }

    private void showQuestionNum() {
        questionNumTextView.setText(String.format(
                getResources().getString(R.string.format_question_num), mCurrentQuestionIndex + 1,
                mTest.getQuestionNum()));
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
        String questionTitle = titleEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(questionTitle)) {
            getCurrentQuestion().getTitle().setText(questionTitle);
        }
        String choiceA = choiceAEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(choiceA)) {
            getCurrentQuestion().getChoiceA().setText(choiceA);
        }
        String choiceB = choiceBEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(choiceB)) {
            getCurrentQuestion().getChoiceB().setText(choiceB);
        }
        String choiceC = choiceCEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(choiceC)) {
            getCurrentQuestion().getChoiceC().setText(choiceC);
        }
        String choiceD = choiceDEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(choiceD)) {
            getCurrentQuestion().getChoiceD().setText(choiceD);
        }
        getCurrentQuestion().setCorrectId(
                new boolean[] {checkBoxA.isCheck(), checkBoxB.isCheck(), checkBoxC.isCheck(),
                        checkBoxD.isCheck()});
    }

    @SuppressWarnings("deprecation")
    private void showQuestionItem(EditText view, QuestionItem item) {
        view.setText("");
        view.setBackgroundResource(R.drawable.stu_checkin_textview);
        if (item.isText()) {
            view.setText(item.getText());
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        } else {
            Bitmap bitmap = item.getBitmap();
            if (null != bitmap) {
                view.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
            view.setFocusable(false);
        }
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
        OnClickListener sureListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        };
        DialogUtil.showNormalDialog(this, R.string.leave, R.string.sureLeave, R.string.sure,
                sureListener, R.string.cancel, null);
    }

    /**
     * for add a new Test or Edit Test;
     */
    private static final String ACTION_ADD = "addTest";
    private static final String ACTION_EDIT = "editTest";

    private static final String DATA_TEST = "test";

    public static void startAddNewTest(Context context, Test test) {
        Intent intent = new Intent(context, EditTestActivity.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(DATA_TEST, test);
        context.startActivity(intent);
    }

    private static final String DATA_TEST_ID = "testId";

    public static void startEditTest(Context context, String testId) {
        Intent intent = new Intent(context, EditTestActivity.class);
        intent.setAction(ACTION_EDIT);
        intent.putExtra(DATA_TEST_ID, testId);
        context.startActivity(intent);
    }
}
