package com.alpha.community.dto;

import java.util.List;

import com.alpha.community.enums.CustomizeErrorCodeEnum;

import com.alpha.community.exception.CustomizeErrorCode;
import com.alpha.community.exception.CustomizeException;

import lombok.Data;
/**
 * 返回json消息的时候用
 * @author alpha
 *
 */
@Data
public class ResultDTO<T> {
	private Integer code;
	private String message;
	private T data;
	
	public static ResultDTO errorOf(CustomizeErrorCode error) {
		return new ResultDTO(error.getCode(),error.getMessage());
	}
	public static ResultDTO okOf() {
		return new ResultDTO(200,"请求成功");
	}
	public static ResultDTO errorOf(CustomizeException e) {
		return new ResultDTO(e.getCode(),e.getMessage());
	}
	

	public static <T> ResultDTO okOf(T data) {
		return new ResultDTO(200,"请求成功",data);
	}
	public ResultDTO(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public ResultDTO(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
	
