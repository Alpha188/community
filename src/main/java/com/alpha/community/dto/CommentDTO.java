package com.alpha.community.dto;

import java.util.Date;

import com.alpha.community.model.User;

import lombok.Data;

@Data
public class CommentDTO {
	private Long id;

	private Long parentId;

	private Integer type;

	private Long commentator;

	private String content;

	private Date gmtCreate;

	private Date gmtModified;

	private Long likeCount;
	
	private User user;
}
