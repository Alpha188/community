package com.alpha.community.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * 1.You can also use regular Spring MVC features such as @ExceptionHandler methods and @ControllerAdvice. 
 * The ErrorController then picks up any unhandled exceptions.
 * 
 * 2.The BasicErrorController can be used as a base class for a custom ErrorController. 
 * This is particularly useful if you want to add a handler for a new content type 
 * (the default is to handle text/html specifically and provide a fallback for everything else). 
 * To do so, extend BasicErrorController, add a public method with a @RequestMapping that has a produces attribute, 
 * and create a bean of your new type.
 * 
 * 以上是springBoot官网文档原话，对于@ControllerAdvice类不能处理的错误
 * 可以实现ErrorController，提供一个带有@RequestMapping(produces = "text/html")的注解
 * 具体参考org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
 * @author alpha
 *
 */
//@Controller
//@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "error";
	}

	@RequestMapping(produces = "text/html")
	public ModelAndView errorHtml(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("error");
		HttpStatus status = getStatus(request);
		if (status.is4xxClientError()) {
			modelAndView.addObject("message", "您的请求出问题了!");
		} else if (status.is5xxServerError()) {
			modelAndView.addObject("message", "服务冒烟了,请稍后再试~");
		}
		return modelAndView;
	}

	protected HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		} catch (Exception ex) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
}
