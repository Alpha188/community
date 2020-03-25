package com.alpha.community.dto;

import java.util.Date;

import com.alpha.community.model.Question;
import com.alpha.community.model.User;

import lombok.Data;
@Data
public class QuestionDTO extends Question {
    private User user;
}