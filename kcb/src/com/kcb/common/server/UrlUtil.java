package com.kcb.common.server;

/**
 * 
 * @className: UrlUtil
 * @description: define and get stu/tch http url here
 * @author: wanghang
 * @date: 2015-5-4 下午1:37:07
 */
public class UrlUtil {

    private static final String IP = "http://armani.aliapp.com";

    /**
     * ******************************stu module******************************
     */
    /**
     * **********1, account**********
     */
    // 1.1 login
    private static final String url_stu_login = IP + "/v1/stu/account/login";

    public static String getStuLoginUrl(String id, String password) {
        return url_stu_login + "?id=" + id + "&password=" + password;
    }

    // 1.2 modify password
    private static final String url_stu_modifypassword = IP + "/v1/stu/account/modifypassword";

    public static String getStuModifyPasswordUrl(String id, String oldPassword, String newPassword) {
        return url_stu_modifypassword + "?id=" + id + "&old=" + oldPassword + "&new=" + newPassword;
    }

    /**
     * **********2, checkin**********
     */
    // 2.1 start checkin
    private static final String url_stu_checkin_start = IP + "/v1/stu/checkin/start";

    public static String getStuCheckinStartUrl(String id, String num) {
        return url_stu_checkin_start + "?id=" + id + "&num=" + num;
    }

    /**
     * **********3, test**********
     */
    // 3.1 start test
    private static final String url_stu_test_start = IP + "/v1/stu/test/start";

    public static String getStuTestStartUrl(String id) {
        return url_stu_test_start + "?id=" + id;
    }

    // 3.2 end test
    private static final String url_stu_test_end = IP + "/v1/stu/test/end";

    public static String getStuTestEndUrl(String id) { // TODO, set answer
        return url_stu_test_end + "?id=" + id;
    }

    /**
     * ******************************tch module******************************
     */
    /**
     * **********1, account**********
     */
    // 1.1 login
    private static final String url_tch_login = IP + "/v1/tch/account/login";

    public static String getTchLoginUrl(String id, String password) {
        return url_tch_login + "?id=" + id + "&password=" + password;
    }

    // 1.2 modify password
    private static final String url_tch_modifypassword = IP + "/v1/tch/account/modifypassword";

    public static String getTchModifyPasswordUrl(String id, String oldPassword, String newPassword) {
        return url_tch_modifypassword + "?id=" + id + "&old=" + oldPassword + "&new=" + newPassword;
    }

    /**
     * **********2, checkin**********
     */
    // 2.1 get checkin num
    private static final String url_tch_checkin_getnum = IP + "/v1/tch/checkin/getnum";

    public static String getTchCheckinGetnumUrl(String id) {
        return url_tch_checkin_getnum + "?id=" + id;
    }

    // 2.2 start checkin
    private static final String url_tch_checkin_start = IP + "/v1/tch/checkin/start";

    public static String getTchCheckinStartUrl(String id, String num) {
        return url_tch_checkin_start + "?id=" + id + "&num=" + num;
    }

    // 2.3 stop checkin
    private static final String url_tch_checkin_stop = IP + "/v1/tch/checkin/stop";

    public static String getTchCheckinStopUrl(String id, String num) {
        return url_tch_checkin_stop + "?id=" + id + "&num=" + num;
    }

    // 2.4 get checkin result
    private static final String url_tch_checkin_getresult = IP + "/v1/tch/checkin/getresult";

    public static String getTchCheckinGetresultUrl(String id, String num) {
        return url_tch_checkin_getresult + "?id=" + id + "&num=" + num;
    }

    // 2.5 giveup checkin
    private static final String url_tch_checkin_giveup = IP + "/v1/tch/checkin/giveup";

    public static String getTchCheckinGiveupUrl(String id, String num) {
        return url_tch_checkin_giveup + "?id=" + id + "&num=" + num;
    }

    /**
     * **********3, test**********
     */
    // 3.1 start test
    private static final String url_tch_test_start = IP + "/v1/tch/test/start";

    public static String getTchTestStartUrl(String id) {
        return url_tch_test_start + "?id=" + id;
    }

    // 3.2 look test result
    private static final String url_tch_test_lookresult = IP + "/v1/tch/test/lookresult";

    public static String getTchTestLookresultUrl(String id) {
        return url_tch_test_lookresult + "?id=" + id;
    }
}
