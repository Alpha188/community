package com.alpha.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
	private long parentId;
	private String content;
	private int type;
	
}
