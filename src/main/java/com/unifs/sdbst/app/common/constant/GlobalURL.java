package com.unifs.sdbst.app.common.constant;

/**
 * @version V1.0
 * @title: GlobalURL
 * @projectName sdbst
 * @description: 接口调用地址常量
 * @author： 张恭雨
 * @date 2019/8/21 9:53
 */
public class GlobalURL {
    public static final String POST_URL="http://124.172.189.170:8082/shundeAPI";

    //发送短信地址
    public static final String SMS_BASE_URL = "http://61.142.131.10:888/OaListenerServlet?mType=89&mPassword=WaGswf51Cj6*r0Vo";

    /*省网厅*/
    //回调地址
    public static final String ST_REDIRECT_URI="http%3A%2F%2Fsdbst.shunde.gov.cn%3A8082%2FSDBST%2Fshengting%2Fgetstuserinfo";
    //用户登陆后  跳到回调地址  获取code
    public static final String ST_URL_GET_CODE="http://tyrz.gdbs.gov.cn/am/oauth2/authorize?client_id=gdbssdbst&client_secret=sd20180404&service=initService&response_type=code&scope=all&redirect_uri=http%3A%2F%2Fsdbst.shunde.gov.cn%3A8082%2FSDBST%2Fshengting%2Fgetstuserinfo";
    //通过code获取token
    public static final String ST_URL_GET_TOKEN="http://tyrz.gdbs.gov.cn/am/oauth2/access_token?client_id=gdbssdbst&redirect_uri=http%3A%2F%2Fsdbst.shunde.gov.cn%3A8082%2FSDBST%2Fshengting%2Fgetstuserinfo&scope=all&client_secret=sd20180404&grant_type=authorization_code&code=CODE";
    //通过token获取用户信息
    public static final String ST_URL_GET_USERINFO="http://tyrz.gdbs.gov.cn/am/oauth2/tokeninfo?access_token=ACCESS_TOKEN";

    //通过code获取token
    public static final String ST_SSO_URL_GET_TOKEN="http://tyrz.gdbs.gov.cn/am/oauth2/access_token?client_id=gdbssdbst&redirect_uri=http%3A%2F%2Fsdbst.shunde.gov.cn%3A8082%2FSDBST%2Fshengting%2FgetSsostuserinfo&scope=all&client_secret=sd20180404&grant_type=authorization_code&code=CODE";
    public static final String ST_USER_TOKEN="http://tyrz.gdbs.gov.cn/am/oauth2/access_token?client_id=gdbssdbst&redirect_uri=http%3A%2F%2Fsdbst.shunde.gov.cn%3A8082%2FSDBST%2Fshengting%2FgetUserinfo&scope=all&client_secret=sd20180404&grant_type=authorization_code&code=CODE";
}

