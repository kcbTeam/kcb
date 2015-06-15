package com.kcb.teacher.util;

import java.io.File;

import com.kcb.common.application.KApplication;
import com.kcb.teacher.model.account.KAccount;

/**
 * 
 * @className: FileUtil
 * @description: 老师模块，编辑题目的时候，如果有图片，需要设置拍照片的临时路径、图片的保存路径
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
     * 用于拍照片的临时目录，裁剪照片的时候，删除拍的照片
     */
    private static final String PATH_KCB_TEMP = PATH_KCB + "/temp";

    /**
     * /kcb/tch
     */
    private static final String PATH_KCB_TCH = PATH_KCB + "/tch";

    /**
     * /kcb/tch/tchid；一个手机可以登录不同的老师账号，需要根据tchId建立不同的路径
     */
    private static final String PATH_KCB_TCH_TCHID = PATH_KCB_TCH + "/" + KAccount.getAccountId();

    /**
     * /kcb/tch/tchid/test；test路径下面存储与测试相关的图片，在此路径下继续以testname建立路径；
     * testname路径下的图片保存格式为：题号_选项号.png，题号为1，2，3开始；选项好为0，1，2，3，4，其中0对于title，1-4对应4个选项
     */
    private static final String PATH_KCB_TCH_TCHID_TEST = PATH_KCB_TCH_TCHID + "/test";

    /**
     * 在老师编辑题目页面初始化路径
     */
    public static void init() {
        createPath(PATH_KCB);
        createPath(PATH_KCB_TEMP);
        createPath(PATH_KCB_TCH);
        createPath(PATH_KCB_TCH_TCHID);
        createPath(PATH_KCB_TCH_TCHID_TEST);
    }

    private static void createPath(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * 获取拍照的临时路径，照片拍摄结束后删除图片
     */
    public static String getTakePhotoPath() {
        return PATH_KCB_TEMP + "/takephoto.png";
    }

    /**
     * /kcb/tch/tchid/test/testname；根据测试的名称，建立这个测试的图片保存路径；如果删除了一个测试，需要将这个路径及下面的文件全部删除
     */
    public static String getTestPath(String testName) {
        File file = new File(PATH_KCB_TCH_TCHID_TEST, testName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * /kcb/tch/tchid/test/testname/1_3.png;
     * 根据测试的名称、题号、选项号来建立图片的名称；第一个数字从1开始，表示第几道题目，第二个数字从0-4，表示title、选项A-D
     */
    public static String getQuestionItemPath(String testName, int questionIndex, int itemIndex) {
        return getTestPath(testName) + "/" + questionIndex + "_" + itemIndex + ".png";
    }
}
