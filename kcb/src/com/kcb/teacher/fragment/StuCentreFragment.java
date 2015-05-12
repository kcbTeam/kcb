package com.kcb.teacher.fragment;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kcb.common.base.BaseFragment;
import com.kcb.library.view.FloatingEditText;
import com.kcb.teacher.adapter.ListAdapterStudent;
import com.kcb.teacher.model.StudentInfo;
import com.kcb.teacher.util.NameUtils;
import com.kcbTeam.R;

/**
 * 
 * @className: StuCentreFragment
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:24:10
 */
public class StuCentreFragment extends BaseFragment implements OnItemClickListener, TextWatcher {

    @SuppressWarnings("unused")
    private static final String TAG = "StuCentreFragment";
    private ListView mStudentList;
    private ListAdapterStudent mAdapter;
    private List<StudentInfo> mList;
    private List<StudentInfo> mTempList;

    private FloatingEditText searchEditText;
    private View mCurrentView = null;

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
        mAdapter = new ListAdapterStudent(getActivity(), mTempList);
        mStudentList.setAdapter(mAdapter);
        mStudentList.setOnItemClickListener(this);

        searchEditText = (FloatingEditText) view.findViewById(R.id.edittext_search);
        searchEditText.addTextChangedListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Intent intent = new Intent(getActivity(), StuDetailsActivity.class);
        // intent.putExtra(CURRENT_STU_KEY, mList.get(position));
        // startActivity(intent);
        if(null == mCurrentView){
            mCurrentView = view;
        } else {
            mCurrentView.setBackgroundColor(getResources().getColor(R.color.white));
        }
        view.setBackgroundColor(getResources().getColor(R.color.list_blue_background));
        mCurrentView = view;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {
        mList = new ArrayList<StudentInfo>();
        mList.clear();
        mList.add(new StudentInfo("令狐", "1004210254", 10, 3, 10, 20));
        mList.add(new StudentInfo("杨过", "1004210256", 10, 4, 5, 8));
        mList.add(new StudentInfo("萧远山", "1004210257", 10, 4, 5, 8));
        mList.add(new StudentInfo("慕容博", "1004210258", 10, 4, 5, 8));
        mList.add(new StudentInfo("扫地僧", "1004210259", 10, 4, 5, 8));
        mList.add(new StudentInfo("向问天", "1004210245", 10, 4, 5, 8));
        mList.add(new StudentInfo("任我行", "1004210221", 10, 4, 5, 8));
        mList.add(new StudentInfo("萧峰", "1004210232", 10, 4, 5, 8));
        mList.add(new StudentInfo("东方", "1004210214", 10, 4, 5, 8));
        mList.add(new StudentInfo("查良镛", "1004210228", 10, 4, 5, 8));
        mTempList = new ArrayList<StudentInfo>();
        mTempList.addAll(mList);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        mTempList.clear();
        mTempList.addAll(mList);
        String searchContent = searchEditText.getText().toString();
        for (int i = 0; i < mTempList.size(); i++) {
            String name = mTempList.get(i).getStudentName();
            try {
                if (NameUtils.isMatch(name, searchContent)) {
                    continue;
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            if (!name.startsWith(searchContent)) {
                mTempList.remove(i);
                i--;
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
