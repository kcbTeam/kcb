package com.kcb.teacher.util;

import java.util.Comparator;

import com.kcb.teacher.model.stucentre.Student;

public class CompareByCorrectRate implements Comparator<Student> {
    @SuppressWarnings("unused")
    private static final String TAG = "CompareByCorrectRate";

    @Override
    public int compare(Student lhs, Student rhs) {
        return -String.valueOf(lhs.getCorrectRate())
                .compareTo(String.valueOf(rhs.getCorrectRate()));
    }
}
