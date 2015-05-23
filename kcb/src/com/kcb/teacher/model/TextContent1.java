package com.kcb.teacher.model;

import java.io.Serializable;

/**
 * 
 * @className: TextContent
 * @description:
 * @author: ZQJ
 * @date: 2015年5月15日 下午8:07:01
 */
public class TextContent1<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4919254309171318451L;

    @SuppressWarnings("unused")
    private static final String TAG = "TextContent";

    private boolean isString = false;
    private T mContent;

    public TextContent1(T mContent) {
        this.mContent = mContent;
        if (mContent instanceof String)
            isString = true;
        else
            isString = false;
    }

    public boolean isString() {
        return isString;
    }

    public T getContent() {
        return this.mContent;
    }

    public void setContent(T mContent) {
        this.mContent = mContent;
    }


    public boolean equals(TextContent1<T> o) {
        if (this == o) {
            return true;
        }
        if (this.isString == o.isString) {
            return this.mContent.equals(o.mContent);
        }
        return false;
    }


}
