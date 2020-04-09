package com.alpha.community.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.alpha.community.enums.CustomizeErrorCodeEnum;
import com.alpha.community.exception.CustomizeException;

@Service
public class QuestionService {
	@Autowired
	private QuestionExtMapper questionExtMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private UserMapper userMapper;

	public List<QuestionDTO> list() {
		return questionExtMapper.listWithUser();
	}

	public List<QuestionDTO> listByCreator(Long creator) {
		return questionExtMapper.listWithUserByCreator(creator);
	}

	public void InsertOrUpdate(Question question) {
		Date date = new Date();
		if (question.getId() == null) {
			// 插入
			question.setGmtCreate(date);
			question.setGmtModified(date);
			questionMapper.insertSelective(question);
		} else {
			// 更新
			question.setGmtModified(date);
			QuestionExample example = new QuestionExample();
			example.createCriteria().andIdEqualTo(question.getId());
			int updated = questionMapper.updateByExampleSelective(question, example);
			if (updated != 1) {
				throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
			}
		}
	}

	public QuestionDTO getWithUser(Long id) {
		Question question = questionMapper.selectByPrimaryKey(id);
		if(question==null) {
			throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
		}
		UserExample example = new UserExample();
		example.createCriteria().andIdEqualTo(question.getCreator());
		List<User> users = userMapper.selectByExample(example);

		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		questionDTO.setUser(users.get(0));
		return questionDTO;
	}

	public List<Question> listRelated(QuestionDTO questionDTO) {
		if(StringUtils.isBlank(questionDTO.getTag())) {
			return new ArrayList<Question>();
		}
		Question question = new Question();
		question.setId(questionDTO.getId());
		question.setTag(questionDTO.getTag().replaceAll(",", "|"));
		
		return questionExtMapper.listRelated(question);
	}
	
	

}
