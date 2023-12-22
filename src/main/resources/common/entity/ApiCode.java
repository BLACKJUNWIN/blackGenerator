package com.black.common.entity;

public enum ApiCode {
    FAIL(500,"操作失败"),
    NO_OBJECT(501,"不存在对象"),
    EXIST_OBJECT(502,"对象已存在"),
    ERROR_LOGIN(502,"登录失败:用户名或密码错误"),
    SUCCESS(200,"操作成功");
    final int code;
    final String message;

    ApiCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
