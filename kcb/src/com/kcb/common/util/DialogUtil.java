package com.kcb.common.util;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View.OnClickListener;

import com.kcb.common.view.MaterialDialog;
import com.kcb.common.view.MaterialListDialog;
import com.kcb.common.view.MaterialListDialog.OnClickSureListener;

/**
 * 
 * @className: DialogUtil
 * @description: used for create and show reused dialog
 * @author: wanghang
 * @date: 2015-5-4 下午10:31:27
 */
public class DialogUtil {

    /**
     * 
     * @title: showDialog
     * @description: show a dialog with title/message/sure button/cancel button
     * @author: wanghang
     * @date: 2015-5-4 下午10:29:45
     * @param context
     * @param titleResId
     * @param messageResId
     * @param sureResId , cannot be null;
     * @param sureListener , can be null (only dismiss after click);
     * @param cancelResId , can be null (don't show cancel button);
     * @param cancelListener , can be null (only dismiss after click);
     */
    public static void showNormalDialog(Context context, int titleResId, int messageResId,
            @NonNull int sureResId, OnClickListener sureListener, int cancelResId,
            OnClickListener cancelListener) {
        MaterialDialog dialog = new MaterialDialog(context);
        dialog.show();
        dialog.setTitle(titleResId);
        dialog.setMessage(messageResId);
        dialog.setSureButton(sureResId, sureListener);
        dialog.setCancelButton(cancelResId, cancelListener);
    }

    /**
     * 
     * @title: showNormalDialog
     * @description:
     * @author: TaoLi
     * @date: 2015-5-24 下午2:36:52
     * @param context
     * @param titleResId
     * @param text
     * @param sureResId
     * @param sureListener
     * @param cancelResId
     * @param cancelListener
     */
    public static void showNormalDialog(Context context, int titleResId, CharSequence text,
            @NonNull int sureResId, OnClickListener sureListener, int cancelResId,
            OnClickListener cancelListener) {
        MaterialDialog dialog = new MaterialDialog(context);
        dialog.show();
        dialog.setTitle(titleResId);
        dialog.setMessage(text);
        dialog.setSureButton(sureResId, sureListener);
        dialog.setCancelButton(cancelResId, cancelListener);
    }

    /**
     * 
     * @title: showDialog
     * @description: show a dialog with title/list/sure Button/cancel Button
     * @author: ljx
     * @date: 2015年5月7日 下午8:06:42
     * @param context
     * @param title
     * @param adapter
     * @param sureText , cannot be null;
     * @param sureListener , can be null (only dismiss after click);
     * @param cancelText , can be null (don't show cancel button);
     * @param cancelListener , can be null (only dismiss after click);
     * 
     */
    public static void showListDialog(Context context, String title, List<String> adapter,
            @NonNull String sureText, OnClickSureListener sureListener, String cancelText,
            OnClickListener cancelListener) {
        MaterialListDialog dialog = new MaterialListDialog(context);
        dialog.show();
        dialog.setTitle(title);
        dialog.setAdapter(adapter);
        dialog.setSureButton(sureText, sureListener);
        dialog.setCancelButton(cancelText, cancelListener);
    }

    /**
     * 
     * @title: showDeletePictureDialog
     * @description:
     * @author: ZQJ
     * @date: 2015年5月20日 下午5:07:11
     * @param context
     * @param title
     * @param reBackgroundListener
     * @param deleteBackgroundListener
     */
    // public static void showDeletePictureDialog(Context context, String title,
    // OnClickListener reBackgroundListener, OnClickListener deleteBackgroundListener) {
    // DeletePictureDialog dialog = new DeletePictureDialog(context);
    // dialog.show();
    // dialog.setTitle(title);
    // dialog.setReBackgroundButton(reBackgroundListener);
    // dialog.setDeleteBackgroundButton(deleteBackgroundListener);
    // dialog.setCancelButton(null);
    // }
}
