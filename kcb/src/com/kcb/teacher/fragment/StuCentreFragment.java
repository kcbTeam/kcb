package com.kcb.teacher.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

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

    private ButtonFlat sortById;
    private ImageView idImage;
    private ButtonFlat sortByCheckInRate;
    private ImageView checkInRateImage;
    private ButtonFlat sortByCorrectRate;
    private ImageView correctRateImage;


    private FloatingEditText searchEditText;
    private View mCurrentView = null;

    private ListView mStudentList;
    private ListAdapterStudent mAdapter;
    private List<StudentInfo> mList;
    private List<StudentInfo> mTempList;
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

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tch_fragment_stucentre, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mStudentList = (ListView) view.findViewById(R.id.listview_student);
        mAdapter = new ListAdapterStudent(getActivity(), mTempList);
        mStudentList.setAdapter(mAdapter);
        mStudentList.setOnItemClickListener(this);

        searchEditText = (FloatingEditText) view.findViewById(R.id.edittext_search);
        searchEditText.addTextChangedListener(this);

        sortById = (ButtonFlat) view.findViewById(R.id.button_sort_info);
        sortById.setOnClickListener(this);
        idImage = (ImageView) view.findViewById(R.id.imageview_id);

        sortByCheckInRate = (ButtonFlat) view.findViewById(R.id.button_sort_checkinrate);
        sortByCheckInRate.setOnClickListener(this);
        checkInRateImage = (ImageView) view.findViewById(R.id.imageview_checkinrate);

        sortByCorrectRate = (ButtonFlat) view.findViewById(R.id.button_sort_correctrate);
        sortByCorrectRate.setOnClickListener(this);
        correctRateImage = (ImageView) view.findViewById(R.id.imageview_correctrate);

        onClick(sortById);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

        idComparator = new CompareById();
        correctRateComparator = new CompareByCorrectRate();
        checkInRateComparator = new CompareByCheckInRate();

        mList = new ArrayList<StudentInfo>();
        mList.clear();
        mList.add(new StudentInfo("令狐", "1004210254", 5, 15, 10, 20));
        mList.add(new StudentInfo("杨过", "1004210256", 6, 15, 5, 23));
        mList.add(new StudentInfo("萧远山", "1004210257", 7, 15, 5, 14));
        mList.add(new StudentInfo("慕容博", "1004210258", 2, 15, 5, 13));
        mList.add(new StudentInfo("扫地僧", "1004210259", 3, 15, 5, 14));
        mList.add(new StudentInfo("向问天", "1004210245", 14, 15, 5, 15));
        mList.add(new StudentInfo("任我行", "1004210221", 13, 15, 5, 14));
        mList.add(new StudentInfo("萧峰", "1004210232", 12, 15, 5, 15));
        mList.add(new StudentInfo("东方", "1004210214", 10, 15, 5, 16));
        mList.add(new StudentInfo("查良镛", "1004210228", 3, 15, 5, 13));
        Collections.sort(mList, idComparator);
        mTempList = new ArrayList<StudentInfo>();
        mTempList.addAll(mList);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (null != mCurrentView) {
            mCurrentView.setBackgroundColor(getResources().getColor(R.color.white));
        }
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
        if (!searchEditText.getText().toString().equals("")) {
            hideSortButton(true);
            mAdapter.notifyDataSetChanged();
        } else {
            hideSortButton(false);
            onClick(sortById);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sort_info:
                Collections.sort(mTempList, idComparator);
                setImageBackGround(INDEX_ID);
                break;
            case R.id.button_sort_checkinrate:
                Collections.sort(mTempList, checkInRateComparator);
                setImageBackGround(INDEX_CHECKIN_RATE);
                break;
            case R.id.button_sort_correctrate:
                Collections.sort(mTempList, correctRateComparator);
                setImageBackGround(INDEX_CORRECT_RATE);
                break;
            default:
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NewApi")
    private void setImageBackGround(int index) {
        Resources res = getResources();
        idImage.setBackground(res.getDrawable(R.drawable.ic_action_sort));
        checkInRateImage.setBackground(res.getDrawable(R.drawable.ic_action_sort));
        correctRateImage.setBackground(res.getDrawable(R.drawable.ic_action_sort));
        switch (index) {
            case INDEX_ID:
                idImage.setBackground(res.getDrawable(R.drawable.ic_action_sort_green));
                break;
            case INDEX_CHECKIN_RATE:
                checkInRateImage.setBackground(res.getDrawable(R.drawable.ic_action_sort_green));
                break;
            case INDEX_CORRECT_RATE:
                correctRateImage.setBackground(res.getDrawable(R.drawable.ic_action_sort_green));
                break;
            default:
                break;
        }
    }

    private void hideSortButton(boolean hide) {
        if (hide) {
            sortById.setVisibility(View.INVISIBLE);
            sortByCheckInRate.setVisibility(View.INVISIBLE);
            sortByCorrectRate.setVisibility(View.INVISIBLE);
        } else {
            sortById.setVisibility(View.VISIBLE);
            sortByCheckInRate.setVisibility(View.VISIBLE);
            sortByCorrectRate.setVisibility(View.VISIBLE);
        }
    }

}
