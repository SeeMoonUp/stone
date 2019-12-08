package com.javalemon.stone.common;

import lombok.Data;

/**
 * @author lemon
 * @date 2019-07-14
 * @desc
 */

@Data
public class Result<T> {
    private T data;

    private String msg;

    private Integer code = 0;

    public static Result success() {
        Result result = new Result();
        result.setCode(CodeEnum.SUCCESS.getCode());
        result.setMsg(CodeEnum.SUCCESS.getMsg());
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(CodeEnum codeEnum) {
        Result<T> result = new Result<>();
        result.setCode(codeEnum.getCode());
        result.setMsg(codeEnum.getMsg());
        return result;
    }

    public boolean isSuccess() {
        return this.code == CodeEnum.SUCCESS.getCode();
    }

    public enum CodeEnum{
        SUCCESS(0, "成功"),
        DAO_ERROR(1, "数据执行异常"),
        SERVICE_ERROR(2, "服务层执行异常"),
        NO_GROUP_ERROR(3, "没有找到对应的分组信息"),
        NO_TAG_ERROR(4, "没有找到对应的标签信息"),
        NO_LOGIN(5, "未登录"),
        PASS_ERROR(6, "密码或用户名错误"),
        PARAM_ERROR(7, "参数错误"),
        WECHAT_AUTH_ERROR(8, "微信授权登录失败"),
        MP_LOGIN_ERROR(9, "登录失败"),
        NO_USER_ERROR(10, "用户未登录"),
        ;

        private int code;
        private String msg;
        CodeEnum(int code, String msg) {
            this.code = code;
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
}
