package com.kcb.common.server;

/**
 * 
 * @className: UrlUtil
 * @description: define and get http url here
 * @author: wanghang
 * @date: 2015-5-4 下午1:37:07
 */
public class UrlUtil {

    private static final String IP = "http://192.168.1.1";

    /**
     * stu module
     */
    private static final String url_stu_login = "/v1/stu/login";

    // login
    public static String getStuLoginUrl(String id, String password) {
        return IP + url_stu_login;
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
