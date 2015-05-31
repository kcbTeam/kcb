package com.kcb.student.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.ToastUtil;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.student.adapter.TestResultAdapter;
import com.kcb.student.fragment.TestFragment;
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
    private FloatingEditText mEditText;
    ArrayList<HashMap<String, Object>> listItem;
    private TestResultAdapter listAdapter;
    private String[] testTitle = {"微积分", "导数", "导数复习"};
    private int[] questionNum = {3, 4, 2};
    private String[] testTime = {"2015-5-1", "2015-5-16", "2015-5-30"};
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
        listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < testTitle.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("testname", testTitle[i]);
            map.put("questionnum", questionNum[i]);
            map.put("testtime", testTime[i]);
            listItem.add(map);
        }
        
        listAdapter = new TestResultAdapter(this,listItem);
        listView.setAdapter(listAdapter);
        listView.post(new Runnable() {
            
            @Override
            public void run() {
                int index=listView.getLastVisiblePosition()-listView.getFirstVisiblePosition();
                listView.getChildAt(index).setBackgroundColor(Color.GRAY);
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
                String test=(String) listItem.get(position).get("testname");
                if(test.equals(testTitle[testTitle.length-1])){
                    arg1.setBackgroundColor(Color.GRAY);
                    ToastUtil.toast(R.string.cannotlook);
                }else{
                    Intent intent = new Intent(TestResultActivity.this, LookTestResultActivity.class);
                    intent.putExtra("testTitle1", (String) listItem.get(position).get("testname"));
                    intent.putExtra("questionNum1", (String) listItem.get(position).get("question"));
                    intent.putExtra("questionInfo", TestFragment.string);
                    startActivity(intent);
                }
            }
        });

        mEditText = (FloatingEditText) findViewById(R.id.search);
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
        listItem = new ArrayList<HashMap<String, Object>>();
        listItem.clear();
        for (int i = 0; i < testTitle.length; i++) {
            if (testTitle[i].contains(string)) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("testname", testTitle[i]);
                map.put("questionnum", questionNum[i]);
                map.put("testtime", testTime[i]);
                listItem.add(map);
            }
        }
        listView.setAdapter(new TestResultAdapter(this,listItem)); 
    }
}
