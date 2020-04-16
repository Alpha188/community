package com.alpha.community.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.alpha.community.dto.NotificationDTO;
import com.alpha.community.dto.QuestionDTO;
import com.alpha.community.model.User;
import com.alpha.community.service.NotificationService;
import com.alpha.community.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class ProfileController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/profile/{action}")
	public String profile(@PathVariable("action")String action,
						  Model model,
						  HttpServletRequest req,
						  @RequestParam(name="page",defaultValue="1") Integer page,
						  @RequestParam(name="size",defaultValue="10") Integer size) {
		User user = (User) req.getSession().getAttribute("user");
		if(user==null) {
			// 用户未登录
			return "redirect:/";
		}
		
		if ("questions".equals(action)) {
			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "我的问题");
			
			PageHelper.startPage(page, size);
			List<QuestionDTO> questions = questionService.listByCreator(user.getId());
			PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questions, 5);
			model.addAttribute("pageInfo", pageInfo);

		} else if ("replies".equals(action)) {
			model.addAttribute("section", "replies");
			model.addAttribute("sectionName", "最新回复");
			
			PageHelper.startPage(page, size);
			List<NotificationDTO> notifications = notificationService.listByReceiver(user.getId());
			PageInfo<NotificationDTO> pageInfo = new PageInfo<>(notifications, 5);
			model.addAttribute("pageInfo", pageInfo);

		}
		
		return "profile";
	}
}
