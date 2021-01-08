package com.cjw.common.util;


import com.cjw.common.exception.BaseInfoInterface;

public enum ResultCode implements BaseInfoInterface {
    SUCCESS(0,"成功"),
    // 常见错误
    BODY_NOT_MATCH(400,"请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!"),
    // 参数错误1001-1999
    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    PARAM_TYPE_BAND_ERROR(1003,"参数类型错误"),
    PARAM_NOT_COMPLETE(1004,"参数缺失"),
    // 用户错误2001-2999
    USER_NOT_LOGGED_IN(2001,"用户未登录"),
    USER_LOGIN_ERROR(2002,"账户不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003,"账户已被禁用"),
    USER_NOT_EXIST(2004,"用户不存在"),
    USER_HAS_EXISTED(2005,"用户已存在"),
    USER_PASS_ERROR(2006,"密码错误"),
    USER_AUTHENTICATION_ERROR(2007,"认证失败"),
    USER_AUTHORIZATION_ERROR(2008,"没有权限"),
    USER_NAME_DUPLICATED(2009,"该用户名已经存在,不能创建或者修改已经存在的用户名"),
    // 服务器错误3001-3999
    SERVER_OPTIMISTIC_LOCK_ERROR(3001,"操作冲突"),
    SERVER_INNER_ERROR(3002,"服务器内部错误"),
    SERVER_UNKNOW_ERROR(3003,"服务器未知错误"),
    SERVER_EMPTY_RESULT_DATA_ACCESS_ERROR(3004,"没有找到对应的数据");

    private Integer status;
    private String message;

    ResultCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
    public Integer status(){
        return this.status;
    }
    public String message(){
        return this.message;
    }

}

