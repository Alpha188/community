package com.alpha.community.exception;
/**
 * 自定义异常，构造函数接受一个CustomizeErrorCode接口类型
 * 所有的错误信息封装在com.alpha.community.enums.CustomizeErrorCodeEnum
 * @author alpha
 *
 */
public class CustomizeException extends RuntimeException{
	private Integer code;
	private String message;
	
	public CustomizeException(CustomizeErrorCode code) {
		this.message = code.getMessage();
		this.code = code.getCode();
	}

	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}
	
	
}
