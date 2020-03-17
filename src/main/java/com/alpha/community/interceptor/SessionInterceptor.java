package com.alpha.community.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alpha.community.mapper.UserMapper;
import com.alpha.community.model.User;

@Component
public class SessionInterceptor implements HandlerInterceptor {
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					String token = cookie.getValue();
					User user = userMapper.getUserByToken(token);
					request.getSession().setAttribute("user", user);
					break;
				}
			}
		}
		return true;
	}
}
