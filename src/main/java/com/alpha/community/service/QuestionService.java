package com.alpha.community.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.community.mapper.QuestionMapper;
import com.alpha.community.mapper.UserMapper;
import com.alpha.community.dto.QuestionDTO;
import com.alpha.community.model.Question;
import com.alpha.community.model.User;


@Service
public class QuestionService  {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private UserMapper userMapper;

	public List<QuestionDTO> list() {
		List<QuestionDTO> questionDTOlist = new ArrayList<QuestionDTO>();
		List<Question> questionList = questionMapper.list();
		
		for (Question question : questionList) {
			User user = userMapper.getUserById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOlist.add(questionDTO);
		}
		return questionDTOlist;
	}

	public List<QuestionDTO> listByCreator(Integer creator) {
		List<QuestionDTO> questionDTOlist = new ArrayList<QuestionDTO>();
		List<Question> questionList = questionMapper.listByCreator(creator);
		
		for (Question question : questionList) {
			User user = userMapper.getUserById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOlist.add(questionDTO);
		}
		return questionDTOlist;
	}

	

}
