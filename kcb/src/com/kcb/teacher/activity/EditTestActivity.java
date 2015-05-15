package com.kcb.teacher.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.kcb.teacher.model.ChoiceQuestion;
import com.kcb.teacher.model.CourseTest;
import com.kcb.teacher.model.TextContent;
import com.kcb.teacher.util.EditTestDialog;
import com.kcb.teacher.util.EditTestDialog.DialogBackListener;
import com.kcbTeam.R;

/**
 * 
 * @className: EditTestActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 下午6:03:59
 */
public class EditTestActivity extends BaseActivity implements OnLongClickListener {

    private PaperButton lastButton;
    private PaperButton nextButton;
    private ImageButton addButton;
    private ImageButton deleteButton;

    private int mCurrentPosition;
    private ChoiceQuestion mNextObj;
    private List<ChoiceQuestion> mQuestionList;

    private int MaxFragmentNum = 3;

    private TextView numHintTextView;

    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private CheckBox checkBoxD;

    private EditText questionEditText;
    private EditText optionAEditText;
    private EditText optionBEditText;
    private EditText optionCEditText;
    private EditText optionDEditText;

    private DialogBackListener mSureClickListener;

    private final int IndexOfQuestion = 1;
    private final int IndexOfA = 2;
    private final int IndexOfB = 3;
    private final int IndexOfC = 4;
    private final int IndexOfD = 5;

    private int mPositionIndex = IndexOfQuestion;
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
        lastButton = (PaperButton) findViewById(R.id.pagerbutton_last);
        lastButton.setOnClickListener(this);
        lastButton.setTextColor(getResources().getColor(R.color.gray));

        nextButton = (PaperButton) findViewById(R.id.pagerbutton_next);
        nextButton.setOnClickListener(this);
        nextButton.setTextColor(getResources().getColor(R.color.black));

        addButton = (ImageButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        deleteButton = (ImageButton) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);

        numHintTextView = (TextView) findViewById(R.id.textview_question_num);
        numHintTextView.setText(String.format(getResources()
                .getString(R.string.format_question_num), mCurrentPosition + 1));

        questionEditText = (EditText) findViewById(R.id.edittext_question);
        questionEditText.setOnClickListener(this);
        questionEditText.setOnLongClickListener(this);

        optionAEditText = (EditText) findViewById(R.id.edittext_A);
        optionAEditText.setOnClickListener(this);
        optionBEditText = (EditText) findViewById(R.id.edittext_B);
        optionBEditText.setOnClickListener(this);
        optionCEditText = (EditText) findViewById(R.id.edittext_C);
        optionCEditText.setOnClickListener(this);
        optionDEditText = (EditText) findViewById(R.id.edittext_D);
        optionDEditText.setOnClickListener(this);

        checkBoxA = (CheckBox) findViewById(R.id.checkBox_A);
        checkBoxB = (CheckBox) findViewById(R.id.checkBox_B);
        checkBoxC = (CheckBox) findViewById(R.id.checkBox_C);
        checkBoxD = (CheckBox) findViewById(R.id.checkBox_D);

