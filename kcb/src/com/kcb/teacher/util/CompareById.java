package com.kcb.teacher.util;

import java.util.Comparator;

import com.kcb.teacher.model.StudentInfo;

public class CompareById implements Comparator<StudentInfo> {
    @SuppressWarnings("unused")
    private static final String TAG = "CompareById";

    @Override
    public int compare(StudentInfo lhs, StudentInfo rhs) {
        return lhs.getStudentID().compareTo(rhs.getStudentID());
    }

}
