package com.alpha.community.dto;

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
public class ResultDTO {
	private Integer code;
	private String message;
	
	
	public static ResultDTO errorOf(CustomizeErrorCode error) {
		return new ResultDTO(error.getCode(),error.getMessage());
	}
	public static ResultDTO ok() {
		return new ResultDTO(200,"请求成功");
	}
	public static ResultDTO errorOf(CustomizeException e) {
		return new ResultDTO(e.getCode(),e.getMessage());
	}
	
	public ResultDTO(Integer code, String message) {
		this.message = message;
		this.code = code;
	}
	
}
	
