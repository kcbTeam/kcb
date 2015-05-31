package com.kcb.teacher.util;

import java.util.Comparator;

import com.kcb.teacher.model.stucentre.Student;

public class CompareById implements Comparator<Student> {
    @SuppressWarnings("unused")
    private static final String TAG = "CompareById";

    @Override
    public int compare(Student lhs, Student rhs) {
        return lhs.getId().compareTo(rhs.getId());
    }

}
