package com.kcb.student.util;

import java.io.File;

import com.kcb.common.application.KApplication;
import com.kcb.student.model.account.KAccount;


/**
 * 
 * @className: FileUtil
 * @description: 学生模块，开始答题的时候，需要从网络上获取题目，如果有图片，需要保存到本地
 * @author: wanghang
 * @date: 2015-6-10 下午2:41:06
 */
public class FileUtil {

    /**
     * /kcb
     */
    private static final String PATH_KCB = KApplication.getInstance().getExternalFilesDir(null)
            .getAbsolutePath()
            + "/kcb";

    /**
     * /kcb/stu
     */
    private static final String PATH_KCB_STU = PATH_KCB + "/stu";

    /**
     * /kcb/stu/stuid；一个手机可以登录不同的学生账号，需要根据stuId建立不同的路径
     */
    private static final String PATH_KCB_STU_STUID = PATH_KCB_STU + "/" + KAccount.getAccountId();

    /**
     * /kcb/stu/stuid/test；test路径下面存储与测试相关的图片，在此路径下继续以testname建立路径；
     * testname路径下的图片保存格式为：题号_选项号.png，题号为1，2，3开始；选项好为0，1，2，3，4，其中0对于title，1-4对应4个选项
     */
    private static final String PATH_KCB_STU_STUID_TEST = PATH_KCB_STU_STUID + "/test";

    /**
     * 在获取的测试内容后，将图片string转成bitmap之前初始化路径
     */
    public static void init() {
        createPath(PATH_KCB);
        createPath(PATH_KCB_STU);
        createPath(PATH_KCB_STU_STUID);
        createPath(PATH_KCB_STU_STUID_TEST);
    }

    private static void createPath(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * /kcb/stu/stuid/test/testname；根据测试的名称，建立这个测试的图片保存路径；如果删除了一个测试，需要将这个路径及下面的文件全部删除
     */
    public static String getTestPath(String testName) {
        File file = new File(PATH_KCB_STU_STUID_TEST, testName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * /kcb/stu/stuid/test/testname/1_3.png;
     * 根据测试的名称、题号、选项号来建立图片的名称；第一个数字从1开始，表示第几道题目，第二个数字从0-4，表示title、选项A-D
     */
    public static String getQuestionItemPath(String testName, int questionIndex, int itemIndex) {
        return getTestPath(testName) + "/" + questionIndex + "_" + itemIndex + ".png";
    }
}
