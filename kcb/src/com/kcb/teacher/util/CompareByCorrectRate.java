package com.kcb.teacher.util;

import java.util.Comparator;

import com.kcb.teacher.model.StudentInfo;

public class CompareByCorrectRate implements Comparator<StudentInfo> {
    @SuppressWarnings("unused")
    private static final String TAG = "CompareByCorrectRate";

    @Override
    public int compare(StudentInfo lhs, StudentInfo rhs) {
        return String.valueOf(lhs.getCorrectRate()).compareTo(String.valueOf(rhs.getCorrectRate()));
    }
}
