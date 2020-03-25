package com.alpha.community.dto;

import java.util.Date;

import com.alpha.community.model.Comment;
import com.alpha.community.model.User;

import lombok.Data;

@Data
public class CommentDTO extends Comment{
	private User user;
}
