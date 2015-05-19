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
    private ChoiceQuestion mCurrentContent;
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
    private int mLongClickFlag;

    Drawable questionOriginalDrawable;
    Drawable optionOriginalDrawable;

    private int mPositionIndex = IndexOfQuestion;
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
        questionOriginalDrawable = questionEditText.getBackground();
        optionOriginalDrawable = optionAEditText.getBackground();
        mCurrentContent = new ChoiceQuestion();
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
                        mCurrentContent.setQuestion(new TextContent(text));
                        break;
                    case IndexOfA:
                        optionAEditText.setText(text);
                        mCurrentContent.setOptionA(new TextContent(text));
                        break;
                    case IndexOfB:
                        optionBEditText.setText(text);
                        mCurrentContent.setOptionB(new TextContent(text));
                        break;
                    case IndexOfC:
                        optionCEditText.setText(text);
                        mCurrentContent.setOptionC(new TextContent(text));
                        break;
                    case IndexOfD:
                        optionDEditText.setText(text);
                        mCurrentContent.setOptionD(new TextContent(text));
                        break;
                    default:
                        break;
                }
            }
        };
        refreshInfo(mQuestionList.get(mCurrentPosition));
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
            default:
                break;
        }
        if (v.getBackground() != questionOriginalDrawable
                && v.getBackground() != optionOriginalDrawable) return;
        switch (v.getId()) {
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

    // four click functions: add ,delete ,next ,last.
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
            if (!getCurrentObj().equal(mQuestionList.get(mCurrentPosition))) {
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

    private void makeEditDialog(String text, String title) {
        EditTestDialog dialog = new EditTestDialog(this, text);
        dialog.show();
        dialog.setTitle(title);
        dialog.setSureButton(getResources().getString(R.string.save), mSureClickListener);
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
     * 
     */
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.edittext_question:
                mLongClickFlag = IndexOfQuestion;
                break;
            case R.id.edittext_A:
                mLongClickFlag = IndexOfA;
                break;
            case R.id.edittext_B:
                mLongClickFlag = IndexOfB;
                break;
            case R.id.edittext_C:
                mLongClickFlag = IndexOfC;
                break;
            case R.id.edittext_D:
                mLongClickFlag = IndexOfD;
                break;
            default:
                break;
        }
        takePhoto();
        return true;
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
                    switch (mLongClickFlag) {
                        case IndexOfQuestion:
                            questionEditText.setBackground(new BitmapDrawable(tempMap));
                            mCurrentContent.setQuestion(new TextContent(tempMap));
                            break;
                        case IndexOfA:
                            optionAEditText.setBackground(new BitmapDrawable(tempMap));
                            mCurrentContent.setOptionA(new TextContent(tempMap));
                            break;
                        case IndexOfB:
                            optionBEditText.setBackground(new BitmapDrawable(tempMap));
                            mCurrentContent.setOptionB(new TextContent(tempMap));
                            break;
                        case IndexOfC:
                            optionCEditText.setBackground(new BitmapDrawable(tempMap));
                            mCurrentContent.setOptionC(new TextContent(tempMap));
                            break;
                        case IndexOfD:
                            optionDEditText.setBackground(new BitmapDrawable(tempMap));
                            mCurrentContent.setOptionD(new TextContent(tempMap));
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
    private ChoiceQuestion getCurrentObj() {
        TextContent question = mCurrentContent.getQuestion();
        TextContent optionA = mCurrentContent.getOptionA();
        TextContent optionB = mCurrentContent.getOptionB();
        TextContent optionC = mCurrentContent.getOptionC();
        TextContent optionD = mCurrentContent.getOptionD();
        boolean[] correctOption =
                {checkBoxA.isCheck(), checkBoxB.isCheck(), checkBoxC.isCheck(), checkBoxD.isCheck()};
        return new ChoiceQuestion(question, optionA, optionB, optionC, optionD, correctOption);
    }

    /**
     * 
     * @title: refreshInfo
     * @description: refresh contents
     * @author: ZQJ
     * @date: 2015年5月19日 下午9:30:36
     * @param showObj
     */
    private void refreshInfo(ChoiceQuestion showObj) {
        showContent(questionEditText, showObj.getQuestion());
        showContent(optionAEditText, showObj.getOptionA());
        showContent(optionBEditText, showObj.getOptionB());
        showContent(optionCEditText, showObj.getOptionC());
        showContent(optionDEditText, showObj.getOptionD());
        boolean[] correctId = showObj.getCorrectId();
        numHintTextView.setText(String.format(getResources()
                .getString(R.string.format_question_num), mCurrentPosition + 1));
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
        mCurrentContent = new ChoiceQuestion(showObj);
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void showContent(EditText view, TextContent content) {
        if (content.isString()) {
            view.setText(content.getContentString());
            if (view == questionEditText) {
                view.setBackground(questionOriginalDrawable);
            } else {
                view.setBackground(optionOriginalDrawable);
            }
        } else {
            view.setBackground(new BitmapDrawable(content.getContentBitmap()));
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
        CourseTest test = new CourseTest("TestName1", mQuestionList);
        Intent intent = new Intent(this, SubmitTestActivity.class);
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
