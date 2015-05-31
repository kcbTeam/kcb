package com.kcb.student.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * 
 * @className: ImageToJsonString
 * @description:
 * @author: TaoLi
 * @date: 2015-5-23 下午3:38:38
 */

public class ImageToJsonString {

    // BASE64字符串转化为二进制数据
    public static byte[] decode(String string) {
        return Base64.decode(string, Base64.DEFAULT);
    }

    // 二进制数据编码为BASE64字符串
    public static String encode(byte[] bytes) {
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    // 将两个byte数组连接起来后，返回连接后的Byte数组
    public static byte[] connectBytes(byte[] front, byte[] after) {
        byte[] result = new byte[front.length + after.length];
        System.arraycopy(front, 0, result, 0, after.length);
        System.arraycopy(after, 0, result, front.length, after.length);
        return result;
    }

    // 将图片以Base64方式编码为json字符串
    public static String encodeImageToJson(Bitmap bitmap) throws IOException {
        return encode(getBitmapByte(bitmap));
    }

    // 将json中字符串转化为图片
    public static Bitmap decodeImagefromJson(String string) throws IOException {
        return getBitmapFromByte(decode(string));
    }

    // 将图片转换为字节流
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    // 将字节流转化为图片
    public static Bitmap getBitmapFromByte(byte[] bytes) {
        if (bytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        } else {
            return null;
        }
    }
}
