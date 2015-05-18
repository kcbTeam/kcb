package com.kcb.student.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
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
    ArrayList<HashMap<String, Object>> listItem;
    private String[] testTitle = {"微积分", "导数", "导数复习"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_testresult);

        initView();

    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.listview);
        MyAdapter mAdapter = new MyAdapter(this);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
        listView.setTextFilterEnabled(true);

        backButton = (ButtonFlat) findViewById(R.id.button_comeback);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEditText = (EditText) findViewById(R.id.search);
    }

    @Override
    protected void initData() {}

    private ArrayList<HashMap<String, Object>> getDate() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < testTitle.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("test", testTitle[i]);
            listItem.add(map);
        }
        return listItem;
    }



    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return getDate().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertview, ViewGroup parent) {

            ViewHolder holder;
            if (convertview == null) {
                convertview = mInflater.inflate(R.layout.stu_view_list, null);
                holder = new ViewHolder();
                holder.testname = (TextView) convertview.findViewById(R.id.testname);
                convertview.setTag(holder);
            } else {
                holder = (ViewHolder) convertview.getTag();
            }
            holder.testname.setText(getDate().get(position).get("test").toString());
            holder.testname.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return convertview;
        }
    }

    public final class ViewHolder {

        public TextView testname;
    }
}
