package com.unifs.sdbst.app.enums;

/**
 * @创建人 张恭雨
 * @创建时间 2018/8/16
 * @描述:枚举类型，返回类型状态定义
 */

public enum RespCode {

    SUCCESS(200, "成功!"),
    DEFAULT(0,"失败!"),
    INPRIVILEGE(403,"权限不足"),
    NOFOUND(404,"访问资源不存在"),
    REPATESUBMIT(405,"请勿重复提交"),
    ERROR(500,"出错啦！"),
    WARN(-1, "网络异常，请稍后重试");
    private int code;
    private String msg;

    RespCode(int code, String msg) {
        this.code=code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
