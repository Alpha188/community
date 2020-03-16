package com.alpha.community.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.alpha.community.mapper.UserMapper;
import com.alpha.community.model.User;


@Controller
public class IndexController {
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/")
	public String index(HttpServletRequest req) {
		for (Cookie cookie : req.getCookies()) {
			if(cookie.getName().equals("token")) {
				String token = cookie.getValue();
				User user = userMapper.getUser(token);
				req.getSession().setAttribute("user", user);
				break;
			}
		}
		return "index";
	}
}
