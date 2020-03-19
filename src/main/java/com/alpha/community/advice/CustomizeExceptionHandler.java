package com.alpha.community.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alpha.community.exception.CustomizeException;
/**
 * 异常错误处理，当有异常的时候捕获，返回ModelAndView
 * 当异常不能被处理的时候，写一个类实现ErrorController接口
 * 参考com.alpha.community.controller.CustomizeErrorController
 * @author alpha
 *
 */
@ControllerAdvice
public class CustomizeExceptionHandler{
	/**
	 * 捕获所有的异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	ModelAndView handleControllerException(HttpServletRequest request, Throwable ex) {
		ModelAndView modelAndView = new ModelAndView("error");
		if (ex instanceof CustomizeException) {
			modelAndView.addObject("message", ex.getMessage());
		}else {
			modelAndView.addObject("message", "服务冒烟了,请稍后再试~");
		}
		return modelAndView;
	}

}