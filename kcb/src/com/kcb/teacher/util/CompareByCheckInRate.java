package com.kcb.teacher.util;

import java.util.Comparator;

import com.kcb.teacher.model.stucentre.Student;

public class CompareByCheckInRate implements Comparator<Student> {
    @SuppressWarnings("unused")
    private static final String TAG = "CompareByCheckInRate";

    @Override
    public int compare(Student lhs, Student rhs) {
        return -String.valueOf(lhs.getCheckInRate())
                .compareTo(String.valueOf(rhs.getCheckInRate()));
    }
}
