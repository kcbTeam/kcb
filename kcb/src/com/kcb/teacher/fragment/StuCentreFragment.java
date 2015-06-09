package com.kcb.teacher.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.base.BaseFragment;
import com.kcb.common.util.StringMatchUtil;
import com.kcb.common.view.SearchEditText;
import com.kcb.common.view.SearchEditText.OnSearchListener;
import com.kcb.teacher.activity.stucentre.StuCentreActivity;
import com.kcb.teacher.adapter.StuCentreAdapter;
import com.kcb.teacher.database.students.StudentDao;
import com.kcb.teacher.model.stucentre.Student;
import com.kcb.teacher.util.CompareByCheckInRate;
import com.kcb.teacher.util.CompareByCorrectRate;
import com.kcb.teacher.util.CompareById;
import com.kcbTeam.R;

/**
 * 
 * @className: StuCentreFragment
 * @description:
 * @author: ZQJ
 * @date: 2015年4月24日 下午3:24:10
 */
public class StuCentreFragment extends BaseFragment
        implements
            OnSearchListener,
            OnItemClickListener {

    private final int INDEX_ID = 0;
    private final int INDEX_CHECKIN_RATE = 1;
    private final int INDEX_CORRECT_RATE = 2;

    private TextView idTextView;
    private TextView checkInRaTextView;
    private TextView correctRaTextView;

    private SearchEditText searchEditText;

    private ListView listView;

    private StuCentreAdapter mAdapter;
    private List<Student> mStudents;
    private List<Student> mTempStudents;

    private String mSearchKey;

    private CompareById mIdComparator;
    private CompareByCheckInRate mCheckInRateComparator;
    private CompareByCorrectRate mCorrectRateComparator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tch_fragment_stucentre, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        View view = getView();
        searchEditText = (SearchEditText) view.findViewById(R.id.searchedittext);
        searchEditText.setOnSearchListener(this);
        searchEditText.setHint(R.string.tch_input_name_search);

        idTextView = (TextView) view.findViewById(R.id.textview_stuinfo);
        idTextView.setOnClickListener(this);

        checkInRaTextView = (TextView) view.findViewById(R.id.textview_checkinrate);
        checkInRaTextView.setOnClickListener(this);

        correctRaTextView = (TextView) view.findViewById(R.id.textview_correctrate);
        correctRaTextView.setOnClickListener(this);

        listView = (ListView) view.findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        setSortIcon(INDEX_ID);
    }

    @Override
    protected void initData() {
        mIdComparator = new CompareById();
        mCorrectRateComparator = new CompareByCorrectRate();
        mCheckInRateComparator = new CompareByCheckInRate();

        StudentDao mStudentDao = new StudentDao(getActivity());
        mStudents = mStudentDao.getAll();
        mStudentDao.close();

        Collections.sort(mStudents, mIdComparator);

        mTempStudents = new ArrayList<Student>();
        mTempStudents.addAll(mStudents);

        mAdapter = new StuCentreAdapter(getActivity(), mTempStudents);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_stuinfo:
                if (TextUtils.isEmpty(mSearchKey)) {
                    new SortTast(INDEX_ID).execute();
                    setSortIcon(INDEX_ID);
                }
                break;
            case R.id.textview_checkinrate:
                if (TextUtils.isEmpty(mSearchKey)) {
                    new SortTast(INDEX_CHECKIN_RATE).execute();
                    setSortIcon(INDEX_CHECKIN_RATE);
                }
                break;
            case R.id.textview_correctrate:
                if (TextUtils.isEmpty(mSearchKey)) {
                    new SortTast(INDEX_CORRECT_RATE).execute();
                    setSortIcon(INDEX_CORRECT_RATE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * search listenr
     */
    @Override
    public void onSearch(String text) {
        mSearchKey = text;
        mTempStudents.clear();
        for (int i = 0; i < mStudents.size(); i++) {
            Student student = mStudents.get(i);
            String name = student.getName();
            try {
                if (StringMatchUtil.isMatch(name, mSearchKey)) {
                    mTempStudents.add(student);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {}
        }
        hideSortButton();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClear() {
        mTempStudents.clear();
        mTempStudents.addAll(mStudents);
        setSortIcon(INDEX_ID);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StuCentreActivity.start(getActivity(), mAdapter.getItem(position));
    }

    private void setSortIcon(int index) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_18dp);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        hideSortButton();
        switch (index) {
            case INDEX_ID:
                idTextView.setCompoundDrawables(null, null, null, drawable);
                break;
            case INDEX_CHECKIN_RATE:
                checkInRaTextView.setCompoundDrawables(null, null, null, drawable);
                break;
            case INDEX_CORRECT_RATE:
                correctRaTextView.setCompoundDrawables(null, null, null, drawable);
                break;
            default:
                break;
        }
    }

    private void hideSortButton() {
        idTextView.setCompoundDrawables(null, null, null, null);
        checkInRaTextView.setCompoundDrawables(null, null, null, null);
        correctRaTextView.setCompoundDrawables(null, null, null, null);
    }

    private class SortTast extends AsyncTask<Integer, Integer, Integer> {
        private int index;

        public SortTast(int index) {
            this.index = index;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            switch (index) {
                case INDEX_ID:
                    Collections.sort(mTempStudents, mIdComparator);
                    break;
                case INDEX_CHECKIN_RATE:
                    Collections.sort(mTempStudents, mCheckInRateComparator);
                    break;
                case INDEX_CORRECT_RATE:
                    Collections.sort(mTempStudents, mCorrectRateComparator);
                    break;
                default:
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
