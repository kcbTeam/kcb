package com.kcb.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * 
 * @className: BitmapUtil
 * @description:
 * @author: wanghang
 * @date: 2015-6-4 下午2:55:25
 */
public class BitmapUtil {

    /**
     * 图片转成String
     */
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    /**
     * string转成图片
     */
    public static Bitmap stringToBitmap(String text) {
        byte[] byteArray = Base64.decode(text, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    /**
     * 根据路径和图片，来保存一张图片
     */
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
