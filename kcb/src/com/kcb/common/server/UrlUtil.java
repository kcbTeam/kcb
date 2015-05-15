package com.kcb.common.server;

/**
 * 
 * @className: UrlUtil
 * @description: define and get http url here
 * @author: wanghang
 * @date: 2015-5-4 下午1:37:07
 */
public class UrlUtil {

    private static final String IP = "http://armani.aliapp.com";

    /**
     * stu module
     */
    private static final String url_stu_login = "/servlet/LoginAction";

    // login
    public static String getStuLoginUrl(String id, String password) {
        return IP + url_stu_login + "?username=" + id + "&password=" + password;
    }

    /**
     * tch module
     */
    private static final String url_tch_login = "/v1/tch/login";

    // login
    public static String getTchLoginUrl(String id, String password) {
        return IP + url_tch_login;
    }
}
