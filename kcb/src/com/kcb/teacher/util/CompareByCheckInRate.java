package com.kcb.teacher.util;

import java.util.Comparator;

import com.kcb.teacher.model.StudentInfo;

public class CompareByCheckInRate implements Comparator<StudentInfo> {
    @SuppressWarnings("unused")
    private static final String TAG = "CompareByCheckInRate";

    @Override
    public int compare(StudentInfo lhs, StudentInfo rhs) {
        return -String.valueOf(lhs.getCheckInRate()).compareTo(String.valueOf(rhs.getCheckInRate()));
    }
}
