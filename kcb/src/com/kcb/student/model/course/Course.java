package com.kcb.student.model.course;

/**
 * 
 * @className: Course
 * @description: 学生模块的课程信息，表示学生选择的课程信息
 * @author: wanghang
 * @date: 2015-7-6 下午1:41:57
 */
public class Course {

    private String mUniversityName; // 大学名称全称，唯一的
    private String mId; // 课程号
    private String mName; // 课程名称
    private String mTchId; // 此课程对应的老师id
    private String mTchName; // 此课程对应的老师姓名

    // 每次打开应用的时候，从数据库中加载当前选择的课程
    static {

    }

    public String getUniversityName() {
        return mUniversityName;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getTchId() {
        return mTchId;
    }

    public String getTchName() {
        return mTchName;
    }
}
