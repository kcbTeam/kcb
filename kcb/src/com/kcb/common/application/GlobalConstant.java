package com.kcb.common.application;

/**
 * 
 * @className: GlobalConstant
 * @description: 全局变量定义；
 * @author: wanghang
 * @date: 2015-5-1 下午1:07:42
 */
public class GlobalConstant {

    // 是否允许使用LogUtil打印日志，发布时置为false；
    public static final boolean ENABLE_LOG = true;

    // 是否允许查看用户的意见反馈，发布时置为false；
    // 管理员版本可以置为true，然后在意见反馈页面可以看到所有用户的反馈内容；
    public static final boolean ENABLE_SEE_FEEDBACK = true;
}
