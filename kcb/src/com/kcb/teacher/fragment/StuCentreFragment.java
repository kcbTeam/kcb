package com.kcb.teacher.fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.json.JSONException;

import android.annotation.SuppressLint;
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
import com.kcb.common.view.SearchEditText.SearchListener;
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
public class StuCentreFragment extends BaseFragment implements SearchListener, OnItemClickListener {

    private final int INDEX_ID = 0;
    private final int INDEX_CHECKIN_RATE = 1;
    private final int INDEX_CORRECT_RATE = 2;

    private TextView sortById;
    private TextView sortByCheckInRate;
    private TextView sortByCorrectRate;

    private SearchEditText searchEditText;

    private ListView mStudentList;
    private StuCentreAdapter mAdapter;
    private List<Student> mStudents;
    private List<Student> mTempStudents;
    private String mSearchKey;
    private CompareById idComparator;
    private CompareByCheckInRate checkInRateComparator;
    private CompareByCorrectRate correctRateComparator;

    private GetAllStudenInfoTask mGetAllStudenInfoTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tch_fragment_stucentre, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        View view = getView();
        mStudentList = (ListView) view.findViewById(R.id.listview_student);
        mStudentList.setAdapter(mAdapter);
        mStudentList.setOnItemClickListener(this);

        searchEditText = (SearchEditText) view.findViewById(R.id.searchedittext);
        searchEditText.setOnSearchListener(this);
        searchEditText.setHint(R.string.tch_input_name_search);

        sortById = (TextView) view.findViewById(R.id.textview_stuinfo);
        sortById.setOnClickListener(this);

        sortByCheckInRate = (TextView) view.findViewById(R.id.textview_stucheckinrate);
        sortByCheckInRate.setOnClickListener(this);

        sortByCorrectRate = (TextView) view.findViewById(R.id.textview_correctrate);
        sortByCorrectRate.setOnClickListener(this);
        onClick(sortById);
    }

    @Override
    protected void initData() {
        mStudents = new ArrayList<Student>();
        idComparator = new CompareById();
        correctRateComparator = new CompareByCorrectRate();
        checkInRateComparator = new CompareByCheckInRate();

        Collections.sort(mStudents, idComparator);
        mTempStudents = new ArrayList<Student>();
        mTempStudents.addAll(mStudents);
        mAdapter = new StuCentreAdapter(getActivity(), mTempStudents);

        mGetAllStudenInfoTask = new GetAllStudenInfoTask();
        mGetAllStudenInfoTask.execute(0);
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
            case R.id.textview_stucheckinrate:
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
        mAdapter.notifyDataSetChanged();
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
        hideSortButton(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClear() {
        mTempStudents.addAll(mStudents);
        hideSortButton(false);
        onClick(sortById);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StuCentreActivity.start(getActivity(), mAdapter.getItem(position));
    }

    @SuppressLint("NewApi")
    private void setSortIcon(int index) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_18dp);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        sortById.setCompoundDrawables(null, null, null, null);
        sortByCheckInRate.setCompoundDrawables(null, null, null, null);
        sortByCorrectRate.setCompoundDrawables(null, null, null, null);
        switch (index) {
            case INDEX_ID:
                sortById.setCompoundDrawables(null, null, null, drawable);
                break;
            case INDEX_CHECKIN_RATE:
                sortByCheckInRate.setCompoundDrawables(null, null, null, drawable);
                break;
            case INDEX_CORRECT_RATE:
                sortByCorrectRate.setCompoundDrawables(null, null, null, drawable);
                break;
            default:
                break;
        }
    }

    private void hideSortButton(boolean hide) {
        if (hide) {
            sortById.setCompoundDrawables(null, null, null, null);
            sortByCheckInRate.setCompoundDrawables(null, null, null, null);
            sortByCorrectRate.setCompoundDrawables(null, null, null, null);
        } else {
            Drawable drawable =
                    getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_18dp);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            sortById.setCompoundDrawables(null, null, null, drawable);
        }
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
                    Collections.sort(mTempStudents, idComparator);
                    break;
                case INDEX_CHECKIN_RATE:
                    Collections.sort(mTempStudents, checkInRateComparator);
                    break;
                case INDEX_CORRECT_RATE:
                    Collections.sort(mTempStudents, correctRateComparator);
                    break;
                default:
                    break;
            }
            return null;
        }
    }

    private class GetAllStudenInfoTask extends AsyncTask<Integer, Integer, List<Student>> {

        @Override
        protected List<Student> doInBackground(Integer... params) {
            try {
                StudentDao mStudentDao = new StudentDao(getActivity());
                mStudents = mStudentDao.getAll();
                mStudentDao.close();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mStudents;
        }

        @Override
        protected void onPostExecute(List<Student> result) {
            super.onPostExecute(result);
            mTempStudents.addAll(result);
            Collections.sort(mTempStudents, idComparator);
            mAdapter.notifyDataSetChanged();

        }
    }
}
