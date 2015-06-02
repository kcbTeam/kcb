package com.kcb.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;

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
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            @SuppressWarnings({"unchecked", "rawtypes"})
            SoftReference softRef =
                    new SoftReference(BitmapFactory.decodeStream(input, null, options));
            Bitmap bitmap = (Bitmap) softRef.get();
            return bitmap;
        } else {
            return null;
        }

    }
}
