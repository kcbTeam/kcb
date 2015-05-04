package com.kcb.common.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View.OnClickListener;

import com.kcb.library.view.MaterialDialog;

/**
 * 
 * @className: DialogUtil
 * @description: used for create dialog and show it;
 * @author: wanghang
 * @date: 2015-5-4 下午10:31:27
 */
public class DialogUtil {

    /**
     * 
     * @title: showDialog
     * @description: show a dialog
     * @author: wanghang
     * @date: 2015-5-4 下午10:29:45
     * @param context
     * @param title
     * @param message
     * @param sureText, cannot be null;
     * @param sureListener, can be null(only dismiss after click);
     * @param cancelText, can be null(don't show cancel button);
     * @param cancelListener, can be null(only dismiss after click);
     */
    public static void showDialog(Context context, String title, String message,
            @NonNull String sureText, OnClickListener sureListener, String cancelText,
            OnClickListener cancelListener) {
        MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setSureButton(sureText, sureListener);
        dialog.setCancelButton(cancelText, cancelListener);
        dialog.show();
    }
}
