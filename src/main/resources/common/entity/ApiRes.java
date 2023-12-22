package com.black.common.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiRes<T> {
    T data;

    int code;

    String message;

    public ApiRes(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public ApiRes(T data){
        this.data=data;
        this.code=ApiCode.SUCCESS.code;
        this.message=ApiCode.SUCCESS.message;
    }

    public static ApiRes<String> SuccessApi(){
        return new ApiRes<>("操作成功");
    }

    public static ApiRes<String> SuccessApi(String message){
        return new ApiRes<>("成功",200,message);
    }

    public static ApiRes<String> FailApi(ApiCode apiCode){
        return new ApiRes<String>("操作失败",apiCode.code, apiCode.message);
    }

    public static ApiRes<String> FailApi(String message){
        return new ApiRes<String>("操作失败", ApiCode.FAIL.code, message);
    }

    public static ApiRes<String> FailApi(){
        return new ApiRes<String>("操作失败", ApiCode.FAIL.code, ApiCode.FAIL.message);
    }
}
