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

    public static String getStuModifyPasswordUrl(String id, String newPassword) {
        return url_stu_modifypassword + "?id=" + id + "&new=" + newPassword;
    }

    // 1.3 check password
    private static final String url_stu_checkoldpassword = IP + "/v1/stu/account/checkoldpassword";

    public static String getStuCheckOldPasswordUrl(String id, String password) {
        return url_stu_checkoldpassword + "?id=" + id + "&old=" + password;

    }

    /**
     * **********2, checkin**********
     */
    // 2.1 start checkin
    private static final String url_stu_checkin_start = IP + "/v1/stu/checkin/start";

    public static String getStuCheckinStartUrl(String id) {
        return url_stu_checkin_start + "?id=" + id;
    }

    private static final String url_stu_checkin_submit = IP + "/v1/stu/checkin/submit";

    public static String getStuCheckinSubmitUrl(String id, String num) {
        return url_stu_checkin_submit + "?id=" + id + "&num=" + num;
    }

    private static final String url_stu_checkin_getresult = IP + "/v1/stu/checkin/start/getresult";

    public static String getStuCheckinResultUrl(String id) {
        return url_stu_checkin_getresult + "?id=" + id;
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

    public static String getTchModifyPasswordUrl(String id, String newPassword) {
        return url_tch_modifypassword + "?id=" + id + "&new=" + newPassword;
    }

    // 1.3 check password
    private static final String url_tch_checkoldpassword = IP + "/v1/tch/account/checkoldpassword";

    public static String getTchCheckOldPasswordUrl(String id, String password) {
        return url_tch_checkoldpassword + "?id=" + id + "&old=" + password;

    }

    /**
     * **********2, checkin**********
     */
    // 2.1 start checkin
    private static final String url_tch_checkin_start = IP + "/v1/tch/checkin/start";

    public static String getTchCheckinStartUrl(String id, int num) {
        return url_tch_checkin_start + "?id=" + id + "&num=" + num;
    }

    // 2.2 get checkin result
    private static final String url_tch_checkin_getresult = IP + "/v1/tch/checkin/getresult";

    public static String getTchCheckinGetresultUrl(String id) {
        return url_tch_checkin_getresult + "?id=" + id;
    }

    /**
     * **********3, test**********
     */
    // 3.1 start test
    private static final String url_tch_test_start = IP + "/v1/tch/test/start";

    public static String getTchTestStartUrl() {
        return url_tch_test_start;
    }

    // 3.2 look test result
    private static final String url_tch_test_lookresult = IP + "/v1/tch/test/lookresult";

    public static String getTchTestLookresultUrl(String id) {
        return url_tch_test_lookresult + "?id=" + id;
    }
}
