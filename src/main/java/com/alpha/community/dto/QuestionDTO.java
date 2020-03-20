package com.alpha.community.dto;

import java.util.Date;

import com.alpha.community.model.User;

import lombok.Data;
@Data
public class QuestionDTO {
    private Long id;
    private Integer creator;
    private String title;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private String tag;
    private Date gmtCreate;
    private Date gmtModified;
    private String description;
    private User user;
}