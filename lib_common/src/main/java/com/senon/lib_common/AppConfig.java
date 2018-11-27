package com.senon.lib_common;

import com.senon.lib_common.login.Login;
import com.senon.lib_common.net.bean.BaseResponse;

/**
 * APP配置参数
 */
public class AppConfig {


    public static final String BASE_URL = "http://xfhapi.xiufanghua.com/";
    public static final String SECRET = "xfh";


    //开闭Klog debug日志
    public static final boolean LOG_DEBUG = true;


    public static String UID;
    public static String USERNAME;
    public static String MOBILE;
    public static String MONEY;
    public static String SIGN;
    public static String POINT;
    public static String TOKEN;
    public static String PASSWORD;
    public static String PAY_PASSWORD;
    public static String HEADIMGURL;
    public static String JOIN_TIME;
    public static String SIG;
    public static String FANS_COUNT;//粉丝
    public static String FOCUS_COUNT;//关注
    public static String BALANCE;//账户余额
    public static String INVITE;//邀请码
    public static String REG_TIME;//注册时间
    public static String LAST_LOGIN_TIME;//最后登录时间
    public static String DES;//新版加密


    public static void saveLoginInfo(BaseResponse data) {
        if(data.getData() instanceof Login){
            AppConfig.UID = ((Login)data.getData()).getUid() + "";
            AppConfig.USERNAME =((Login)data.getData()).getNickname();
            AppConfig.MOBILE = ((Login)data.getData()).getMobile();
            AppConfig.MONEY =((Login)data.getData()).getBalance();
            AppConfig.SIGN = ((Login)data.getData()).getSignature();
            AppConfig.POINT = ((Login)data.getData()).getScore1() + "";
            AppConfig.TOKEN = ((Login)data.getData()).getToken() + "";
            AppConfig.PASSWORD = ((Login)data.getData()).getPassword() + "";
            AppConfig.PAY_PASSWORD = ((Login)data.getData()).getPay_password() + "";
            AppConfig.HEADIMGURL = ((Login)data.getData()).getPortrait() + "";
            AppConfig.JOIN_TIME = ((Login)data.getData()).getReg_time() + "";
            AppConfig.SIG = ((Login)data.getData()).getSig() + "";
            AppConfig.FANS_COUNT = ((Login)data.getData()).getFans_count() + "";
            AppConfig.FOCUS_COUNT = ((Login)data.getData()).getFocus_count() + "";
            AppConfig.BALANCE = ((Login)data.getData()).getBalance() + "";
            AppConfig.INVITE = ((Login)data.getData()).getInvite() + "";
            AppConfig.REG_TIME = ((Login)data.getData()).getReg_time() + "";
            AppConfig.LAST_LOGIN_TIME = ((Login)data.getData()).getLast_login_time() + "";
            AppConfig.DES = ((Login)data.getData()).getDes() + "";
        }
//        else if(data.getData() instanceof Register){
//            AppConfig.UID = ((Register)data.getData()).getUid() + "";
//            AppConfig.USERNAME =((Register)data.getData()).getNickname();
//            AppConfig.MOBILE = ((Register)data.getData()).getMobile();
//            AppConfig.MONEY =((Register)data.getData()).getBalance();
//            AppConfig.SIGN = (String) ((Register)data.getData()).getSignature();
//            AppConfig.POINT = ((Register)data.getData()).getScore1() + "";
//            AppConfig.TOKEN = ((Register)data.getData()).getToken() + "";
//            AppConfig.PASSWORD = ((Register)data.getData()).getPassword() + "";
//            AppConfig.PAY_PASSWORD = ((Register)data.getData()).getPay_password() + "";
//            AppConfig.HEADIMGURL = ((Register)data.getData()).getPortrait() + "";
//            AppConfig.JOIN_TIME = ((Register)data.getData()).getReg_time() + "";
//            AppConfig.SIG = ((Register)data.getData()).getSig() + "";
//            AppConfig.FANS_COUNT = ((Register)data.getData()).getFans_count() + "";
//            AppConfig.FOCUS_COUNT = ((Register)data.getData()).getFocus_count() + "";
//            AppConfig.BALANCE = ((Register)data.getData()).getBalance() + "";
//            AppConfig.Invite = ((Register)data.getData()).getInvite() + "";
//            AppConfig.reg_time = ((Register)data.getData()).getReg_time() + "";
//            AppConfig.last_login_time = ((Register)data.getData()).getLast_login_time() + "";
//            AppConfig.des = ((Register)data.getData()).getDes() + "";
//        }
    }

}
