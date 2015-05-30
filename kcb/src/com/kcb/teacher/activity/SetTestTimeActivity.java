package com.kcb.teacher.activity;

import java.io.IOException;
import java.util.Date;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.application.KApplication;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.DialogUtil;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.slider.Slider;
import com.kcb.library.slider.Slider.OnValueChangedListener;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.SetTestTimeAdapter;
import com.kcb.teacher.adapter.SetTestTimeAdapter.EditQuestionListener;
import com.kcb.teacher.model.test.Question;
import com.kcb.teacher.model.test.Test;
import com.kcbTeam.R;

public class SetTestTimeActivity extends BaseActivity {

    private ButtonFlat finishButton;

    private TextView testTimeTextView;
    private Slider slider;

    private ListView listView;

    public static Test sTest;
    private EditQuestionListener mEditListener;
    private SetTestTimeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_settesttime);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        finishButton = (ButtonFlat) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(this);

        testTimeTextView = (TextView) findViewById(R.id.textview_testtime);

        slider = (Slider) findViewById(R.id.slider_testtime);
        slider.setValue(5);
        slider.setOnValueChangedListener(new OnValueChangedListener() {

            @Override
            public void onValueChanged(int value) {
                testTimeTextView.setText(String.format(getString(R.string.settime_hint),
                        sTest.getQuestionNum(), value));
            }
        });

        listView = (ListView) findViewById(R.id.listview_questions);
    }

    @Override
    protected void initData() {
        testTimeTextView.setText(String.format(getString(R.string.settime_hint),
                sTest.getQuestionNum(), 5));

        mEditListener = new EditQuestionListener() {

            @Override
            public void onEdit(int index, Question question) {
                EditQuestionActivty.startForResult(SetTestTimeActivity.this, index, question);
            }
        };
        mAdapter = new SetTestTimeAdapter(this, sTest, mEditListener);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditQuestionActivty.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                int index = data.getIntExtra(EditQuestionActivty.DATA_INDEX, 0);
                mAdapter.setItem(index, EditQuestionActivty.sQuestion);
                mAdapter.notifyDataSetChanged();
                ToastUtil.toast("已保存");
            } else if (resultCode == EditQuestionActivty.RESULT_DELETE) {
                int index = data.getIntExtra(EditQuestionActivty.DATA_INDEX, 0);
                mAdapter.deleteItem(index);
                mAdapter.notifyDataSetChanged();
                testTimeTextView.setText(String.format(getString(R.string.settime_hint),
                        mAdapter.getCount(), slider.getValue()));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == finishButton) {
            sTest.setDate(new Date());
            sTest.setTime(slider.getValue());
            // TODO
//            mTest.setId("2015-1-1");
            try {
                KApplication.mTestDao.add(sTest);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish();
        }
    }

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

    public static void start(Context context, Test test) {
        Intent intent = new Intent(context, SetTestTimeActivity.class);
        context.startActivity(intent);
        sTest = test;
    }
}
