package com.kcb.common.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.kcb.common.util.LogUtil;



/**
 * 
 * @className: UrlUtil
 * @description: define and get stu/tch http url here
 * @author: wanghang
 * @date: 2015-5-4 下午1:37:07
 */
public class UrlUtil {

    private static final String TAG = UrlUtil.class.getName();

    private static final String IP = "http://123.56.42.224";

    /**
     * ******************************common module******************************
     */
    /**
     * **********1, feedback**********
     */
    // 1.1 提交意见反馈
    private static final String url_common_feedback_submit = IP + "/v1/common/feedback/submit";

    public static String getCommFeedbackSubmitUrl() {
        return url_common_feedback_submit;
    }

    // 1.2 查看意见反馈列表
    private static final String url_common_feedback_look = IP + "/v1/common/feedback/look";

    // 所有人对app的反馈
    public static String getCommFeedbackLookAllUrl() {
        return url_common_feedback_look + "?type=0";
    }

    // 某个学生提交的所有反馈
    public static String getCommFeedbackLookStuUrl(String stuId) {
        return url_common_feedback_look + "?stuid=" + stuId;
    }

    // 对某个老师的所有反馈
    public static String getCommFeedbackLookTchUrl(String tchId) {
        return url_common_feedback_look + "?type=1&tchid=" + tchId;
    }

    /**
     * ******************************stu module******************************
     */
    /**
     * **********1, account**********
     */
    // 1.1 登录
    // private static final String url_stu_login = IP + "/v1/stu/account/login";
    private static final String url_stu_login = IP + "/TestServlet02/stuLogin";

