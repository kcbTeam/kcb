package com.kcb.teacher.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kcb.common.base.BaseFragment;
import com.kcb.teacher.activity.StuDetailsActivity;
import com.kcb.teacher.adapter.ListAdapterStudent;
import com.kcb.teacher.model.StudentInfo;
import com.kcbTeam.R;

/**
 * 
 * @className: StuCentreFragment
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:24:10
 */
public class StuCentreFragment extends BaseFragment implements OnItemClickListener {

    private ListView mStudentList;
    private ListAdapterStudent mAdapter;
    private List<StudentInfo> mList;

    public static final String CURRENT_STU_KEY = "cunrrent_stu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tch_fragment_stucentre, container, false);
        mStudentList = (ListView) view.findViewById(R.id.listview_student);
        mAdapter = new ListAdapterStudent(getActivity(), mList);
        mStudentList.setAdapter(mAdapter);
        mStudentList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), StuDetailsActivity.class);
        intent.putExtra(CURRENT_STU_KEY, mList.get(position));
        startActivity(intent);
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {
        mList = new ArrayList<StudentInfo>();
        mList.clear();
        mList.add(new StudentInfo("zqj", "1004210254", 10, 3));
        mList.add(new StudentInfo("zh", "1104210256", 10, 4));
    }
}
