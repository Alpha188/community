package com.alpha.community.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	private Integer id;
	private String name;
	private String AccountId;
	private String token;
	private String bio;
	private String avatarUrl;
	private Date gmtCreate;
	private Date gmtModified;

}
