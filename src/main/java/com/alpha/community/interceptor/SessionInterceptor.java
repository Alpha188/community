package com.alpha.community.interceptor;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alpha.community.mapper.UserMapper;
import com.alpha.community.model.User;
import com.alpha.community.model.UserExample;

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
					UserExample userExample = new UserExample();
					userExample.createCriteria().andTokenEqualTo(token);
					List<User> users = userMapper.selectByExample(userExample);
					request.getSession().setAttribute("user", users.get(0));
					break;
				}
			}
		}
		return true;
	}
}
