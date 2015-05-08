package com.kcb.teacher.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.PaperButton;
import com.kcb.teacher.fragment.EditTestFragment;
import com.kcb.teacher.model.QuestionObj;
import com.kcbTeam.R;

/**
 * 
 * @className: EditTestActivity
 * @description:
 * @author: ZQJ
 * @date: 2015年5月8日 下午6:03:59
 */
public class EditTestActivity extends BaseFragmentActivity {

    private PaperButton lastButton;
    private PaperButton nextButton;
    private ImageButton deleteButton;


    private FragmentManager mFragmentManager;
    private EditTestFragment mCurrentFragment;

    private int mCurrentPosition;
    private List<EditTestFragment> mFragmentList;
    private List<QuestionObj> mQuestionList;

    private int MaxFragmentNum = 3; // set max question num

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_edittest);
        initData();
        initView();
    }

    // Initialisation of the view
    @Override
    protected void initView() {
        lastButton = (PaperButton) findViewById(R.id.pagerbutton_last);
        nextButton = (PaperButton) findViewById(R.id.pagerbutton_next);
        deleteButton = (ImageButton) findViewById(R.id.delete_button);

        lastButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        lastButton.setTextColor(getResources().getColor(R.color.gray));
        nextButton.setTextColor(getResources().getColor(R.color.black));

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_content, mCurrentFragment);
        transaction.commit();

    }

    @Override
    protected void initData() {
        mFragmentList = new ArrayList<EditTestFragment>();
        mQuestionList = new ArrayList<QuestionObj>();
        mFragmentList.clear();
        mQuestionList.clear();
        mCurrentPosition = 1;
        mCurrentFragment = new EditTestFragment(mCurrentPosition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_button:
                /*
                 * make sure mCurrentPositon at the right range
                 */
                if (mCurrentPosition > MaxFragmentNum) {
                    mCurrentPosition = MaxFragmentNum;
                }
                if (mCurrentPosition == 1) {
                    /*
                     * if the lists only contain one or less record clear list,otherwise delete
                     * first record and reset the questionNum of remain fragments
                     */
                    if (mFragmentList.size() <= 1) {
                        mFragmentList.clear();
                        mQuestionList.clear();
                    } else {
                        mFragmentList.remove(0);
                        mQuestionList.remove(0);
                        for (int i = mCurrentPosition - 1; i < mFragmentList.size(); i++) {
                            mFragmentList.get(i).questionNumReduce();
                        }
                    }
                } else {
                    // remove current fragment
                    if (mCurrentPosition <= mFragmentList.size()) {
                        mFragmentList.remove(mCurrentPosition - 1);
                        mQuestionList.remove(mCurrentPosition - 1);
                    }
                    // reset questionNum of the rest fragments
                    for (int i = mCurrentPosition - 1; i < mFragmentList.size(); i++) {
                        mFragmentList.get(i).questionNumReduce();
                    }
                    mCurrentPosition--;
                }
                refreshButtonBar(); // change the "下一题" to "已完成" if the condition is satisfied.
                refreshFragment(); // display the fragment that need to be seen next.
                break;
            case R.id.pagerbutton_last:
                if (mCurrentPosition > MaxFragmentNum) {
                    mCurrentPosition = MaxFragmentNum;
                }
                final QuestionObj temp = mCurrentFragment.getQuestionObj();
                if (mCurrentPosition > 1 &&  null != temp) { // lastButton , need not action while the current
                                            // position = 1.
                    if (mCurrentPosition > mFragmentList.size()) {
                        mQuestionList.add(temp);
                        mFragmentList.add(mCurrentFragment);
                        ToastUtil.toast("第" + mCurrentPosition + "题已保存");
                        if (mCurrentPosition == 1) {
                            lastButton.setTextColor(getResources().getColor(R.color.gray));
                        }
                        mCurrentPosition--;
                        refreshFragment();
                    } else {
                        /*
                         * compare if the current is modified.haven't modified display the last
                         * fragment
                         */
                        if (mQuestionList.get(mCurrentPosition - 1).equal(temp)) {
                            mCurrentPosition--;
                            if (mCurrentPosition == 1) {
                                lastButton.setTextColor(getResources().getColor(R.color.gray));
                            }
                            refreshFragment();
                        } else {
                            /*
                             * have modified ,make dialog for user to save or give up the
                             * modifications
                             */
                            OnClickListener sure = new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    mQuestionList.set(mCurrentPosition - 1, temp);
                                    mFragmentList.set(mCurrentPosition - 1, mCurrentFragment);
                                    ToastUtil.toast("第" + mCurrentPosition + "题已保存");
                                    mCurrentPosition--;
                                    if (mCurrentPosition == 1) {
                                        lastButton.setTextColor(getResources().getColor(
                                                R.color.gray));
                                    }
                                    refreshFragment();
                                }
                            };
                            OnClickListener cancel = new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    
                                    if (mCurrentPosition == 1) {
                                        lastButton.setTextColor(getResources().getColor(
                                                R.color.gray));
                                    }
                                    mCurrentPosition--;
                                    refreshFragment();
                                }
                            };
                            DialogUtil.showDialog(this, "提示", "是否保存修改", "确定", sure, "取消", cancel);
                        }
                    }
                }
                refreshButtonBar();
                break;
            case R.id.pagerbutton_next:
                if (mCurrentPosition <= MaxFragmentNum) {
                    QuestionObj currentObj = mCurrentFragment.getQuestionObj();
                    if (null != currentObj) {
                        /*
                         * if the current fragment is correct ,save it and make a new fragment.while
                         * if mCurrentPosition > MaxFragment,go to complete situation
                         */
                        lastButton.setTextColor(getResources().getColor(R.color.black));
                        if (mCurrentPosition > mFragmentList.size()) {
                            mQuestionList.add(currentObj);
                            mFragmentList.add(mCurrentFragment);
                            ToastUtil.toast("第" + mCurrentPosition + "题已保存");
                        } else {
                            if (!mQuestionList.get(mCurrentPosition - 1).equal(currentObj)) {
                                mQuestionList.set(mCurrentPosition - 1, currentObj);
                                mFragmentList.set(mCurrentPosition - 1, mCurrentFragment);
                                ToastUtil.toast("第" + mCurrentPosition + "题已保存");
                            }
                        }
                        mCurrentPosition++;
                        refreshFragment();
                    }
                } else {
                    mCurrentPosition = MaxFragmentNum;
                    QuestionObj currentObj = mCurrentFragment.getQuestionObj();
                    if (!mQuestionList.get(mCurrentPosition - 1).equal(currentObj)) {
                        mQuestionList.set(mCurrentPosition - 1, currentObj);
                        mFragmentList.set(mCurrentPosition - 1, mCurrentFragment);
                        ToastUtil.toast("第" + mCurrentPosition + "题已保存");
                    }
                    // TODO add complete edit situation
                    ToastUtil.toast("建设中。。。");
                }
                refreshButtonBar();
                break;
            default:
                break;
        }

    }

    private void refreshButtonBar() {
        if (mCurrentPosition > MaxFragmentNum) {
            nextButton.setText("已完成！");
        } else {
            nextButton.setText("下一题");
        }
    }

    private void refreshFragment() {
        if (mCurrentPosition <= MaxFragmentNum) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            /*
             * if mCurrentPosition > mFragmentList.size() add a new fragment , otherwise get a
             * fragment form fragment list.
             */
            if (mCurrentPosition > mFragmentList.size()) {
                mCurrentFragment = new EditTestFragment(mCurrentPosition);
            } else {
                mCurrentFragment = mFragmentList.get(mCurrentPosition - 1);
                mCurrentFragment.setCheckId(mQuestionList.get(mCurrentPosition - 1).getCorrectId());
            }
            transaction.replace(R.id.fragment_content, mCurrentFragment);
            transaction.commit();
        }
    }
}
