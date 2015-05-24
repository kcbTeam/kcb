package com.kcb.teacher.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.teacher.model.CourseTest;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.QuestionItem;
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

    private TextView titleTextView;

    private ImageButton addButton;
    private ImageButton deleteButton;

    private TextView numHintTextView;

    private EditText questionEditText;

    private EditText optionAEditText;
    private EditText optionBEditText;
    private EditText optionCEditText;
    private EditText optionDEditText;

    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private CheckBox checkBoxD;

    private PaperButton lastButton;
    private PaperButton nextButton;

    private DialogSureListener mDialogSureListener;

    //
    private int mQuestionNum;

    private List<Question> mQuestionList;
    private int mQuestionIndex;

    private final int INDEX_QUESTION = 1;
    private final int INDEX_A = 2;
    private final int INDEX_B = 3;
    private final int INDEX_C = 4;
    private final int INDEX_D = 5;
    private int mEditIndex = INDEX_QUESTION; // click or long click

    public final static String COURSE_TEST_KEY = "current_course_key";
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_edittest);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        // TODO set test name;
        titleTextView = (TextView) findViewById(R.id.textview_title);

        addButton = (ImageButton) findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
        deleteButton = (ImageButton) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(this);

        lastButton = (PaperButton) findViewById(R.id.pagerbutton_last);
        lastButton.setOnClickListener(this);
        nextButton = (PaperButton) findViewById(R.id.pagerbutton_next);
        nextButton.setOnClickListener(this);

        numHintTextView = (TextView) findViewById(R.id.textview_question_num);
        numHintTextView.setText(String.format(getResources()
                .getString(R.string.format_question_num), mQuestionIndex + 1));

        questionEditText = (EditText) findViewById(R.id.edittext_question);
        questionEditText.setOnClickListener(this);
        questionEditText.setOnLongClickListener(this);

        optionAEditText = (EditText) findViewById(R.id.edittext_A);
        optionAEditText.setOnClickListener(this);
        optionAEditText.setOnLongClickListener(this);

        optionBEditText = (EditText) findViewById(R.id.edittext_B);
        optionBEditText.setOnClickListener(this);
        optionBEditText.setOnLongClickListener(this);

        optionCEditText = (EditText) findViewById(R.id.edittext_C);
        optionCEditText.setOnClickListener(this);
        optionCEditText.setOnLongClickListener(this);

        optionDEditText = (EditText) findViewById(R.id.edittext_D);
        optionDEditText.setOnClickListener(this);
        optionDEditText.setOnLongClickListener(this);

        checkBoxA = (CheckBox) findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) findViewById(R.id.checkBox_D);
    }

    @Override
    protected void initData() {
        getIntentData();

        mDialogSureListener = new DialogSureListener() {

            @Override
            public void onClickSure(String text) {
                switch (mEditIndex) {
                    case INDEX_QUESTION:
                        questionEditText.setText(text);
                        getCurrentQuestion().getTitle().setText(text);
                        break;
                    case INDEX_A:
                        optionAEditText.setText(text);
                        getCurrentQuestion().getChoiceA().setText(text);
                        break;
                    case INDEX_B:
                        optionBEditText.setText(text);
                        getCurrentQuestion().getChoiceB().setText(text);
                        break;
                    case INDEX_C:
                        optionCEditText.setText(text);
                        getCurrentQuestion().getChoiceC().setText(text);
                        break;
                    case INDEX_D:
                        optionDEditText.setText(text);
                        getCurrentQuestion().getChoiceD().setText(text);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private Question getCurrentQuestion() {
        return mQuestionList.get(mQuestionIndex);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (ACTION_ADD.equals(action)) {
            mQuestionList = new ArrayList<Question>();
            for (int i = 0; i < mQuestionNum; i++) {
                mQuestionList.add(getCurrentObj());
            }
        } else if (ACTION_EDIT.equals(action)) {
            // TODO get data from db & show them
            String testId = intent.getStringExtra(DATA_TEST_ID);
        }
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
            case R.id.button_add:
                DialogUtil.showNormalDialog(this, R.string.dialog_title_add, R.string.add_msg,
                        R.string.sure, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                AddQuestion();
                            }
                        }, R.string.cancel, null);
                break;
            case R.id.button_delete:
                deleteQuestion();
                break;
            case R.id.edittext_question:
                mEditIndex = INDEX_QUESTION;
                makeEditDialog(questionEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_question));
                break;
            case R.id.edittext_A:
                mEditIndex = INDEX_A;
                makeEditDialog(optionAEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_optionA));
                break;
            case R.id.edittext_B:
                mEditIndex = INDEX_B;
                makeEditDialog(optionBEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_optionB));
                break;
            case R.id.edittext_C:
                mEditIndex = INDEX_C;
                makeEditDialog(optionCEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_optionC));
                break;
            case R.id.edittext_D:
                mEditIndex = INDEX_D;
                makeEditDialog(optionDEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_optionD));
                break;
            case R.id.pagerbutton_last:
                LastQuestion();
                break;
            case R.id.pagerbutton_next:
                NextQuesion();
                break;
            default:
                break;
        }
    }

    // four click functions: add ,delete ,next ,last.
    private void AddQuestion() {
        mQuestionNum++;
        mQuestionList.add(new Question());
        if (!mQuestionList.get(mQuestionIndex).equal(getCurrentObj())) {
            mQuestionList.set(mQuestionIndex, getCurrentObj());
            ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                    1 + mQuestionIndex));
        }
        mQuestionIndex++;
        refreshInfo(mQuestionList.get(mQuestionIndex));
    }

    private void deleteQuestion() {
        if (mQuestionNum != 1) {
            DialogUtil.showNormalDialog(this, R.string.dialog_title_delete, R.string.delete_msg,
                    R.string.sure, new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            clickDelete();
                        }
                    }, R.string.cancel, null);
        } else {
            clickDelete();
        }
    }

    private void clickDelete() {
        if (mQuestionNum > 1) {
            mQuestionNum--;
            mQuestionList.remove(mQuestionIndex);
            if (mQuestionIndex == 0) {
                refreshInfo(mQuestionList.get(mQuestionIndex));
            } else {
                mQuestionIndex--;
                refreshInfo(mQuestionList.get(mQuestionIndex));
            }
            ToastUtil.toast(R.string.delete_success);
        } else {
            OnClickListener sureListener = new OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            };
            DialogUtil.showNormalDialog(this, R.string.leave, R.string.sureLeave, R.string.sure,
                    sureListener, R.string.cancel, null);
        }
    }

    private void LastQuestion() {
        if (mQuestionIndex != 0) {
            if (!getCurrentObj().equal(mQuestionList.get(mQuestionIndex))) {
                mQuestionList.set(mQuestionIndex, getCurrentObj());
                ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                        1 + mQuestionIndex));
            }
            mQuestionIndex--;
            refreshInfo(mQuestionList.get(mQuestionIndex));
        }
    }

    private void NextQuesion() {
        if (mQuestionIndex + 1 >= mQuestionNum) {
            if (!getCurrentObj().equal(mQuestionList.get(mQuestionIndex))) {
                mQuestionList.set(mQuestionIndex, getCurrentObj());
            }
            String hintString = "";
            for (int i = 0; i < mQuestionList.size(); i++) {
                if (!mQuestionList.get(i).isLegal()) {
                    hintString = hintString + String.valueOf(1 + i) + "、";
                }
            }
            if (hintString != "") {
                ToastUtil.toast(String.format(
                        getResources().getString(R.string.format_edit_empty_hint),
                        hintString.substring(0, hintString.length() - 1)));
                return;
            }
            completeEdit();
        } else {
            if (!getCurrentObj().equal(mQuestionList.get(mQuestionIndex))) {
                mQuestionList.set(mQuestionIndex, getCurrentObj());
                ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                        1 + mQuestionIndex));
            }
            mQuestionIndex++;
            refreshInfo(mQuestionList.get(mQuestionIndex));
        }
    }

    private void makeEditDialog(String text, String title) {
        EditTestDialog dialog = new EditTestDialog(this, text);
        dialog.show();
        dialog.setTitle(title);
        dialog.setSureButton(getResources().getString(R.string.save), mDialogSureListener);
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
            case R.id.edittext_question:
                mEditIndex = INDEX_QUESTION;
                longClick(questionEditText);
                break;
            case R.id.edittext_A:
                mEditIndex = INDEX_A;
                longClick(optionAEditText);
                break;
            case R.id.edittext_B:
                mEditIndex = INDEX_B;
                longClick(optionBEditText);
                break;
            case R.id.edittext_C:
                mEditIndex = INDEX_C;
                longClick(optionCEditText);
                break;
            case R.id.edittext_D:
                mEditIndex = INDEX_D;
                longClick(optionDEditText);
                break;
            default:
                break;
        }
        return true;
    }

    private void longClick(final EditText editText) {
        // TODO
        if (true) {
            takePhoto();
            return;
        } else {
            DialogUtil.showDeletePictureDialog(this, "选择", new OnClickListener() {

                @Override
                public void onClick(View v) {
                    takePhoto();
                }
            }, new OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View v) {
                    switch (editText.getId()) {
                        case R.id.edittext_question:
                            getCurrentQuestion().setContent(new QuestionItem());
                            break;
                        case R.id.edittext_A:
                            getCurrentQuestion().setOptionA(new QuestionItem());
                            break;
                        case R.id.edittext_B:
                            getCurrentQuestion().setOptionB(new QuestionItem());
                            break;
                        case R.id.edittext_C:
                            getCurrentQuestion().setOptionC(new QuestionItem());
                            break;
                        case R.id.edittext_D:
                            getCurrentQuestion().setOptionD(new QuestionItem());
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    // take photo and cut photo
    private final int REQUEST_TAKEPHOTO = 100;
    private final int REQUEXT_CUTPHOTO = 200;

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        path = Environment.getExternalStorageDirectory() + "/KCB/";
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
        if (resultCode == RESULT_CANCELED) {
            ToastUtil.toast("拍照取消了");
            return;
        }
        if (requestCode == REQUEST_TAKEPHOTO) {
            File picture = new File(path + "temp.jpg");
            try {
                cutPicture(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),
                        picture.getAbsolutePath(), null, null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEXT_CUTPHOTO) {
            if (resultCode == RESULT_OK) {
                Uri uri = (Uri) data.getParcelableExtra("CUTTED_PICTURE");
                try {
                    Bitmap tempMap = Media.getBitmap(getContentResolver(), uri);
                    switch (mEditIndex) {
                        case INDEX_QUESTION:
                            questionEditText.setBackground(new BitmapDrawable(tempMap));
                            getCurrentQuestion().getTitle().setBitmap(tempMap);
                            break;
                        case INDEX_A:
                            optionAEditText.setBackground(new BitmapDrawable(tempMap));
                            getCurrentQuestion().getChoiceA().setBitmap(tempMap);
                            break;
                        case INDEX_B:
                            optionBEditText.setBackground(new BitmapDrawable(tempMap));
                            getCurrentQuestion().getChoiceB().setBitmap(tempMap);
                            break;
                        case INDEX_C:
                            optionCEditText.setBackground(new BitmapDrawable(tempMap));
                            getCurrentQuestion().getChoiceC().setBitmap(tempMap);
                            break;
                        case INDEX_D:
                            optionDEditText.setBackground(new BitmapDrawable(tempMap));
                            getCurrentQuestion().getChoiceD().setBitmap(tempMap);
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

    public void cutPicture(Uri uri) {
        Intent intent = new Intent(this, CutPictureActivity.class);
        intent.putExtra("PICTURE", uri);
        startActivityForResult(intent, REQUEXT_CUTPHOTO);
    }

    /**
     * 
     * @title: getCurrentObj
     * @description: return the current ChoiceQuestion object, invoked when add,delete,next,last is
     *               clicked
     * @author: ZQJ
     * @date: 2015年5月19日 下午9:27:53
     * @return
     */
    private Question getCurrentObj() {
        QuestionItem question = getCurrentQuestion().getTitle();
        QuestionItem optionA = getCurrentQuestion().getChoiceA();
        QuestionItem optionB = getCurrentQuestion().getChoiceB();
        QuestionItem optionC = getCurrentQuestion().getChoiceC();
        QuestionItem optionD = getCurrentQuestion().getChoiceD();
        boolean[] correctOption =
                {checkBoxA.isCheck(), checkBoxB.isCheck(), checkBoxC.isCheck(), checkBoxD.isCheck()};
        return new Question(question, optionA, optionB, optionC, optionD, correctOption);
    }

    /**
     * 
     * @title: refreshInfo
     * @description: refresh contents
     * @author: ZQJ
     * @date: 2015年5月19日 下午9:30:36
     * @param showObj
     */
    private void refreshInfo(Question showObj) {
        showContent(questionEditText, showObj.getTitle());
        showContent(optionAEditText, showObj.getChoiceA());
        showContent(optionBEditText, showObj.getChoiceB());
        showContent(optionCEditText, showObj.getChoiceC());
        showContent(optionDEditText, showObj.getChoiceD());
        boolean[] correctId = showObj.getCorrectId();
        numHintTextView.setText(String.format(getResources()
                .getString(R.string.format_question_num), mQuestionIndex + 1));
        checkBoxA.setChecked(correctId[0]);
        checkBoxB.setChecked(correctId[1]);
        checkBoxC.setChecked(correctId[2]);
        checkBoxD.setChecked(correctId[3]);
        if (mQuestionIndex + 1 >= mQuestionNum) {
            nextButton.setText(getResources().getString(R.string.complete));
            addButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setText(getResources().getString(R.string.next_item));
            addButton.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void showContent(EditText view, QuestionItem content) {
        if (content.isText()) {
            view.setText(content.getText());
            if (view == questionEditText) {} else {}
        } else {
            view.setBackground(new BitmapDrawable(content.getBitmap()));
        }
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
     * 
     * @title: completeEdit
     * @description: goto next activity
     * @author: ZQJ
     * @date: 2015年5月19日 下午9:31:16
     */
    private void completeEdit() {
        changeDataSerializable(mQuestionList);
        CourseTest test = new CourseTest("TestName1", mQuestionList);
        Intent intent = new Intent(this, SubmitTestActivity.class);
        intent.putExtra(COURSE_TEST_KEY, test);
        startActivity(intent);
        finish();
    }

    /**
     * 
     * @title: changeDataSerializable
     * @description: change TextContents's Bitmap to Byte[]
     * @author: ZQJ
     * @date: 2015年5月20日 下午7:10:01
     * @param questionList
     */
    public static void changeDataSerializable(List<Question> questionList) {
        if (null == questionList) return;
        for (int i = 0; i < questionList.size(); i++) {
            if (!questionList.get(i).getTitle().isText()) {
                questionList.get(i).getTitle().changeBitmapToBytes();
            }

            if (!questionList.get(i).getChoiceA().isText()) {
                questionList.get(i).getChoiceA().changeBitmapToBytes();
            }

            if (!questionList.get(i).getChoiceB().isText()) {
                questionList.get(i).getChoiceB().changeBitmapToBytes();
            }

            if (!questionList.get(i).getChoiceC().isText()) {
                questionList.get(i).getChoiceC().changeBitmapToBytes();
            }

            if (!questionList.get(i).getChoiceD().isText()) {
                questionList.get(i).getChoiceD().changeBitmapToBytes();
            }
        }
    }

    /**
     * for add a new Test or Edit Test;
     */
    private static final String ACTION_ADD = "addTest";
    private static final String ACTION_EDIT = "editTest";

    public static void AddNewTest(Context context, String testId) {
        Intent intent = new Intent(context, EditTestActivity.class);
        intent.setAction(ACTION_ADD);
        context.startActivity(intent);
    }

    private static final String DATA_TEST_ID = "testId";

    public static void EditTest(Context context, String testId) {
        Intent intent = new Intent(context, EditTestActivity.class);
        intent.setAction(ACTION_EDIT);
        intent.putExtra(DATA_TEST_ID, testId);
        context.startActivity(intent);
    }
}