        refreshInfo(null);
    }

    @Override
    protected void initData() {
        mQuestionList = new ArrayList<ChoiceQuestion>();
        for (int i = 0; i < MaxFragmentNum; i++) {
            mQuestionList.add(getCurrentObj());
        }
        mCurrentPosition = 0;
        mSureClickListener = new DialogBackListener() {

            @Override
            public void refreshActivity(String text) {
                switch (mPositionIndex) {
                    case IndexOfQuestion:
                        questionEditText.setText(text);
                        break;
                    case IndexOfA:
                        optionAEditText.setText(text);
                        break;
                    case IndexOfB:
                        optionBEditText.setText(text);
                        break;
                    case IndexOfC:
                        optionCEditText.setText(text);
                        break;
                    case IndexOfD:
                        optionDEditText.setText(text);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_button:
                if (MaxFragmentNum != 1) {
                    DialogUtil.showNormalDialog(this, R.string.dialog_title_delete,
                            R.string.delete_msg, R.string.sure, new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    clickDelete();
                                }
                            }, R.string.cancel, null);
                } else {
                    clickDelete();
                }

                break;
            case R.id.pagerbutton_last:
                clickLast();
                break;
            case R.id.pagerbutton_next:
                clickNext();
                break;
            case R.id.add_button:
                DialogUtil.showNormalDialog(this, R.string.dialog_title_add, R.string.add_msg,
                        R.string.sure, new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                clickAdd();
                            }
                        }, R.string.cancel, null);
                break;
            case R.id.edittext_question:
                mPositionIndex = IndexOfQuestion;
                makeEditDialog(questionEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_question));
                break;
            case R.id.edittext_A:
                mPositionIndex = IndexOfA;
                makeEditDialog(optionAEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_optionA));
                break;
            case R.id.edittext_B:
                mPositionIndex = IndexOfB;
                makeEditDialog(optionBEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_optionB));
                break;
            case R.id.edittext_C:
                mPositionIndex = IndexOfC;
                makeEditDialog(optionCEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_optionC));
                break;
            case R.id.edittext_D:
                mPositionIndex = IndexOfD;
                makeEditDialog(optionDEditText.getText().toString(),
                        getResources().getString(R.string.dialog_title_optionD));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        takePhoto();
        return false;
    }

    private void makeEditDialog(String text, String title) {
        EditTestDialog dialog = new EditTestDialog(this, text);
        dialog.show();
        dialog.setTitle(title);
        dialog.setSureButton(getResources().getString(R.string.save), mSureClickListener);
        dialog.setCancelButton(getResources().getString(R.string.cancel), null);
    }

    private final int REQUEST_TAKEPHOTO = 100;

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheDir());
        // intent.putExtra(MediaStore.EXTRA_OUTPUT,
        // Uri.fromFile(new File(getCacheDir().getPath() + File.separator + "temp.jpg")));
        if (intent.resolveActivity(getPackageManager()) == null) {
            ToastUtil.toast("没有可以拍照的应用程序");
            return;
        }
        startActivityForResult(intent, REQUEST_TAKEPHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            ToastUtil.toast("拍照取消了");
            return;
        }
        // after camera, we need to cut the picture
        if (requestCode == REQUEST_TAKEPHOTO) {
            // TODO get bitmap form data
            File picture = new File(getCacheDir().getPath() + File.separator + "temp.jpg");
            cutPicture(Uri.fromFile(picture));
        }
    }

    // TODO github开源代码
    public void cutPicture(Uri uri) {
        ToastUtil.toast("需要裁剪图片");
    }

    private void clickAdd() {
        MaxFragmentNum++;
        mQuestionList.add(new ChoiceQuestion());
        if (!mQuestionList.get(mCurrentPosition).equal(getCurrentObj())) {
            mQuestionList.set(mCurrentPosition, getCurrentObj());
            ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                    1 + mCurrentPosition));
        }
        mCurrentPosition++;
        refreshInfo(mQuestionList.get(mCurrentPosition));
    }

    private void clickNext() {
        if (mCurrentPosition + 1 >= MaxFragmentNum) {
            if (!getCurrentObj().equal(mQuestionList.get(mCurrentPosition))) {
                mQuestionList.set(mCurrentPosition, getCurrentObj());
            }
            for (int i = 0; i < mQuestionList.size(); i++) {
                if (!mQuestionList.get(i).isLegal()) {
                    ToastUtil.toast(String.format(
                            getResources().getString(R.string.format_edit_empty_hint), 1 + i));
                    return;
                }
            }
            completeEdit();
        } else {
            if (!getCurrentObj().equal(mQuestionList.get(mCurrentPosition))) {
                mQuestionList.set(mCurrentPosition, getCurrentObj());
                ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                        1 + mCurrentPosition));
            }
            mCurrentPosition++;
            mNextObj = mQuestionList.get(mCurrentPosition);
            refreshInfo(mNextObj);
        }
    }

    private void clickLast() {
        if (mCurrentPosition != 0) {
            if (!mQuestionList.get(mCurrentPosition).equal(getCurrentObj())) {
                mQuestionList.set(mCurrentPosition, getCurrentObj());
                ToastUtil.toast(String.format(getResources().getString(R.string.format_edit_save),
                        1 + mCurrentPosition));
            }
            mCurrentPosition--;
            mNextObj = mQuestionList.get(mCurrentPosition);
            refreshInfo(mNextObj);
        }
    }

    private void clickDelete() {
        if (MaxFragmentNum > 1) {
            MaxFragmentNum--;
            mQuestionList.remove(mCurrentPosition);
            if (mCurrentPosition == 0) {
                refreshInfo(mQuestionList.get(mCurrentPosition));
            } else {
                mCurrentPosition--;
                refreshInfo(mQuestionList.get(mCurrentPosition));
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

    private ChoiceQuestion getCurrentObj() {
        TextContent question = new TextContent(questionEditText.getText().toString());
        TextContent optionA = new TextContent(optionAEditText.getText().toString());
        TextContent optionB = new TextContent(optionBEditText.getText().toString());
        TextContent optionC = new TextContent(optionCEditText.getText().toString());
        TextContent optionD = new TextContent(optionDEditText.getText().toString());
        boolean[] correctOption =
                {checkBoxA.isCheck(), checkBoxB.isCheck(), checkBoxC.isCheck(), checkBoxD.isCheck()};
        return new ChoiceQuestion(question, optionA, optionB, optionC, optionD, correctOption);
    }

    private void refreshInfo(ChoiceQuestion currentObj) {
        String question = "";
        String optionA = "";
        String optionB = "";
        String optionC = "";
        String optionD = "";
        boolean[] correctId = {false, false, false, false};
        if (null != currentObj) {
            question = currentObj.getQuestion().getContentString();
            optionA = currentObj.getOptionA().getContentString();
            optionB = currentObj.getOptionB().getContentString();
            optionC = currentObj.getOptionC().getContentString();
            optionD = currentObj.getOptionD().getContentString();
            correctId = currentObj.getCorrectId();
        }
        numHintTextView.setText(String.format(getResources()
                .getString(R.string.format_question_num), mCurrentPosition + 1));
        questionEditText.setText(question);
        optionAEditText.setText(optionA);
        optionBEditText.setText(optionB);
        optionCEditText.setText(optionC);
        optionDEditText.setText(optionD);
        checkBoxA.setChecked(correctId[0]);
        checkBoxB.setChecked(correctId[1]);
        checkBoxC.setChecked(correctId[2]);
        checkBoxD.setChecked(correctId[3]);
        if (mCurrentPosition + 1 >= MaxFragmentNum) {
            nextButton.setText(getResources().getString(R.string.complete));
            addButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setText(getResources().getString(R.string.next_item));
            addButton.setVisibility(View.INVISIBLE);
        }
    }

    private void completeEdit() {
        CourseTest test = new CourseTest("TestName1", mQuestionList);
        Intent intent = new Intent(this, SubmitTest.class);
        intent.putExtra(COURSE_TEST_KEY, test);
        startActivity(intent);
        finish();
    }

    /**
     * for add a new Test or Edit Test;
     */
    private static final String TEST_ID = "testId";

    public static void startAddTest(Context context) {
        Intent intent = new Intent(context, EditTestFirstActivity.class);
        context.startActivity(intent);
    }

    public static void startEditTest(Context context, String testId) {
        Intent intent = new Intent(context, EditTestActivity.class);
        intent.putExtra(TEST_ID, testId);
        context.startActivity(intent);
    }
}
