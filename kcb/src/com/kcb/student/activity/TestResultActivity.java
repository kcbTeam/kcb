package com.kcb.student.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.student.adapter.TestResultAdapter;
import com.kcbTeam.R;

/**
 * 
 * @className: TestResultActivity
 * @description:
 * @author: Ding
 * @date: 2015年5月7日 下午5:09:20
 */
public class TestResultActivity extends BaseActivity {

    private ListView listView;
    private ButtonFlat backButton;
    private EditText mEditText;
    ArrayList<String> listItem = new ArrayList<String>();
    private String[] testTitle = {"微积分", "导数", "导数复习"};
    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_testresult);

        initView();
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.listview);
        mEditText = (EditText) findViewById(R.id.search);
        listView.setAdapter(new TestResultAdapter(this, listItem));
        for (int i = 0; i < testTitle.length; i++) {
            listItem.add(testTitle[i]);
        }
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {

            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEditText.getText().toString() != null) {
                    test = mEditText.getText().toString();
                    search(test);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {}

    private void search(String string) {
        listItem.clear();
        for (int i = 0; i < testTitle.length; i++) {
            if (testTitle[i].contains(string)) {
                listItem.add(testTitle[i]);
            }
        }
        listView.setAdapter(new TestResultAdapter(this, listItem));
    }
}