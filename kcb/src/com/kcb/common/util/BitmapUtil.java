package com.kcb.common.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class BitmapUtil {

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();
        try {
            return new String(bitmapByte, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static Bitmap stringToBitmap(String text) {
        try {
            byte[] bitmapArray = text.getBytes("IOS-8859-1");
            return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            return null;
        }
    }
}
