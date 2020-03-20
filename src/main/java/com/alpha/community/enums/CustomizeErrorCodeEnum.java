package com.alpha.community.enums;

import com.alpha.community.exception.CustomizeErrorCode;

public enum CustomizeErrorCodeEnum implements CustomizeErrorCode{
	QUESTION_NOT_FOUND(2001,"您找的问题不存在，换个问题试试吧!"),
	TARGET_PARAM_NOT_FOUND(2002,"未选中如何问题或评论进行回复"), 
	NO_LOGIN(2003,"该操作需要登录，请登录后重试"), 
	SYS_ERROR(2004, "服务冒烟了,请稍后再试~"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"), 
    USUER_NOT_FOUND(2008,"用户不存在");
	
	private String message;
	private Integer code;

	CustomizeErrorCodeEnum(Integer code,String message) {
		this.message = message;
		this.code = code;
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
