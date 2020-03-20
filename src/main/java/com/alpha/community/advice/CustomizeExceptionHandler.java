package com.alpha.community.advice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.alpha.community.dto.ResultDTO;
import com.alpha.community.enums.CustomizeErrorCodeEnum;
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
	 * 1.当错误请求是application/json类型的时候，返回json错误信息
	 * 2.当错误请求是其他类型的时候，返回错误页面
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	ModelAndView handleControllerException(HttpServletRequest request, 
										   Throwable ex,HttpServletResponse response) {
		String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            // 返回 JSON
            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCodeEnum.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        } else {
            // 错误页面跳转
        	ModelAndView modelAndView = new ModelAndView("error");
            if (ex instanceof CustomizeException) {
            	modelAndView.addObject("message", ex.getMessage());
            } else {
            	modelAndView.addObject("message", CustomizeErrorCodeEnum.SYS_ERROR.getMessage());
            }
			return modelAndView;
        }
	}

}