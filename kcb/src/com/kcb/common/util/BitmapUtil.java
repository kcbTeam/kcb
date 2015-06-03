package com.kcb.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BitmapUtil {

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encode(out.toByteArray(), Base64.DEFAULT));
    }

    public static Bitmap stringToBitmap(String text) {
        byte[] bytes = Base64.decode(text, Base64.DEFAULT);
        if (bytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        } else {
            return null;
        }
    }
}
