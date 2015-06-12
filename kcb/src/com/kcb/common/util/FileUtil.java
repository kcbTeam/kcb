package com.kcb.common.util;

import java.io.File;

import android.os.Environment;

public class FileUtil {

    /**
     * invoked init() in EditTestActivity
     */
    private static final String PATH_KCB_FOLDER = Environment.getExternalStorageDirectory()
            + "/kcb";

    private static final String PATH_TEMP = Environment.getExternalStorageDirectory() + "/kcb/temp";
    private static final String NAME_TEMP_FOLDER = "temp";

    private static final String PATH_TEST = Environment.getExternalStorageDirectory() + "/kcb/test";
    private static final String NAME_TEST_FOLDER = "test";

    public static void init() {
        File folder = new File(PATH_KCB_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File tempFolder = new File(PATH_KCB_FOLDER, NAME_TEMP_FOLDER);
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }

        File testFolder = new File(PATH_KCB_FOLDER, NAME_TEST_FOLDER);
        if (!testFolder.exists()) {
            tempFolder.mkdirs();
        }
    }

    public static String getTakePhotoPath() {
        LogUtil.e("wanghang", PATH_TEMP + "/takephoto.png");

        return PATH_TEMP + "/takephoto.png";
    }

    public static String getCropPhotoPath() {
        LogUtil.e("wanghang", PATH_TEMP + "/cropphoto.png");

        return PATH_TEMP + "/cropphoto.png";
    }

    public static String getQuestionItemPath(String testName, int questionIndex, int itemIndex) {
        File file = new File(PATH_TEST, testName);
        if (!file.exists()) {
            file.mkdirs();
        }

        LogUtil.e("wanghang", file.getAbsolutePath() + "/" + questionIndex + "_" + itemIndex
                + ".png");

        return file.getAbsolutePath() + "/" + questionIndex + "_" + itemIndex + ".png";
    }
}
