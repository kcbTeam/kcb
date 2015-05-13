package com.kcb.teacher.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.base.BaseFragment;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.teacher.adapter.ListAdapterStudent;
import com.kcb.teacher.model.StudentInfo;
import com.kcb.teacher.util.CompareByCheckInRate;
import com.kcb.teacher.util.CompareByCorrectRate;
import com.kcb.teacher.util.CompareById;
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

    private final int INDEX_ID = 0;
    private final int INDEX_CHECKIN_RATE = 1;
    private final int INDEX_CORRECT_RATE = 2;

    private ListView mStudentList;
    private ListAdapterStudent mAdapter;
    private List<StudentInfo> mList;
    private List<StudentInfo> mTempList;

    private FloatingEditText searchEditText;
    private ButtonFlat sortButton;
    private TextView stuId;
    private TextView stuCheckInRate;
    private TextView stuCorrectRate;
    private View mCurrentView = null;
    private int sortSwitch = INDEX_ID;

    private CompareById idComparator;
    private CompareByCheckInRate checkInRateComparator;
    private CompareByCorrectRate correctRateComparator;
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

        sortButton = (ButtonFlat) view.findViewById(R.id.button_sort);
        sortButton.setOnClickListener(this);

        stuId = (TextView) view.findViewById(R.id.textview_stuid);
        stuCheckInRate = (TextView) view.findViewById(R.id.textview_stucheckinrate);
        stuCorrectRate = (TextView) view.findViewById(R.id.textview_correctrate);

        onClick(sortButton);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Intent intent = new Intent(getActivity(), StuDetailsActivity.class);
        // intent.putExtra(CURRENT_STU_KEY, mList.get(position));
        // startActivity(intent);
        if (null == mCurrentView) {
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
        mList.add(new StudentInfo("杨过", "1004210256", 10, 4, 5, 23));
        mList.add(new StudentInfo("萧远山", "1004210257", 10, 1, 5, 14));
        mList.add(new StudentInfo("慕容博", "1004210258", 10, 2, 5, 13));
        mList.add(new StudentInfo("扫地僧", "1004210259", 10, 6, 5, 14));
        mList.add(new StudentInfo("向问天", "1004210245", 10, 7, 5, 15));
        mList.add(new StudentInfo("任我行", "1004210221", 10, 5, 5, 14));
        mList.add(new StudentInfo("萧峰", "1004210232", 10, 8, 5, 15));
        mList.add(new StudentInfo("东方", "1004210214", 10, 9, 5, 16));
        mList.add(new StudentInfo("查良镛", "1004210228", 10, 10, 5, 13));
        mTempList = new ArrayList<StudentInfo>();
        mTempList.addAll(mList);
        idComparator = new CompareById();
        correctRateComparator = new CompareByCorrectRate();
        checkInRateComparator = new CompareByCheckInRate();
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

            mTempList.remove(i);
            i--;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sort:
                sortSwitch = sortSwitch % 3;
                switch (sortSwitch) {
                    case INDEX_ID:
                        Collections.sort(mTempList, idComparator);
                        setTextViewColor(INDEX_ID);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case INDEX_CHECKIN_RATE:
                        Collections.sort(mTempList, checkInRateComparator);
                        setTextViewColor(INDEX_CHECKIN_RATE);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case INDEX_CORRECT_RATE:
                        Collections.sort(mTempList, correctRateComparator);
                        setTextViewColor(INDEX_CORRECT_RATE);
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                sortSwitch++;
                break;
            default:
                break;
        }
    }

    private void setTextViewColor(int index) {
        Resources res = getResources();
        stuId.setTextColor(res.getColor(R.color.black));
        stuCheckInRate.setTextColor(res.getColor(R.color.black));
        stuCorrectRate.setTextColor(res.getColor(R.color.black));
        switch (index) {
            case INDEX_ID:
                stuId.setTextColor(res.getColor(R.color.blue));
                break;
            case INDEX_CHECKIN_RATE:
                stuCheckInRate.setTextColor(res.getColor(R.color.blue));
                break;
            case INDEX_CORRECT_RATE:
                stuCorrectRate.setTextColor(res.getColor(R.color.blue));
                break;
            default:
                break;
        }
    }
}
