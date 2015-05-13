package com.kcb.common.server;

public class UrlUtil {

    private static final String IP = "http://192.168.1.1";
    private static final String url_stu_login = "/v1/stu/login";
    private static final String url_tch_login = "/v1/tch/login";

    public static String getStuLoginUrl(String id, String password) {
        return IP + url_stu_login;
    }

    public static String getTchLoginUrl(String id, String password) {
        return IP + url_tch_login;
    }
}
