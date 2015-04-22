package com.kcb.teacher.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.util.StudentInfo;
import com.kcb.teacher.adapter.ListAdapterStudent;
import com.kcbTeam.R;

/**
 * 
 * @className: StudentCenterFragment
 * @description: student centre fragment
 * @author: ZQJ
 * @date: 2015��4��22�� ����4:25:13
 */
public class StudentCentreFragment extends BaseFragment {

    private ListView mStudentList;
    private ListAdapterStudent mAdapter;
    private List<StudentInfo> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_fragment_studentcentre, container, false);
        mStudentList = (ListView) view.findViewById(R.id.listview_student);
        mAdapter = new ListAdapterStudent(getActivity(), mList);
        mStudentList.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {
        mList = new ArrayList<StudentInfo>();
        mList.clear();
        mList.add(new StudentInfo("����", "ѧ��"));
        mList.add(new StudentInfo("zqj", "1004210254"));
        mList.add(new StudentInfo("zh", "1104210256"));
    }
}
