package com.example.community.Exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(4001,"该问题不存在或者已经被删除！"),
    TARGET_PARAM_NOT_FOUND(4002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(4003,"当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(4004,"服务器错误，请重试！"),
    TYPE_PARAM_WRONG(4005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(4006,"你回复的评论离家出走了~刷新重试吧！");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
