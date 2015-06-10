package com.kcb.teacher.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.json.JSONArray;
import org.json.JSONException;
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
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kcb.common.base.BaseFragment;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.ResponseUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.StringMatchUtil;
import com.kcb.common.view.EmptyTipView;
import com.kcb.common.view.SearchEditText;
import com.kcb.common.view.SearchEditText.OnSearchListener;
import com.kcb.library.view.smoothprogressbar.SmoothProgressBar;
import com.kcb.teacher.activity.stucentre.StuCentreActivity;
import com.kcb.teacher.adapter.StuCentreAdapter;
import com.kcb.teacher.database.students.StudentDao;
import com.kcb.teacher.model.account.KAccount;
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

    private static final String TAG = StuCentreFragment.class.getName();

    private final int INDEX_ID = 0;
    private final int INDEX_CHECKIN_RATE = 1;
    private final int INDEX_CORRECT_RATE = 2;

    private SmoothProgressBar progressBar;
    private SearchEditText searchEditText;

    private View sortLayout;
    private TextView idTextView;
    private TextView checkInRaTextView;
    private TextView correctRaTextView;

    private ListView listView;

    private EmptyTipView emptyTipView;

    private StuCentreAdapter mAdapter;
    private List<Student> mAllStudents;
    private List<Student> mSearchedStudents;

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

        sortLayout = view.findViewById(R.id.layout_sort);

        idTextView = (TextView) view.findViewById(R.id.textview_stuinfo);
        idTextView.setOnClickListener(this);

        checkInRaTextView = (TextView) view.findViewById(R.id.textview_checkinrate);
        checkInRaTextView.setOnClickListener(this);

        correctRaTextView = (TextView) view.findViewById(R.id.textview_correctrate);
        correctRaTextView.setOnClickListener(this);

        listView = (ListView) view.findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        emptyTipView = (EmptyTipView) view.findViewById(R.id.emptytipview);

        setSortIcon(INDEX_ID);
    }

    @Override
    protected void initData() {
        mIdComparator = new CompareById();
        mCorrectRateComparator = new CompareByCorrectRate();
        mCheckInRateComparator = new CompareByCheckInRate();

        StudentDao mStudentDao = new StudentDao(getActivity());
        mAllStudents = mStudentDao.getAll();
        mStudentDao.close();

        if (mAllStudents.isEmpty()) {
            sortLayout.setVisibility(View.INVISIBLE);
            emptyTipView.setVisibility(View.VISIBLE);
            emptyTipView.setEmptyText(R.string.tch_no_student);
        }

        Collections.sort(mAllStudents, mIdComparator);

        mSearchedStudents = new ArrayList<Student>();
        mSearchedStudents.addAll(mAllStudents);

        mAdapter = new StuCentreAdapter(getActivity(), mSearchedStudents);
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
        if (mAllStudents.isEmpty()) {
            return;
        }
        mSearchKey = text;
        mSearchedStudents.clear();
        for (int i = 0; i < mAllStudents.size(); i++) {
            Student student = mAllStudents.get(i);
            String name = student.getName();
            try {
                if (StringMatchUtil.isMatch(name, mSearchKey)) {
                    mSearchedStudents.add(student);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {}
        }
        hideSortButton();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClear() {
        if (mAllStudents.isEmpty()) {
            return;
        }
        mSearchedStudents.clear();
        mSearchedStudents.addAll(mAllStudents);
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
                    Collections.sort(mSearchedStudents, mIdComparator);
                    break;
                case INDEX_CHECKIN_RATE:
                    Collections.sort(mSearchedStudents, mCheckInRateComparator);
                    break;
                case INDEX_CORRECT_RATE:
                    Collections.sort(mSearchedStudents, mCorrectRateComparator);
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

    public void setProgressBar(SmoothProgressBar _progressBar) {
        progressBar = _progressBar;
    }

    public void refresh() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request =
                new JsonArrayRequest(Method.GET, UrlUtil.getTchStucenterLookinfoUrl(KAccount
                        .getAccountId()), new Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.hide(getActivity());
                        List<Student> students = new ArrayList<Student>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Student student = Student.fromjsonObject(response.getJSONObject(i));
                                students.add(student);
                            } catch (JSONException e) {}
                        }
                        if (!students.isEmpty()) {
                            sortLayout.setVisibility(View.VISIBLE);
                            emptyTipView.setVisibility(View.GONE);
                            mAllStudents.clear();
                            mAllStudents.addAll(students);
                            mSearchedStudents.clear();
                            mSearchedStudents.addAll(students);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.hide(getActivity());
                        ResponseUtil.toastError(error);
                    }
                });
        RequestUtil.getInstance().addToRequestQueue(request, TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestUtil.getInstance().cancelPendingRequests(TAG);
        searchEditText.release();
        mAdapter.release();
        mAdapter = null;
        mAllStudents = null;
        mSearchedStudents = null;
    }
}
