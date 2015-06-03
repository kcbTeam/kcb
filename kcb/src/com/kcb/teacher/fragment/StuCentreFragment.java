package com.kcb.teacher.fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kcb.common.base.BaseFragment;
import com.kcb.library.view.FloatingEditText;
import com.kcb.teacher.activity.StuCentreActivity;
import com.kcb.teacher.adapter.ListAdapterStudent;
import com.kcb.teacher.database.students.StudentDao;
import com.kcb.teacher.model.stucentre.Student;
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
public class StuCentreFragment extends BaseFragment implements
		OnItemClickListener, TextWatcher {

	private final int INDEX_ID = 0;
	private final int INDEX_CHECKIN_RATE = 1;
	private final int INDEX_CORRECT_RATE = 2;

	private TextView sortById;
	private TextView sortByCheckInRate;
	private TextView sortByCorrectRate;

	private FloatingEditText searchEditText;
	private ImageView clearImageView;

	private ListView mStudentList;
	private ListAdapterStudent mAdapter;
	private List<Student> mList;
	private List<Student> mTempList;
	private CompareById idComparator;
	private CompareByCheckInRate checkInRateComparator;
	private CompareByCorrectRate correctRateComparator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.tch_fragment_stucentre, container,
				false);
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
		mAdapter = new ListAdapterStudent(getActivity(), mTempList);
		mStudentList.setAdapter(mAdapter);
		mStudentList.setOnItemClickListener(this);

		searchEditText = (FloatingEditText) view
				.findViewById(R.id.edittext_search);
		searchEditText.addTextChangedListener(this);

		clearImageView = (ImageView) view.findViewById(R.id.imageview_clear);
		clearImageView.setOnClickListener(this);

		sortById = (TextView) view.findViewById(R.id.textview_stuinfo);
		sortById.setOnClickListener(this);

		sortByCheckInRate = (TextView) view
				.findViewById(R.id.textview_stucheckinrate);
		sortByCheckInRate.setOnClickListener(this);

		sortByCorrectRate = (TextView) view
				.findViewById(R.id.textview_correctrate);
		sortByCorrectRate.setOnClickListener(this);

		onClick(sortById);
	}

	@Override
	protected void initData() {
		idComparator = new CompareById();
		correctRateComparator = new CompareByCorrectRate();
		checkInRateComparator = new CompareByCheckInRate();

		StudentDao mStudentDao = new StudentDao(getActivity());
		try {
			mStudentDao
					.addStudentRecord(new Student("ljx", "123123", 0.9, 0.9));
			mStudentDao
					.addStudentRecord(new Student("asd", "1234324", 0.8, 0.9));

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mList = mStudentDao.getAllStuCentre();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Collections.sort(mList, idComparator);
		mTempList = new ArrayList<Student>();
		mTempList.addAll(mList);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	private String searchContent;

	@Override
	public void afterTextChanged(Editable s) {
		mTempList.clear();
		mTempList.addAll(mList);
		searchContent = searchEditText.getText().toString().trim()
				.replace(" ", "");
		if (!searchContent.equals("")) {
			clearImageView.setVisibility(View.VISIBLE);
		} else {
			clearImageView.setVisibility(View.INVISIBLE);
		}
		for (int i = 0; i < mTempList.size(); i++) {
			String name = mTempList.get(i).getName();
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
		if (!searchContent.equals("")) {
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
		case R.id.textview_stuinfo:
			if (TextUtils.isEmpty(searchContent)) {
				new SortTast(INDEX_ID).execute();
				setImageBackGround(INDEX_ID);
			}
			break;
		case R.id.textview_stucheckinrate:
			if (TextUtils.isEmpty(searchContent)) {
				new SortTast(INDEX_CHECKIN_RATE).execute();
				setImageBackGround(INDEX_CHECKIN_RATE);
			}
			break;
		case R.id.textview_correctrate:
			if (TextUtils.isEmpty(searchContent)) {
				new SortTast(INDEX_CORRECT_RATE).execute();
				setImageBackGround(INDEX_CORRECT_RATE);
			}
			break;
		case R.id.imageview_clear:
			searchEditText.setText("");
			clearImageView.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
		mAdapter.notifyDataSetChanged();
	}

	@SuppressLint("NewApi")
	private void setImageBackGround(int index) {
		Drawable drawable = getResources().getDrawable(
				R.drawable.ic_arrow_drop_down_black_18dp);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());

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
			Drawable drawable = getResources().getDrawable(
					R.drawable.ic_arrow_drop_down_black_18dp);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			sortById.setCompoundDrawables(null, null, null, drawable);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		StuCentreActivity.start(getActivity(), mAdapter.getItem(position));
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
				Collections.sort(mTempList, idComparator);
				break;
			case INDEX_CHECKIN_RATE:
				Collections.sort(mTempList, checkInRateComparator);
				break;
			case INDEX_CORRECT_RATE:
				Collections.sort(mTempList, correctRateComparator);
				break;
			default:
				break;
			}
			return null;
		}
	}
}
