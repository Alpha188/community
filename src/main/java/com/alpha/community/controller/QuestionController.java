package com.alpha.community.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.alpha.community.dto.QuestionDTO;
import com.alpha.community.mapper.QuestionExtMapper;
import com.alpha.community.service.QuestionService;

@Controller
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionExtMapper questionExtMapper;
	@GetMapping("/question/{id}")
	public String question(@PathVariable("id") Long id, Model model) {
		// 增加阅读数
		questionExtMapper.incViewCount(id);
		// 获取问题
		QuestionDTO question = questionService.getWithUser(id);
		model.addAttribute("question", question);
		return "question";
	}

}
