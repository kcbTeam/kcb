package com.kcb.common.util;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View.OnClickListener;

import com.kcb.common.view.dialog.MaterialBitmapDialog;
import com.kcb.common.view.dialog.MaterialBitmapDialog.OnActionListener;
import com.kcb.common.view.dialog.MaterialDialog;
import com.kcb.common.view.dialog.MaterialListDialog;
import com.kcb.common.view.dialog.MaterialListDialog.OnClickSureListener;

/**
 * 
 * @className: DialogUtil
 * @description: used for create and show reused dialog
 * @author: wanghang
 * @date: 2015-5-4 下午10:31:27
 */
public class DialogUtil {

    private static final String TAG = DialogUtil.class.getName();

    /**
     * 在老师、学生模块的确定按钮字体颜色应该不一样
     */
    private static int sSureTextColor;

    public static void setSureTextColor(int color) {
        sSureTextColor = color;
    }

    /**
     * 
     * @title: showNormalDialog
     * @description: show a dialog with title/message/sure button/cancel button
     * @author: wanghang
     * @date: 2015-5-4 下午10:29:45
     * @param context
     * @param titleResId
     * @param messageResId
     * @param sureResId , cannot be null;
     * @param sureListener , can be null (only dismiss after click);
     * @param cancelResId , can be -1 (don't show cancel button);
     * @param cancelListener , can be null (only dismiss after click);
     */
    public static MaterialDialog showNormalDialog(Context context, int titleResId,
            int messageResId, @NonNull int sureResId, OnClickListener sureListener,
            int cancelResId, OnClickListener cancelListener) {
        MaterialDialog dialog = new MaterialDialog(context);
        try {
            dialog.show();
            dialog.setTitle(titleResId);
            dialog.setMessage(messageResId);
            dialog.setSureButton(sureResId, sureListener);
            dialog.setCancelButton(cancelResId, cancelListener);
            dialog.setSureTextColor(sSureTextColor);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return dialog;
    }

    /**
     * 
     * @title: showNormalDialog
     * @description:
     * @author: TaoLi
     * @date: 2015-5-24 下午2:36:52
     * @param context
     * @param titleResId
     * @param message
     * @param sureResId
     * @param sureListener
     * @param cancelResId
     * @param cancelListener
     */
    public static void showNormalDialog(Context context, int titleResId, CharSequence message,
            @NonNull int sureResId, OnClickListener sureListener, int cancelResId,
            OnClickListener cancelListener) {
        try {
            MaterialDialog dialog = new MaterialDialog(context);
            dialog.show();
            dialog.setTitle(titleResId);
            dialog.setMessage(message);
            dialog.setSureButton(sureResId, sureListener);
            dialog.setCancelButton(cancelResId, cancelListener);
            dialog.setSureTextColor(sSureTextColor);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

    /**
     * 
     * @title: showDialog
     * @description: show a dialog with title/list/sure Button/cancel Button
     * @author: ljx
     * @date: 2015年5月7日 下午8:06:42
     * @param context
     * @param title
     * @param messages
     * @param sureText , cannot be null;
     * @param sureListener , can be null (only dismiss after click);
     * @param cancelText , can be -1 (don't show cancel button);
     * @param cancelListener , can be null (only dismiss after click);
     * 
     */
    public static MaterialListDialog showListDialog(Context context, int titleResId,
            List<String> messages, @NonNull int sureResId, OnClickSureListener sureListener,
            int cancelResId, OnClickListener cancelListener) {
        MaterialListDialog dialog = new MaterialListDialog(context);
        try {
            dialog.show();
            dialog.setTitle(titleResId);
            dialog.setAdapter(messages);
            dialog.setSureButton(sureResId, sureListener);
            dialog.setCancelButton(cancelResId, cancelListener);
            dialog.setSureTextColor(sSureTextColor);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return dialog;
    }

    /**
     * 
     * @title: showBitmapDialog
     * @description:
     * @author:
     * @date: 2015-7-7 下午2:27:16
     * @param context
     * @param titleResId
     * @param bitmap
     * @param listener, if is null, only has sure button
     * @param flag , if has action, must has
     */
    public static void showBitmapDialog(Context context, int titleResId, Bitmap bitmap,
            OnActionListener listener, int flag) {
        MaterialBitmapDialog dialog = new MaterialBitmapDialog(context);
        try {
            dialog.show();
            dialog.setTitle(titleResId);
            dialog.setImageView(bitmap);
            dialog.setOnActionListener(flag, listener);
            dialog.setSureTextColor(sSureTextColor);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }
}
