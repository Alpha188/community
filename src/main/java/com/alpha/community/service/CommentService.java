package com.alpha.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alpha.community.enums.CommentTypeEnum;
import com.alpha.community.enums.CustomizeErrorCodeEnum;
import com.alpha.community.exception.CustomizeException;
import com.alpha.community.mapper.CommentMapper;
import com.alpha.community.mapper.QuestionExtMapper;
import com.alpha.community.mapper.QuestionMapper;
import com.alpha.community.model.Comment;
import com.alpha.community.model.Question;

@Service
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private QuestionExtMapper questionExtMapper;
	@Autowired
	private QuestionMapper questionMapper;

	@Transactional
	public void insert(Comment comment) {
		if (comment.getParentId() == null || comment.getParentId() == 0) {
			throw new CustomizeException(CustomizeErrorCodeEnum.TARGET_PARAM_NOT_FOUND);
		}
		Integer type = comment.getType();

		if (type == null || !CommentTypeEnum.isExist(type)) {
			throw new CustomizeException(CustomizeErrorCodeEnum.TYPE_PARAM_WRONG);
		}

		// 是否是二级评论(对评论回复)
		if ((type == CommentTypeEnum.COMMENT.getType())) {
			// 对评论进行回复

			// 1.判断一级评论是否存在
			Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
			if (dbComment == null) {
				throw new CustomizeException(CustomizeErrorCodeEnum.COMMENT_NOT_FOUND);
			}

			// 2.判断一级评论对应的问题是否存在
			Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
			}

			// 3.插入
			commentMapper.insertSelective(comment);

		} else {
			// 对问题进行回复

			// 1.判断问题是否存在
			Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
			}
			// 2.插入
			commentMapper.insertSelective(comment);

			// 3.增加回复数
			questionExtMapper.incCommentCount(question.getId());
		}

	}

}
