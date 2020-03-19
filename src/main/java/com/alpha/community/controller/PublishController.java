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

import com.alpha.community.exception.CustomizeErrorCodeEnum;
import com.alpha.community.exception.CustomizeException;
import com.alpha.community.mapper.QuestionMapper;
import com.alpha.community.model.Question;
import com.alpha.community.model.User;
import com.alpha.community.service.QuestionService;

@Controller
public class PublishController {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/publish")
	public String publish() {
		return "publish";
	}

	@GetMapping("/publish/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Question question = questionMapper.selectByPrimaryKey(id);
		if (question == null) {
			throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FIND);
		} else {
			model.addAttribute("id", question.getId());
			model.addAttribute("title", question.getTitle());
			model.addAttribute("description", question.getDescription());
			model.addAttribute("tag", question.getTag());
		}
		return "publish";
	}

	@PostMapping("/publish")
	public String doPublish(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "id", required = false) Integer id, HttpServletRequest req, Model model) {
		model.addAttribute("title", title);
		model.addAttribute("description", description);
		model.addAttribute("tag", tag);
		if (title == null || title.equals("")) {
			model.addAttribute("error", "标题不能为空");
			return "publish";
		}
		if (description == null || description.equals("")) {
			model.addAttribute("error", "补充不能为空");
			return "publish";
		}
		if (tag == null || tag.equals("")) {
			model.addAttribute("error", "标签不能为空");
			return "publish";
		}

		User user = (User) req.getSession().getAttribute("user");
		if (user != null) {
			Question question = new Question();
			question.setDescription(description);
			question.setTitle(title);
			question.setTag(tag);
			question.setCreator(user.getId());
			question.setId(id);

			questionService.InsertOrUpdate(question);
			return "redirect:/";
		} else {
			model.addAttribute("error", "用户未登录");
			return "publish";
		}
	}
}
