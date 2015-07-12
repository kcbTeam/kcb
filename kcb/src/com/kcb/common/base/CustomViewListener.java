package com.kcb.common.base;

import android.content.Context;

/**
 * 
 * @className: CustomViewListener
 * @description: 所有自定义的view都需要继承自BaseLinearLayout或者BaseReleativeLayout，此二者都实现了这个接口；
 * @author: wanghang
 * @date: 2015-7-11 下午2:44:24
 */
public interface CustomViewListener {

    /**
     * 由构造方法调用；
     */
    void init(Context context);

    /**
     * 由init()方法调用；
     */
    void initView();

    /**
     * 不用view时调用；
     */
    void release();
}
