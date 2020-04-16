package com.alpha.community.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alpha.community.dto.QuestionDTO;
import com.alpha.community.mapper.UserMapper;
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
						@RequestParam(name="size",defaultValue="10") Integer size,
						@RequestParam(name="keyword",required = false) String keyword) {
		
		PageHelper.startPage(page, size);
		List<QuestionDTO> questionList = questionService.listByKeyword(keyword);
		PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questionList, 5);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("keyword", keyword);
		return "index";
	}
}