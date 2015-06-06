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
        return url_stu_login + "?stuid=" + id + "&password=" + password;
    }

    // 1.2 check old password
    private static final String url_stu_checkoldpassword = IP + "/v1/stu/account/checkoldpassword";

    public static String getStuCheckOldPasswordUrl(String id, String password) {
        return url_stu_checkoldpassword + "?stuid=" + id + "&old=" + password;
    }

    // 1.3 modify password
    private static final String url_stu_modifypassword = IP + "/v1/stu/account/modifypassword";

    public static String getStuModifyPasswordUrl(String id, String newPassword) {
        return url_stu_modifypassword + "?stuid=" + id + "&new=" + newPassword;
    }

    /**
     * **********2, checkin**********
     */
    // 2.1 start checkin
    private static final String url_stu_checkin_start = IP + "/v1/stu/checkin/start";

    public static String getStuCheckinStartUrl(String stuId, String tchId) {
        return url_stu_checkin_start + "?stuid=" + stuId + "&tchid=" + tchId;
    }

    private static final String url_stu_checkin_submit = IP + "/v1/stu/checkin/submit";

    public static String getStuCheckinSubmitUrl(String stuid, String tchId, String num) {
        return url_stu_checkin_submit + "?stuid=" + stuid + "&tchid=" + tchId + "&num=" + num;
    }

    private static final String url_stu_checkin_getresult = IP + "/v1/stu/checkin/start/getresult";

    public static String getStuCheckinResultUrl(String stuId, String tchId) {
        return url_stu_checkin_getresult + "?stuid=" + stuId + "&tchid=" + tchId;
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
    private static final String url_stu_test_end = IP + "/v1/stu/test/finish";

    public static String getStuTestFinishUrl() { // TODO, set answer
        return url_stu_test_end;
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
        return url_tch_login + "?tchid=" + id + "&password=" + password;
    }

    // 1.2 check password
    private static final String url_tch_checkoldpassword = IP + "/v1/tch/account/checkoldpassword";

    public static String getTchCheckOldPasswordUrl(String id, String password) {
        return url_tch_checkoldpassword + "?tchid=" + id + "&old=" + password;

    }

    // 1.3 modify password
    private static final String url_tch_modifypassword = IP + "/v1/tch/account/modifypassword";

    public static String getTchModifyPasswordUrl(String id, String newPassword) {
        return url_tch_modifypassword + "?tchid=" + id + "&new=" + newPassword;
    }

    /**
     * **********2, checkin**********
     */
    // 2.1 start checkin
    private static final String url_tch_checkin_start = IP + "/v1/tch/checkin/start";

    public static String getTchCheckinStartUrl(String id, int num) {
        return url_tch_checkin_start + "?tchid=" + id + "&num=" + num;
    }

    // 2.2 get checkin result
    private static final String url_tch_checkin_getresult = IP + "/v1/tch/checkin/getresult";

    public static String getTchCheckinGetresultUrl(String id) {
        return url_tch_checkin_getresult + "?tchid=" + id;
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

    /**
     * **********4, stu centre**********
     */
    // 4.1 look stu info
    private static final String url_tch_stucenter_lookinfo = IP + "/v1/tch/stucenter/lookstuinfo";

    public static String getTchStucenterLookinfoUrl(String id) {
        return url_tch_stucenter_lookinfo + "?id=" + id;
    }
}
