package com.alpha.community.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.community.mapper.QuestionExtMapper;
import com.alpha.community.mapper.QuestionMapper;
import com.alpha.community.mapper.UserMapper;
import com.alpha.community.model.Question;
import com.alpha.community.model.QuestionExample;
import com.alpha.community.model.User;
import com.alpha.community.model.UserExample;
import com.alpha.community.dto.QuestionDTO;


@Service
public class QuestionService  {
	@Autowired
	private QuestionExtMapper questionExtMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private UserMapper userMapper;

	public List<QuestionDTO> list() {
		return questionExtMapper.listWithUser();
		
		
	}

	public List<QuestionDTO> listByCreator(Integer creator) {
		return questionExtMapper.listWithUserByCreator(creator);
	}
	
	public int InsertOrUpdate(Question question) {
		Date date = new Date();
		if (question.getId() == null) {
			// 插入
			question.setGmtCreate(date);
			question.setGmtModified(date);
			return questionMapper.insert(question);
		} else {
			// 更新
			question.setGmtModified(date);
			QuestionExample example = new QuestionExample();
			example.createCriteria().andIdEqualTo(question.getId());
			return questionMapper.updateByExampleSelective(question, example);
		}
	}

	public QuestionDTO getWithUser(Integer id) {
		Question question = questionMapper.selectByPrimaryKey(id);
		UserExample example = new UserExample();
		example.createCriteria().andIdEqualTo(question.getCreator());
		List<User> users = userMapper.selectByExample(example);
		
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO );
		questionDTO.setUser(users.get(0));
		return questionDTO;
	}
	

}
