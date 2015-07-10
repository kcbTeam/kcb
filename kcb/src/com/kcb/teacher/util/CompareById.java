package com.kcb.teacher.util;

import java.util.Comparator;

import com.kcb.teacher.database.students.Student;

public class CompareById implements Comparator<Student> {

    @Override
    public int compare(Student lhs, Student rhs) {
        return lhs.getId().compareTo(rhs.getId());
    }
}
