package com.alpha.community.model;

import java.util.Date;

import lombok.Data;
@Data
public class Question {
    private Integer id;
    private Integer creator;
    private String title;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private String tag;
    private Date gmtCreate;
    private Date gmtModified;
    private String description;
}