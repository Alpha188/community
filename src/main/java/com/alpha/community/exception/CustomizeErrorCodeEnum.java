package com.alpha.community.exception;

public enum CustomizeErrorCodeEnum implements CustomizeErrorCode{
	QUESTION_NOT_FIND("您找的问题不存在，换个问题试试吧!");
	private String message;

	CustomizeErrorCodeEnum(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