    public static String getStuLoginUrl(String id, String password) {
        String url = "";
        String encodeId = "";
        String encodePassword = "";
        try {
            encodeId = URLEncoder.encode(id, "utf-8");
            encodePassword = URLEncoder.encode(password, "utf-8");
            url = url_stu_login + "?stuCode=" + encodeId + "&password=" + encodePassword;
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return url;
    }

    // 1.2 验证原密码
    private static final String url_stu_checkoldpassword = IP + "/TestServlet02/stuReset";

    public static String getStuCheckOldPasswordUrl(String id, String password) {
        String url = "";
        String encodeId = "";
        String encodePassword = "";
        try {
            encodeId = URLEncoder.encode(id, "utf-8");
            encodePassword = URLEncoder.encode(password, "utf-8");
            url =
                    url_stu_checkoldpassword + "?stuCode=" + encodeId + "&old=" + encodePassword
                            + "&flag=0";
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return url;
    }

    // 1.3 修改密码
    private static final String url_stu_modifypassword = IP + "/TestServlet02/stuReset";

    public static String getStuModifyPasswordUrl(String id, String newPassword) {
        String url = "";
        String encodeId = "";
        String encodePassword = "";
        try {
            encodeId = URLEncoder.encode(id, "utf-8");
            encodePassword = URLEncoder.encode(newPassword, "utf-8");
            url =
                    url_stu_modifypassword + "?stuCode=" + encodeId + "&new=" + encodePassword
                            + "&flag=1";
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return url;
    }

    /**
     * **********2, checkin**********
     */
    // 2.1 开始签到
    private static final String url_stu_checkin_start = IP + "/TestServlet02/stuCheckin";

    public static String getStuCheckinStartUrl(String stuId, String tchId) {
        return url_stu_checkin_start + "?stuCode=" + stuId + "&tchCode=" + tchId;
    }

    // 2.2 提交签到
    private static final String url_stu_checkin_submit = IP + "/TestServlet02/stuCheckin";

    public static String getStuCheckinSubmitUrl(String stuid, String tchId, String num) {
        return url_stu_checkin_submit + "?stuCode=" + stuid + "&tchCode=" + tchId + "&num=" + num;
    }

    // 2.3 获取签到结果
    private static final String url_stu_checkin_getresult = IP + "/v1/stu/checkin/getresult";

//      取缔了
//    public static String getStuCheckinResultUrl(String stuId, String tchId, long date) {
//        return url_stu_checkin_getresult + "?stuid=" + stuId + "&tchid=" + tchId + "&date=" + date;
//    }
    
    public static String getStuCheckinResultUrl(String stuId, String tchId) {
        return url_stu_checkin_getresult + "?stuid=" + stuId + "&tchid=" + tchId;
    }

    /**
     * **********3, test**********
     */
    // 3.1 开始测试
    private static final String url_stu_test_start = IP + "/v1/stu/test/start";

    public static String getStuTestStartUrl(String stuId, String tchId) {
        return url_stu_test_start + "?stuid=" + stuId + "&tchid=" + tchId;
    }

    // 3.2 结束测试
    private static final String url_stu_test_end = IP + "/v1/stu/test/finish";

    public static String getStuTestFinishUrl() {
        return url_stu_test_end;
    }

    // 3.3 查看测试结果
    private static final String url_stu_test_lookresult = IP + "/v1/stu/test/lookresult";

    public static String getStuTestLookResultUrl(String stuId, long date) {
        return url_stu_test_lookresult + "?stuid=" + stuId + "&date=" + date;
    }

    /**
     * ******************************tch module******************************
     */
    
    /**
     * **********1, account**********
     */
    // 1.1 登录
    private static final String url_tch_login = IP + "/TestServlet02/tecLogin";

    public static String getTchLoginUrl(String id, String password) {
        String url = "";
        String encodeId = "";
        String encodePassword = "";
        try {
            encodeId = URLEncoder.encode(id, "utf-8");
            encodePassword = URLEncoder.encode(password, "utf-8");
            url = url_tch_login + "?tchCode=" + encodeId + "&password=" + encodePassword;
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return url;
    }

    // 1.2 验证原密码
    private static final String url_tch_checkoldpassword = IP + "/TestServlet02/tecReset";

    public static String getTchCheckOldPasswordUrl(String id, String password) {
        String url = "";
        String encodeId = "";
        String encodePassword = "";
        try {
            encodeId = URLEncoder.encode(id, "utf-8");
            encodePassword = URLEncoder.encode(password, "utf-8");
            url = url_tch_checkoldpassword + "?tchCode=" + encodeId + "&old=" + encodePassword;
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return url;
    }

    // 1.3 修改密码
    private static final String url_tch_modifypassword = IP + "/TestServlet02/tecReset";

    public static String getTchModifyPasswordUrl(String id, String newPassword) {
        String url = "";
        String encodeId = "";
        String encodePassword = "";
        try {
            encodeId = URLEncoder.encode(id, "utf-8");
            encodePassword = URLEncoder.encode(newPassword, "utf-8");
            url = url_tch_modifypassword + "?tchCode=" + encodeId + "&new=" + encodePassword;
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return url;
    }

    /**
     * **********2, checkin**********
     */
    // 2.1 开始签到
//    private static final String url_tch_checkin_start = IP + "/v1/tch/checkin/start";
    private static final String url_tch_checkin_start = IP + "/TestServlet02/tchCheckin";

    public static String getTchCheckinStartUrl(String id, int num) {
        return url_tch_checkin_start + "?tchCode=" + id + "&num=" + num;
    }

    // 2.2 查看签到结果
//    private static final String url_tch_checkin_getresult = IP + "/v1/tch/checkin/getresult";
    private static final String url_tch_checkin_getresult = IP + "/v1/tch/checkin/getresult";

    public static String getTchCheckinGetresultUrl(String tchId, String date) {
        String url = "";
        String encodeDate;
        try {
            encodeDate = URLEncoder.encode(date, "utf-8");
            url = url_tch_checkin_getresult + "?tchid=" + tchId + "&date=" + encodeDate;
        } catch (UnsupportedEncodingException e) {}
        return url;
    }

    // 2.3 查看签到结果详情
    private static final String url_tch_checkin_getresultdetail = IP
            + "/v1/tch/checkin/getresultdetail";

    public static String getTchCheckinGetResultDetailUrl(String tchId, long date) {
        return url_tch_checkin_getresultdetail + "?tchid=" + tchId + "&date=" + date;
    }

    /**
     * **********3, test**********
     */
    // 3.1 检测有没有正在进行的测试
    private static final String url_tch_test_detect = IP + "/v1/tch/test/detect";

    public static String getTchTestDetectUrl(String tchId) {
        return url_tch_test_detect + "?tchid=" + tchId;
    }

    // 3.2 开始测试
    private static final String url_tch_test_start = IP + "/v1/tch/test/start";

    public static String getTchTestStartUrl() {
        return url_tch_test_start;
    }

    // 3.3 查看测试结果，包括内容和详情
    private static final String url_tch_test_lookresult = IP + "/v1/tch/test/lookresult";

    public static String getTchTestLookresultUrl(String id, long date) {
        return url_tch_test_lookresult + "?tchid=" + id + "&date=" + date;
    }

    // 3.4 查看测试结果，只有详情
    private static final String url_tch_test_lookresultdetail = IP
            + "/v1/tch/test/lookresultdetail";

    public static String getTchTestLookresultdetailUrl(String tchId, String testId) {
        return url_tch_test_lookresultdetail + "?tchid=" + tchId + "&testid=" + testId;
    }

    /**
     * **********4, stu centre**********
     */
    // 4.1 查看学生信息
    private static final String url_tch_stucenter_lookinfo = IP + "/v1/tch/stucenter/lookinfo";

    public static String getTchStucenterLookinfoUrl(String id) {
        return url_tch_stucenter_lookinfo + "?tchid=" + id;
    }
}
