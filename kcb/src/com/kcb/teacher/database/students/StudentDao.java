package com.kcb.teacher.database.students;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kcb.teacher.database.CommonSQLiteOpenHelper;
import com.kcb.teacher.database.checkin.CheckInDB;
import com.kcb.teacher.model.stucentre.Student;

public class StudentDao {
	private CommonSQLiteOpenHelper mCommonSQLiteOpenHelper;
	private SQLiteDatabase mSqLiteDatabase;

	public StudentDao(Context context) {
		mCommonSQLiteOpenHelper = new CommonSQLiteOpenHelper(context);
		mSqLiteDatabase = mCommonSQLiteOpenHelper.getWritableDatabase();
	}

	/**
	 * 
	 * @title: addCheckInReslt
	 * @description: add a CheckInResult to db
	 * @author: ZQJ
	 * @date: 2015年5月31日 下午8:15:14
	 * @param checkInResult
	 * @throws SQLException
	 * @throws JSONException
	 */
	public void insert(Student student) throws SQLException, JSONException {
		mSqLiteDatabase.execSQL("INSERT INTO " + CheckInDB.TABLE_NAME
				+ " VALUES(null,?,?,?,?)", new String[] { student.getName(),
				student.getId(), String.valueOf(student.getCheckInRate()),
				String.valueOf(student.getCorrectRate()) });
	}

	/**
	 * 
	 * @title: getCheckInResultByDate
	 * @description: get CheckInResults from db by Date info
	 * @author: ZQJ
	 * @date: 2015年5月31日 下午8:49:27
	 * @param date
	 * @return
	 * @throws ParseException
	 * @throws JSONException
	 */
	public List<Student> getStuCentreById(String stuId) throws ParseException,
			JSONException {
		Cursor cursor = mSqLiteDatabase.rawQuery("SELECT * FROM "
				+ StudentDB.TABLE_NAME + " WHERE " + StudentDB.KEY_NAME + "=?",
				new String[] { stuId });
		List<Student> stuCentreList = new ArrayList<Student>();
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor
						.getColumnIndex(StudentDB.KEY_NAME));
				String studentId = cursor.getString(cursor
						.getColumnIndex(StudentDB.KEY_STUID));

				String checkinRate = cursor.getString(cursor
						.getColumnIndex(StudentDB.KEY_CHECKINRATE));
				String correctRate = cursor.getString(cursor
						.getColumnIndex(StudentDB.KEY_CORRECTRATE));

				double mcheckinRate = Double.valueOf(checkinRate);
				double mcorrectRate = Double.valueOf(correctRate);

				stuCentreList.add(new Student(name, studentId, mcheckinRate,
						mcorrectRate));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return stuCentreList;
	}

	/**
	 * 
	 * @title: getAllCheckInResults
	 * @description: get all checkinresult records from db
	 * @author: ZQJ
	 * @date: 2015年5月31日 下午8:37:50
	 * @return
	 * @throws ParseException
	 * @throws JSONException
	 */
	public List<Student> getAllStuCentre() throws ParseException, JSONException {
		Cursor cursor = mSqLiteDatabase.query(StudentDB.TABLE_NAME, null, null,
				null, null, null, null);
		List<Student> stuCentreList = new ArrayList<Student>();
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor
						.getColumnIndex(StudentDB.KEY_NAME));
				String studentId = cursor.getString(cursor
						.getColumnIndex(StudentDB.KEY_STUID));

				String checkinRate = cursor.getString(cursor
						.getColumnIndex(StudentDB.KEY_CHECKINRATE));
				String correctRate = cursor.getString(cursor
						.getColumnIndex(StudentDB.KEY_CORRECTRATE));

				double mcheckinRate = Double.valueOf(checkinRate);
				double mcorrectRate = Double.valueOf(correctRate);

				stuCentreList.add(new Student(name, studentId, mcheckinRate,
						mcorrectRate));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return stuCentreList;
	}

	public void deleteStuCentreById(String stuId) {
		mSqLiteDatabase.delete(StudentDB.TABLE_NAME,
				StudentDB.KEY_STUID + "=?", new String[] { stuId });
	}

	/**
	 * 
	 * @title: deleteAllCheckInResults
	 * @description: delete All records
	 * @author: ZQJ
	 * @date: 2015年5月31日 下午8:52:58
	 */
	public void deleteAllStuCentre() {
		mSqLiteDatabase.execSQL("DELETE FROM " + StudentDB.TABLE_NAME);
	}

	public void close() {
		mSqLiteDatabase.close();
	}

	/**
	 * 
	 * @title: changeUnCheckStudentsToString
	 * @description: List<UnCheckedStudent> to String
	 * @author: ZQJ
	 * @date: 2015年5月31日 下午8:14:07
	 * @param list
	 * @return
	 * @throws JSONException
	 */
	private static String changeStuCentreToString(List<Student> list)
			throws JSONException {
		int length = list.size();
		if (length < 1) {
			return null;
		}
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < length; i++) {
			jsonArray.put(changeStuCentreToJsonObject(list.get(i)));
		}
		return jsonArray.toString();
	}

	/**
	 * 
	 * @title: changeUnCheckStudentsToJsonObject
	 * @description: UnCheckedStudent to JSONObject
	 * @author: ZQJ
	 * @date: 2015年5月31日 下午8:14:33
	 * @param uncheckedStudent
	 * @return
	 * @throws JSONException
	 */
	private static JSONObject changeStuCentreToJsonObject(Student student)
			throws JSONException {

		if (null == student) {
			return null;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", student.getName());
		jsonObject.put("studentId", student.getId());
		jsonObject.put("checkinRate", student.getCheckInRate());
		jsonObject.put("correctRate", student.getCorrectRate());

		return jsonObject;

	}

	/**
	 * 
	 * @title: jsonStringToUncheckedStudent
	 * @description: jsonString to List<UnCheckedStudent>
	 * @author: ZQJ
	 * @date: 2015年5月31日 下午8:34:09
	 * @param jsonString
	 * @return
	 * @throws JSONException
	 */
	private static List<Student> jsonStringToStuCentre(String jsonString)
			throws JSONException {
		JSONArray jsonArray = new JSONArray(jsonString);
		int length = jsonArray.length();
		if (length < 1) {
			return null;
		}
		List<Student> list = new ArrayList<Student>();
		for (int i = 0; i < length; i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			list.add(jsonObjectToStuCentre(jsonObject));
		}
		return list;
	}

	private static Student jsonObjectToStuCentre(JSONObject jsonObject)
			throws JSONException {
		String name = jsonObject.getString("name");
		String id = jsonObject.getString("studentId");
		double checkinRate = jsonObject.getDouble("checkinRate");
		double correctRate = jsonObject.getDouble("correctRate");
		return new Student(name, id, checkinRate, correctRate);
	}
}
