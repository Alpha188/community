package com.alpha.community.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alpha.community.dto.CommentCreateDTO;
import com.alpha.community.dto.ResultDTO;
import com.alpha.community.enums.CustomizeErrorCodeEnum;
import com.alpha.community.exception.CustomizeErrorCode;
import com.alpha.community.mapper.CommentMapper;
import com.alpha.community.model.Comment;
import com.alpha.community.model.User;
import com.alpha.community.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/comment")
	@ResponseBody
	public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
					   HttpServletRequest request){
		 User user = (User) request.getSession().getAttribute("user");
	        if (user == null) {
	            return ResultDTO.errorOf(CustomizeErrorCodeEnum.NO_LOGIN);
	        }

	        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
	            return ResultDTO.errorOf(CustomizeErrorCodeEnum.CONTENT_IS_EMPTY);
	        }

	        Comment comment = new Comment();
	        comment.setParentId(commentCreateDTO.getParentId());
	        comment.setContent(commentCreateDTO.getContent());
	        comment.setType(commentCreateDTO.getType());
	        Date date = new Date();
			comment.setGmtModified(date);
	        comment.setGmtCreate(date);
	        comment.setCommentator(user.getId());
	        comment.setLikeCount(0L);
	        commentService.insert(comment);
	        return ResultDTO.ok();
	}
}
