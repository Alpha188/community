package com.alpha.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.community.mapper.QuestionMapper;
import com.alpha.community.mapper.UserMapper;
import com.alpha.community.dto.QuestionDTO;


@Service
public class QuestionService  {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private UserMapper userMapper;

	public List<QuestionDTO> list() {
		return questionMapper.listWithUser();
		
		
	}

	public List<QuestionDTO> listByCreator(Integer creator) {
		return questionMapper.listWithUserByCreator(creator);
	}

	

}
