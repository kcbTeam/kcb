package com.kcb.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
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
        return PATH_TEMP + "/takephoto.png";
    }

    public static String getCropPhotoPath() {
        return PATH_TEMP + "/cropphoto.png";
    }

    public static String getTestPath(String testName) {
        File file = new File(PATH_TEST, testName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String getQuestionItemPath(String testName, int questionIndex, int itemIndex) {
        return getTestPath(testName) + "/" + questionIndex + "_" + itemIndex + ".png";
    }

    public static void saveBitmap(String path, Bitmap bitmap) {
        File file = new File(path);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {}
    }
}
