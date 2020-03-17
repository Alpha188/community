package com.alpha.community.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alpha.community.dto.QuestionDTO;
import com.alpha.community.mapper.QuestionMapper;
import com.alpha.community.mapper.UserMapper;
import com.alpha.community.model.Question;
import com.alpha.community.model.User;
import com.alpha.community.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class IndexController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/")
	public String index(HttpServletRequest req,
						Model model,
						@RequestParam(name="page",defaultValue="1") Integer page,
						@RequestParam(name="size",defaultValue="5") Integer size) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					String token = cookie.getValue();
					User user = userMapper.getUserByToken(token);
					req.getSession().setAttribute("user", user);
					break;
				}
			}
		}
		PageHelper.startPage(page, size);
		List<QuestionDTO> questionList = questionService.list();
		PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questionList, 5);
		model.addAttribute("pageInfo", pageInfo);
		return "index";
	}
}