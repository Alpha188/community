package com.alpha.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.community.mapper.QuestionExtMapper;
import com.alpha.community.mapper.QuestionMapper;
import com.alpha.community.mapper.UserMapper;
import com.alpha.community.dto.QuestionDTO;


@Service
public class QuestionService  {
	@Autowired
	private QuestionExtMapper questionExtMapper;
	@Autowired
	private UserMapper userMapper;

	public List<QuestionDTO> list() {
		return questionExtMapper.listWithUser();
		
		
	}

	public List<QuestionDTO> listByCreator(Integer creator) {
		return questionExtMapper.listWithUserByCreator(creator);
	}

	

}
