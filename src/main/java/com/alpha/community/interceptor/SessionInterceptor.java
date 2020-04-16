package com.alpha.community.interceptor;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alpha.community.enums.CustomizeErrorCodeEnum;
import com.alpha.community.exception.CustomizeException;
import com.alpha.community.mapper.UserMapper;
import com.alpha.community.model.User;
import com.alpha.community.model.UserExample;
import com.alpha.community.service.NotificationService;

@Component
public class SessionInterceptor implements HandlerInterceptor {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private NotificationService notificationService;

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
					if (users.size() != 0) {
						request.getSession().setAttribute("user", users.get(0));
						Long unreadCount = notificationService.countUnread(users.get(0).getId());
						request.getSession().setAttribute("unreadCount", unreadCount);
						break;
					} else {
						throw new CustomizeException(CustomizeErrorCodeEnum.USUER_NOT_FOUND);
					}

				}
			}
		}
		return true;
	}
}
